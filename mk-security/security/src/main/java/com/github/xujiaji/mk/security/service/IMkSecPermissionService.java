package com.github.xujiaji.mk.security.service;

import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.common.base.BaseIService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface IMkSecPermissionService extends BaseIService<MkSecPermission> {

    /**
     * 删除权限
     * @param ids 权限ID列表
     */
    void deletePermissions(List<Long> ids);

    /**
     * 用户的所有权限
     * @param secUserId 权限用户ID
     * @return 权限列表
     */
    List<MkSecPermission> userPermissions(Long secUserId);
}
