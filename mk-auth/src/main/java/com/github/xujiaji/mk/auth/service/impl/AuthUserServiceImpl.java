package com.github.xujiaji.mk.auth.service.impl;

import com.github.xujiaji.mk.auth.entity.AuthUser;
import com.github.xujiaji.mk.auth.mapper.AuthUserMapper;
import com.github.xujiaji.mk.auth.service.IAuthUserService;
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
public class AuthUserServiceImpl extends BaseServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {

}
