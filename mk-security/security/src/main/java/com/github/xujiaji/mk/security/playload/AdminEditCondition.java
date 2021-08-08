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
     * 管理员id
     */
    @NotNull(message = "要编辑的管理员id不能为空")
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 角色ID
     */
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
