package com.github.xujiaji.mk.security.admin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jiajixu
 * @date 2020/11/9 10:06
 */
@Slf4j
public class OtherTests {
    @Test
    public void testPassword() {
        log.info("密码：{}", new BCryptPasswordEncoder().encode("security-password-key" + "123123"));
    }
}
