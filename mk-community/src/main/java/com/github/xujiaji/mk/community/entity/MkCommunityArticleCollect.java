package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态—收藏帖子表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_article_collect")
public class MkCommunityArticleCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子guid
     */
    private String articleId;

    /**
     * 用户guid
     */
    private Long userId;


}
