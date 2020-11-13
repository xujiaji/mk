package com.github.xujiaji.mk.community.service.impl;

import com.github.xujiaji.mk.community.entity.MkCommunityCollect;
import com.github.xujiaji.mk.community.mapper.MkCommunityCollectMapper;
import com.github.xujiaji.mk.community.service.IMkCommunityCollectService;
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
public class MkCommunityCollectServiceImpl extends BaseServiceImpl<MkCommunityCollectMapper, MkCommunityCollect> implements IMkCommunityCollectService {

    @Override
    public int collectStatus(Long articleId, Long userId, Integer type) {
        return baseMapper.isCollected(articleId, userId, type);
    }
}
