package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态—帖子表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_article")
public class MkCommunityArticle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 话题id
     */
    private Long topicId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Long praiseNum;

    /**
     * 状态 0 待审核 1 审核成功 2审核未通过 3屏蔽
     */
    private Integer state;

    /**
     * 优先级排序
     */
    private Long priority;

    /**
     * 1 显示 0隐藏
     */
    private Integer display;

    /**
     * 收藏数量
     */
    private Integer collectNum;


}
