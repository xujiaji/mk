package com.github.xujiaji.mk.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jiajixu
 * @date 2020/11/5 11:27
 */
@Slf4j
public class UserTests {

    @Test
    void testPassword() {
        log.info("密码验证：{}",
                new BCryptPasswordEncoder()
                        .matches("123456",
                                "$2a$10$COJbRpOuGUj81bnWQ8LwzeD6eYKXeUKKDOAR/X4rbjpza9/RIEDxC"
                        )
        );
    }
}
