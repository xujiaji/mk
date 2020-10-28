package com.github.xujiaji.mk.security.admin.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色设置权限约束
 * @author jiajixu
 * @date 2020/10/28 15:16
 */
@Data
public class RoleSetPermissionsCondition {

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    private Long id;

    /**
     * 权限id列表
     */
    @NotEmpty(message = "权限id列表不能为空")
    private List<Long> permissionIds;
}
