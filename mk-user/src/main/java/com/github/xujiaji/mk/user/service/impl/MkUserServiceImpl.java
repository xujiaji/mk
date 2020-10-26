package com.github.xujiaji.mk.user.service.impl;

import com.github.xujiaji.mk.user.entity.MkUser;
import com.github.xujiaji.mk.user.mapper.MkUserMapper;
import com.github.xujiaji.mk.user.service.IMkUserService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@Service
public class MkUserServiceImpl extends BaseServiceImpl<MkUserMapper, MkUser> implements IMkUserService {

    @Override
    public MkUser info(Long id) {
        return baseMapper.selectById(id);
    }
}
