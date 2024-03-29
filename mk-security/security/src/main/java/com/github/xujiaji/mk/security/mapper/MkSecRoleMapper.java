package com.github.xujiaji.mk.security.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.security.dto.RoleDTO;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface MkSecRoleMapper extends BaseMapper<MkSecRole> {

    List<MkSecRole> selectBySecUserId(@Param("secUserId") Long secUserId);

    int roleSetPermissions(@Param("id") Long id, @Param("permissionIds") List<Long> permissionIds);

    IPage<RoleDTO> rolePage(Page<RoleDTO> page);

    int countRoleByRoleIds(@Param("ids") List<Long> ids);
}
