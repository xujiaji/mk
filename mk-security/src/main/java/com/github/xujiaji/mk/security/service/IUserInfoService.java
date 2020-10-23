package com.github.xujiaji.mk.security.service;

import com.github.xujiaji.mk.security.entity.IUser;

/**
 * @author jiajixu
 * @date 2020/10/24 01:00
 */
public interface IUserInfoService {
    IUser getUser(Long userId);
}
