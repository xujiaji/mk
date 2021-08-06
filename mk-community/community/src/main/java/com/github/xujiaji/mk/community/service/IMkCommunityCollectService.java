package com.github.xujiaji.mk.community.service;

import com.github.xujiaji.mk.community.entity.MkCommunityCollect;
import com.github.xujiaji.mk.common.base.BaseIService;

/**
 * <p>
 * 社区动态—收藏帖子表 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface IMkCommunityCollectService extends BaseIService<MkCommunityCollect> {
    int collectStatus(Long articleId, Long userId, Integer type);
}
