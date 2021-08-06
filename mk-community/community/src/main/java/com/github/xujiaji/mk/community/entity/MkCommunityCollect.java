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
@TableName("mk_community_collect")
public class MkCommunityCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 被收藏的id
     */
    private Long collectedId;

    /**
     * 用户guid
     */
    private Long userId;

    /**
     * 0收藏帖子 1收藏留言
     */
    private Integer type;

}
