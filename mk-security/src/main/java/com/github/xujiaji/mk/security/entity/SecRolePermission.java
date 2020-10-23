package com.github.xujiaji.mk.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_role_permission")
public class SecRolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色主键
     */
    private Long roleId;

    /**
     * 权限主键
     */
    private Long permissionId;


}
