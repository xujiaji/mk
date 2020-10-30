package com.github.xujiaji.mk.security.playload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 管理员添加
 * @author jiajixu
 * @date 2020/10/30 13:47
 */
@Data
public class AdminAddCondition {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色ID
     */
    @NotNull(message = "角色不能为空")
    private Long roleId;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
}
