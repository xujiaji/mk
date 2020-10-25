package com.github.xujiaji.mk.auth.service.impl;

import com.github.xujiaji.mk.auth.entity.MkAuthUser;
import com.github.xujiaji.mk.auth.mapper.MkAuthUserMapper;
import com.github.xujiaji.mk.auth.service.IMkAuthUserService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 认证用户 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@Service
public class MkAuthUserServiceImpl extends BaseServiceImpl<MkAuthUserMapper, MkAuthUser> implements IMkAuthUserService {

}
