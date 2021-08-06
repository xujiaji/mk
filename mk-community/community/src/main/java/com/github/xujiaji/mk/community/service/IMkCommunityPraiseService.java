package com.github.xujiaji.mk.community.service;

import com.github.xujiaji.mk.community.entity.MkCommunityPraise;
import com.github.xujiaji.mk.common.base.BaseIService;

/**
 * <p>
 * 社区动态—点赞 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface IMkCommunityPraiseService extends BaseIService<MkCommunityPraise> {

    /**
     * 点赞状态
     * @param praisedId
     * @param userId
     * @param type 0点赞帖子 1点赞评论
     * @return
     */
    int praiseStatus(Long praisedId, Long userId, Integer type);
}
