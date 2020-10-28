package com.github.xujiaji.mk.security.service;

import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.common.base.BaseIService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface IMkSecRoleService extends BaseIService<MkSecRole> {

    /**
     * 角色设置权限列表
     * @param id 角色id
     * @param permissionIds 权限列表
     */
    void roleSetPermissions(Long id, List<Long> permissionIds);
}
