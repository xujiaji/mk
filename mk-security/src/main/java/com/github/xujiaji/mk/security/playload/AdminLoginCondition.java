package com.github.xujiaji.mk.security.playload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/2 10:57
 */
@Data
public class AdminLoginCondition {
    /**
     * 用户名
     */
    @NotNull(message = "请输入用户名")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "请输入密码")
    private String password;

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String code;

    /**
     * 验证码标识号
     */
    @NotNull(message = "验证码标识号不能为空")
    private String verify;

    /**
     * 记住登录，记住：7天，不记住10分钟
     */
    @NotNull(message = "请传入是否记住账号")
    private Boolean rememberMe;
}
