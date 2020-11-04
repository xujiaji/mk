package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 微信小程序登录请求
 * @author jiajixu
 * @date 2020/8/24 11:33
 */
@Data
public class MiniWxLoginCondition {

    /**
     * 登录时获取的 code
     */
    @NotNull(message = "请传入登录时获取的 code")
    private String jsCode;
}
