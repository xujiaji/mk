package com.github.xujiaji.mk.community.service.impl;

import com.github.xujiaji.mk.community.entity.MkCommunityArticleCollect;
import com.github.xujiaji.mk.community.mapper.MkCommunityArticleCollectMapper;
import com.github.xujiaji.mk.community.service.IMkCommunityArticleCollectService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 社区动态—收藏帖子表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Service
public class MkCommunityArticleCollectServiceImpl extends BaseServiceImpl<MkCommunityArticleCollectMapper, MkCommunityArticleCollect> implements IMkCommunityArticleCollectService {

    @Override
    public int collectStatus(Long articleId, Long userId) {
        return baseMapper.isCollected(articleId, userId);
    }
}
