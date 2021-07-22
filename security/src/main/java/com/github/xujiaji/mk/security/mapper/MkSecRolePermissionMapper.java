package com.github.xujiaji.mk.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.entity.MkSecRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface MkSecRolePermissionMapper extends BaseMapper<MkSecRolePermission> {

    List<MkSecPermission> selectPermissionByRoleId(@Param("roleId") Long roleId);
}
