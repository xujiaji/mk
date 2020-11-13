package com.github.xujiaji.mk.community.front.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleDTO;
import com.github.xujiaji.mk.community.entity.*;
import com.github.xujiaji.mk.community.front.playload.ArticleCommentAddCondition;
import com.github.xujiaji.mk.community.front.playload.CommunityArticleAddCondition;
import com.github.xujiaji.mk.community.service.impl.*;
import com.github.xujiaji.mk.file.service.IMkFileService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

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
public class MkFrontCommunityArticleService extends MkCommunityArticleServiceImpl {

    private final IMkFileService fileService;
    private final MkCommunityArticleFileServiceImpl articleFileService;
    private final MkCommunityPraiseServiceImpl praiseService;
    private final MkCommunityCollectServiceImpl collectService;
    private final MkCommunityCommentServiceImpl commentService;
    private final CommonUtil commonUtil;

    @Transactional(rollbackFor = Exception.class)
    public void addByRequest(Long userId, CommunityArticleAddCondition request) {
        val article = BeanUtil.copyProperties(request, MkCommunityArticle.class);
        article.setUserId(userId);
        // 添加文章到数据库
        add(article);
        for (int i = 0; i < request.getImages().size(); i++) {
            val mkFile = fileService.uploadBase64(userId, request.getImages().get(i), Consts.FileType.IMAGE);
            val articleFile = new MkCommunityArticleFile();
            articleFile.setArticleId(article.getId());
            articleFile.setFileId(mkFile.getId());
            articleFile.setType(Consts.ArticleFileType.IMAGE);
            articleFile.setOrderNum(i);
            articleFileService.add(articleFile);

            val mkThumbnailFile = fileService.generateThumbnail(mkFile);
            val articleThumbnailFile = new MkCommunityArticleFile();
            articleThumbnailFile.setArticleId(article.getId());
            articleThumbnailFile.setFileId(mkThumbnailFile.getId());
            articleThumbnailFile.setType(Consts.ArticleFileType.IMAGE_THUMBNAIL);
            articleThumbnailFile.setOrderNum(i);
            articleFileService.add(articleThumbnailFile);
        }

    }

    private void buildArticles(Long userId, List<FrontArticleDTO> articleDTOS) {
        for (FrontArticleDTO a : articleDTOS) {
            if (userId != null) {
                a.setCollected(collectService.collectStatus(a.getId(), userId, Consts.CollectType.ARTICLE));
                a.setPraised(praiseService.praiseStatus(a.getId(), userId, Consts.PraiseType.ARTICLE));
            }
            a.setBeforeText(commonUtil.getShortTime(a.getUpdateTime()));
            a.setThumbnails(articleFileService.getUrlsByArticleId(a.getId(), Consts.ArticleFileType.IMAGE_THUMBNAIL));
            a.setImages(articleFileService.getUrlsByArticleId(a.getId(), Consts.ArticleFileType.IMAGE));
        }
    }

    public IPage<FrontArticleDTO> articlePage(Long userId, Page<FrontArticleDTO> page, Long categoryId, Integer type) {
        val articlePage = baseMapper.articlePage(page, categoryId, type);
        buildArticles(userId, articlePage.getRecords());
        return articlePage;
    }

    public FrontArticleDTO details(@Nullable Long userId, Long articleId) {
        val article = baseMapper.selectArticleDetails(articleId);
        buildArticles(userId, Lists.newArrayList(article));
        return article;
    }

    public void articleCollect(Long userId, Long articleId, Integer type) {
        if ((type == Consts.DISABLE ? baseMapper.updateCollectSub1(articleId) : baseMapper.updateCollectAdd1(articleId)) == 0) {
            throw new RequestActionException("没有这个动态");
        }
        if (type == Consts.DISABLE) {
            collectService.getBaseMapper()
                    .delete(
                            new QueryWrapper<MkCommunityCollect>()
                                    .eq("user_id", userId)
                                    .eq("collected_id", articleId)
                                    .eq("type", Consts.CollectType.ARTICLE));
        } else {
            val collect = new MkCommunityCollect();
            collect.setCollectedId(articleId);
            collect.setUserId(userId);
            collect.setType(Consts.CollectType.ARTICLE);
            collectService.add(collect);
        }
    }

    public void articlePraise(Long userId, Long articleId, Integer type) {
        if ((type == Consts.DISABLE ? baseMapper.updatePraiseSub1(articleId) : baseMapper.updatePraiseAdd1(articleId)) == 0) {
            throw new RequestActionException("没有这个动态");
        }
        if (type == Consts.DISABLE) {
            praiseService.getBaseMapper()
                    .delete(
                            new QueryWrapper<MkCommunityPraise>()
                                    .eq("user_id", userId)
                                    .eq("praised_id", articleId)
                                    .eq("type", Consts.PraiseType.ARTICLE));
        } else {
            val praise = new MkCommunityPraise();
            praise.setPraisedId(articleId);
            praise.setUserId(userId);
            praise.setType(Consts.PraiseType.ARTICLE);
            praiseService.add(praise);
        }
    }

    public void commentAdd(Long userId, ArticleCommentAddCondition request) {
        val comment = new MkCommunityComment();
        comment.setRootId(request.getArticleId());
        // 如果是回复
        if (request.getReplyId() != null) {
            // 获取回复的评论，然后设置父级评论
            val replyComment = commentService.getById(request.getReplyId());
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
        }
    }

    public IPage<FrontArticleCommentDTO> commentPage(Long userId, Page<FrontArticleCommentDTO> page, Long articleId, Integer type) {
        val commentPage = commentService.getBaseMapper().articleCommentPage(page, articleId, type);
        for (FrontArticleCommentDTO record : commentPage.getRecords()) {
            if (userId != null) {
                record.setPraised(praiseService.praiseStatus(record.getId(), userId, Consts.PraiseType.COMMENT));
            }
            record.setChild(commentService.getBaseMapper().selectThreeReplyComment(articleId, record.getId()));
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
        }
    }
}