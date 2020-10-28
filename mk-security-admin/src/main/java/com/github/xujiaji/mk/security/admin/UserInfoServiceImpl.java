package com.github.xujiaji.mk.security.admin;

import com.github.xujiaji.mk.common.entity.IUser;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author jiajixu
 * @date 2020/10/28 15:43
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Override
    public IUser getUserWithPhoneEmailPassword(Long userId) {
        return new IUser() {
            @Override
            public Long getId() {
                return 0L;
            }

            @Override
            public String getNickname() {
                return "xu";
            }

            @Override
            public String getPassword() {
                return "1234";
            }

            @Override
            public Integer getSex() {
                return 1;
            }

            @Override
            public String getPhone() {
                return "123";
            }

            @Override
            public String getEmail() {
                return "123@jj.com";
            }

            @Override
            public LocalDateTime getBirthday() {
                return LocalDateTime.now();
            }
        };
    }
}
