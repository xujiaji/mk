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
    @NotNull(message = "用户名不能为空")
    private String username;

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
