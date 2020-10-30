package com.github.xujiaji.mk.common.service;

/**
 * @author jiajixu
 * @date 2020/10/30 14:53
 */
public interface IPasswordService {

    /**
     * 加密原密码
     * @param rawPassword 原密码
     * @return 加密密码
     */
    String encode(CharSequence rawPassword);

    /**
     * 验证密码
     * @param rawPassword 原密码
     * @param encodedPassword 加密密码
     * @return 是否合法
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

}
