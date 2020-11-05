package com.github.xujiaji.mk.security.service.impl;

import com.github.xujiaji.mk.common.service.IPasswordService;
import com.github.xujiaji.mk.security.config.MkSecurityConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 密码处理服务
 * @author jiajixu
 * @date 2020/10/30 14:54
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PasswordServiceImpl extends BCryptPasswordEncoder implements IPasswordService {

    private final MkSecurityConfig securityConfig;

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        return super.encode(securityConfig.getPasswordKey() + rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        if (encodedPassword == null || encodedPassword.length() == 0) {
            log.warn("Empty encoded password");
            return false;
        }
        return super.matches(securityConfig.getPasswordKey() + rawPassword, encodedPassword);
    }
}
