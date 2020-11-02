package com.github.xujiaji.mk.common.util;

import com.github.xujiaji.mk.common.base.Consts;

/**
 * 正则工具类
 * @author jiajixu
 * @date 2020/11/2 14:30
 */
public class RegexpUtil {

    /**
     * 验证是否是手机号
     * @param phoneNumber 手机号
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(Consts.Regexp.PHONE);
    }
}
