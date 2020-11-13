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
     * 父级id
     */
    private Long parentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 顶级id
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;


}
