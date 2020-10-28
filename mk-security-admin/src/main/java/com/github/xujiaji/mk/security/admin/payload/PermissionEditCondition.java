package com.github.xujiaji.mk.security.admin.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 权限编辑约束
 * @author jiajixu
 * @date 2020/10/28 23:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionEditCondition extends PermissionAddCondition {

    /**
     * 权限ID
     */
    @NotNull(message = "请传入权限ID")
    private Long id;

}
