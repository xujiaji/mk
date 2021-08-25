package com.github.xujiaji.mk.security.playload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
     * 昵称
     */
    private String nickname;

    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色id列表不能为空")
    private List<Long> roleIds;

    /**
     * 头像文件id
     */
    private Long avatarId;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
}
