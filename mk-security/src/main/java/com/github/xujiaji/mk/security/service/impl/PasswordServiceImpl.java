package com.github.xujiaji.mk.security.service.impl;

import com.github.xujiaji.mk.common.service.IPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 密码处理服务
 * @author jiajixu
 * @date 2020/10/30 14:54
 */
@RequiredArgsConstructor
@Service
public class PasswordServiceImpl implements IPasswordService {

    private final BCryptPasswordEncoder encoder;

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
