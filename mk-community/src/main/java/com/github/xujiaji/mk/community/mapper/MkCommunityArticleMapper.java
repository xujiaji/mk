package com.github.xujiaji.mk.community.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.community.dto.FrontArticleDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 社区动态—帖子表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface MkCommunityArticleMapper extends BaseMapper<MkCommunityArticle> {

    Page<FrontArticleDTO> articlePage(Page<FrontArticleDTO> page, @Param("categoryId") Long categoryId, @Param("type") Integer type);

    FrontArticleDTO selectArticleDetails(@Param("articleId") Long articleId);

    int updateCollectAdd1(@Param("articleId") Long articleId);

    int updatePraiseAdd1(@Param("articleId") Long articleId);

    int updateCollectSub1(@Param("articleId") Long articleId);

    int updatePraiseSub1(@Param("articleId") Long articleId);
}
