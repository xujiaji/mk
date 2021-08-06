package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态—留言表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_comment")
public class MkCommunityComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 顶级id
     */
    private Long rootId;

    /**
     * 父级评论id
     */
    private Long parentId;

    /**
     * 回复的回复id
     */
    private Long replyId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Long praiseNum;

}
