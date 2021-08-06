package com.github.xujiaji.mk.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.security.dto.RoleDTO;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.entity.MkSecUserRole;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecRolePermissionMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserRoleMapper;
import com.github.xujiaji.mk.security.service.IMkSecRoleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@RequiredArgsConstructor
@Service
public class MkSecRoleServiceImpl extends BaseServiceImpl<MkSecRoleMapper, MkSecRole> implements IMkSecRoleService {

    private final MkSecUserRoleMapper secUserRoleMapper;
    private final MkSecRolePermissionMapper rolePermissionMapper;

    @Override
    public void roleSetPermissions(Long id, List<Long> permissionIds) {
        checkInsertSuccess(baseMapper.roleSetPermissions(id, permissionIds));
    }

    @Override
    public void deleteRoleById(Long id) {
        val count = secUserRoleMapper.selectCount(new QueryWrapper<MkSecUserRole>().eq("role_id", id));
        if (count > 0) {
            throw new RequestActionException("有管理员处于这个角色，无法删除！请先修改对应管理员角色");
        }
        deleteById(id);
    }

    @Override
    public IPage<RoleDTO> rolePage(Page<RoleDTO> page) {
        val result = baseMapper.rolePage(page);
        for (RoleDTO record : result.getRecords()) {
            record.setPermissions(rolePermissionMapper.selectPermissionByRoleId(record.getId()));
        }
        return result;
    }
}
