package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.entity.IUser;

/**
 * @author jiajixu
 * @date 2020/10/24 01:00
 */
public interface IUserInfoService {
    IUser getUserWithPhoneEmailPassword(Long userId);
}
