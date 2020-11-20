package com.github.xujiaji.mk.community.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDetailsDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityComment;
import com.github.xujiaji.mk.community.entity.MkCommunityPraise;
import com.github.xujiaji.mk.community.front.playload.ArticleCommentAddCondition;
import com.github.xujiaji.mk.community.mapper.MkCommunityArticleMapper;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleServiceImpl;
import com.github.xujiaji.mk.community.service.impl.MkCommunityCommentServiceImpl;
import com.github.xujiaji.mk.community.service.impl.MkCommunityPraiseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 社区动态—帖子表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@Service
public class MkFrontCommunityArticleCommentService extends MkCommunityArticleServiceImpl {

    private final MkCommunityPraiseServiceImpl praiseService;
    private final MkCommunityCommentServiceImpl commentService;
    private final MkFrontCommunityNoticeService noticeService;
    private final MkCommunityArticleMapper articleMapper;

    public void commentAdd(Long userId, ArticleCommentAddCondition request) {
        val comment = new MkCommunityComment();
        comment.setRootId(request.getArticleId());
        // 如果是回复
        MkCommunityComment replyComment = null;
        if (request.getReplyId() != null) {
            // 获取回复的评论，然后设置父级评论
            replyComment = commentService.getById(request.getReplyId());
            if (replyComment == null) {
                throw new RequestActionException("没有这个评论");
            }
            comment.setParentId(replyComment.getParentId());
            comment.setReplyId(request.getReplyId());
        }
        comment.setUserId(userId);
        comment.setContent(request.getContent());
        commentService.add(comment);
        // 如果是评论的帖子，那么设置父级id为自己的id
        if (request.getReplyId() == null) {
            comment.setParentId(comment.getId());
            commentService.updateById(comment);
            noticeService.addNotice(userId, articleMapper.selectAuthorIdByArticleId(request.getArticleId()), comment.getId(), Consts.NoticeType.ARTICLE_COMMENT);
        } else {
            assert replyComment != null;
            noticeService.addNotice(userId, replyComment.getUserId(), comment.getId(), Consts.NoticeType.COMMENT_REPLY);
        }
    }

    public IPage<FrontArticleCommentDTO> commentPage(Long userId, Page<FrontArticleCommentDTO> page, Long articleId, Integer type) {
        val commentPage = commentService.getBaseMapper().articleCommentPage(page, articleId, type);
        for (FrontArticleCommentDTO record : commentPage.getRecords()) {
            if (userId != null) {
                record.setPraised(praiseService.praiseStatus(record.getId(), userId, Consts.PraiseType.COMMENT));
            }
            record.setChild(commentService.getBaseMapper().selectThreeReplyComment(articleId, record.getId()));
            record.setChildAllNum(commentService.getBaseMapper().countAllReplyComment(record.getId()));
        }
        return commentPage;
    }

    public void commentPraise(Long userId, Long commentId, Integer type) {
        if ((type == Consts.DISABLE ? commentService.getBaseMapper().updatePraiseSub1(commentId) : commentService.getBaseMapper().updatePraiseAdd1(commentId)) == 0) {
            throw new RequestActionException("没有这个评论");
        }
        if (type == Consts.DISABLE) {
            praiseService.getBaseMapper()
                    .delete(
                            new QueryWrapper<MkCommunityPraise>()
                                    .eq("user_id", userId)
                                    .eq("praised_id", commentId)
                                    .eq("type", Consts.PraiseType.COMMENT));
        } else {
            val praise = new MkCommunityPraise();
            praise.setPraisedId(commentId);
            praise.setUserId(userId);
            praise.setType(Consts.PraiseType.COMMENT);
            praiseService.add(praise);
            noticeService.addNotice(userId, commentService.getBaseMapper().selectAuthorIdByCommentId(commentId), commentId, Consts.NoticeType.COMMENT_PRAISE);
        }
    }

    public IPage<FrontArticleCommentDetailsDTO> commentDetailsPage(Long userId, Page<FrontArticleCommentDetailsDTO> page, Long commentId) {
        val commentDetailsPage = commentService.getBaseMapper().commentDetailsPage(page, commentId);
        if (userId != null) {
            for (FrontArticleCommentDetailsDTO record : commentDetailsPage.getRecords()) {
                record.setPraised(praiseService.praiseStatus(record.getId(), userId, Consts.PraiseType.COMMENT));
            }
        }
        return commentDetailsPage;
    }
}
