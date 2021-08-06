package com.github.xujiaji.mk.community.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDetailsDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 社区动态—留言表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface MkCommunityCommentMapper extends BaseMapper<MkCommunityComment> {

    IPage<FrontArticleCommentDTO> articleCommentPage(Page<FrontArticleCommentDTO> page, @Param("articleId") Long articleId, @Param("type") Integer type);

    List<FrontArticleCommentDTO> selectThreeReplyComment(@Param("articleId") Long articleId, @Param("commentId") Long commentId);

    long countAllReplyComment(@Param("commentId") Long commentId);

    int updatePraiseAdd1(@Param("commentId") Long commentId);

    int updatePraiseSub1(@Param("commentId") Long commentId);

    IPage<FrontArticleCommentDetailsDTO> commentDetailsPage(Page<FrontArticleCommentDetailsDTO> page, @Param("commentId") Long commentId);

    Long selectAuthorIdByCommentId(@Param("commentId") Long commentId);
}
