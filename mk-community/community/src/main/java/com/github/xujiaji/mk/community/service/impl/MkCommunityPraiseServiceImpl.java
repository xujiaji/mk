package com.github.xujiaji.mk.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.community.entity.MkCommunityPraise;
import com.github.xujiaji.mk.community.mapper.MkCommunityPraiseMapper;
import com.github.xujiaji.mk.community.service.IMkCommunityPraiseService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 社区动态—点赞 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Service
public class MkCommunityPraiseServiceImpl extends BaseServiceImpl<MkCommunityPraiseMapper, MkCommunityPraise> implements IMkCommunityPraiseService {

    @Override
    public int praiseStatus(Long praisedId, Long userId, Integer type) {
        val count = baseMapper.selectCount(
                new QueryWrapper<MkCommunityPraise>()
                        .eq("user_id", userId)
                        .eq("praised_id", praisedId)
                        .eq("type", type)
        );
        return count == null ? 0 : count;
    }
}
