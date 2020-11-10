package com.github.xujiaji.mk.user.dto;

import lombok.Data;

/**
 * @author jiajixu
 * @date 2020/11/10 10:08
 */
@Data
public class ThirdBindStatusDTO {
    /**
     * QQ绑定状态
     */
    private Integer qqStatus;
    /**
     * 微信绑定状态
     */
    private Integer wxStatus;
    /**
     * 微信小程序绑定状态
     */
    private Integer wxMiniStatus;
    /**
     * iOS绑定状态
     */
    private Integer iosStatus;
    /**
     * 手机号绑定状态
     */
    private Integer phoneStatus;
    /**
     * 邮箱绑定状态
     */
    private Integer emailStatus;
}
