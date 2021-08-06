package com.github.xujiaji.mk.community.service;

import com.github.xujiaji.mk.community.entity.MkCommunityNotice;
import com.github.xujiaji.mk.common.base.BaseIService;

/**
 * <p>
 * 社区动态-消息通知 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-19
 */
public interface IMkCommunityNoticeService extends BaseIService<MkCommunityNotice> {

    /**
     * 添加通知
     * @param fromUserId
     * @param toUserId
     * @param articleId
     * @param commentId
     * @param type
     */
    void addNotice(Long fromUserId, Long toUserId, Long articleId, Long commentId, int type);
}
