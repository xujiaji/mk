package com.github.xujiaji.mk.security.service.impl;

import com.github.xujiaji.mk.common.service.IPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 密码处理服务
 * @author jiajixu
 * @date 2020/10/30 14:54
 */
@Service
public class PasswordServiceImpl implements IPasswordService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
