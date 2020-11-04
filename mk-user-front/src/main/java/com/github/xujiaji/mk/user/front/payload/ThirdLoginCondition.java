package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 三方登录校验
 * @author jiajixu
 */
@Data
public class ThirdLoginCondition {
    /**
     * 1 qq
     * 2 微信
     * 6 iOS
     */
    @NotNull(message = "三方类型不能为NULL")
    @Pattern(regexp = "[126]", message = "没有该类型")
    private Integer type;
    /**
     * qq和微信 三方登录token
     */
    private String tokenId;

    /**
     * 苹果登录校验
     * 苹果票据 很长很长的一串
     */
    private String jwt;

    /**
     * iOS包名
     */
    private String aud = "com.baozao.yunyu";

    /**
     * 苹果对应的唯一用户标识
     */
    private String sub;
}
