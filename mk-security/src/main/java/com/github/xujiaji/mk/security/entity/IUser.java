package com.github.xujiaji.mk.security.entity;

import java.time.LocalDateTime;

/**
 * 用户扩展用户表，来补充用户信息
 * @author jiajixu
 * @date 2020/10/23 22:38
 */
public interface IUser {
    Long getId();
    String getNickname();
    String getPassword();
    Integer getSex();
    String getPhone();
    LocalDateTime getBirthday();
}
