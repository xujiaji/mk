package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信小程序登录请求
 * @author jiajixu
 * @date 2020/8/24 11:33
 */
@Data
public class MiniWxInfoLoginCondition {

    /**
     * 登录时获取的 code
     */
    @NotBlank(message = "请传入登录时获取的 code")
    private String jsCode;

    /**
     * 加密数据
     */
    @NotBlank(message = "请传入加密数据")
    private String encryptedData;

    /**
     * 偏移量
     */
    @NotBlank(message = "请传入偏移量")
    private String iv;
}
