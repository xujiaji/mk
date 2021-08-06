package com.github.xujiaji.mk.security.admin.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 角色添加约束
 * @author jiajixu
 * @date 2020/10/28 14:34
 */
@Data
public class RoleEditCondition {

    /**
     * id
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 角色名
     */
    @NotNull(message = "角色名不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;
}
