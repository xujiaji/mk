package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态—分类表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_article_category")
public class MkCommunityCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 1 显示 0 隐藏
     */
    private Integer display;

    /**
     * 标题
     */
    private String title;

    /**
     * 优先级排序
     */
    private Integer priority;

    /**
     * 封面
     */
    private Long thumb;

}
