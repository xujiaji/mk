package com.github.xujiaji.mk.user.service;

import com.github.xujiaji.mk.user.entity.MkSms;
import com.github.xujiaji.mk.common.base.BaseIService;

/**
 * <p>
 * 短信验证码表 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
public interface IMkSmsService extends BaseIService<MkSms> {

    /**
     * 是否过期
     * @param mobile 手机号
     * @param type 验证码类型
     * @param code 验证码
     * @return 是否过期
     */
    boolean isValid(String mobile, int type, int code);
}
