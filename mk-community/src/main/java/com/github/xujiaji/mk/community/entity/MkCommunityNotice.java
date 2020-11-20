package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 社区动态-消息通知
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_notice")
public class MkCommunityNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 来自哪个用户
     */
    private Long fromUserId;

    /**
     * 到达哪个用户
     */
    private Long toUserId;

    /**
     * 目标id（帖子或评论等的id）
     */
    private Long targetId;

    /**
     * 0未读，1已读
     */
    private Integer state;

    /**
     * 0帖子，1评论，2点赞，3收藏
     */
    private Integer type;


}
