package com.github.xujiaji.mk.auth.service.impl;

import com.github.xujiaji.mk.auth.entity.AuthUserLog;
import com.github.xujiaji.mk.auth.mapper.AuthUserLogMapper;
import com.github.xujiaji.mk.auth.service.IAuthUserLogService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 认证用户日志 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@Service
public class AuthUserLogServiceImpl extends BaseServiceImpl<AuthUserLogMapper, AuthUserLog> implements IAuthUserLogService {

}
