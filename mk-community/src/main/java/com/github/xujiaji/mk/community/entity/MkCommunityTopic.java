package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态—话题表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_article_topic")
public class MkCommunityTopic extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 话题图片
     */
    private Long image;

    /**
     * 略缩图
     */
    private Long thumb;

    /**
     * 话题描述
     */
    private String content;

    /**
     * 优先级排序
     */
    private Integer priority;


}
