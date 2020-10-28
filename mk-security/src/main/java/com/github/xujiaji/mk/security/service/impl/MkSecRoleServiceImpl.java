package com.github.xujiaji.mk.security.service.impl;

import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.service.IMkSecRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Service
public class MkSecRoleServiceImpl extends BaseServiceImpl<MkSecRoleMapper, MkSecRole> implements IMkSecRoleService {

    @Override
    public void roleSetPermissions(Long id, List<Long> permissionIds) {
        checkInsertSuccess(baseMapper.roleSetPermissions(id, permissionIds));
    }
}
