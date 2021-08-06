package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态—关注表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_follow")
public class MkCommunityFollow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关注的用户id  或者是关注的帖子id
     */
    private String followedId;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 0 关注的用户 1关注的话题
     */
    private Integer type;


}
