package com.github.xujiaji.mk.security.playload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 管理员添加
 * @author jiajixu
 * @date 2020/10/30 13:47
 */
@Data
public class AdminEditCondition {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 角色ID
     */
    @NotNull(message = "角色不能为空")
    private Long roleId;

    /**
     * 头像文件id
     */
    private Long avatarId;

    /**
     * 密码
     */
    private String password;
}
