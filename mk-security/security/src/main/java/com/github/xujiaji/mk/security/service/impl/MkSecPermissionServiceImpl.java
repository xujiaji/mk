package com.github.xujiaji.mk.security.service.impl;

import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.mapper.MkSecPermissionMapper;
import com.github.xujiaji.mk.security.service.IMkSecPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Service
public class MkSecPermissionServiceImpl extends BaseServiceImpl<MkSecPermissionMapper, MkSecPermission> implements IMkSecPermissionService {

    @Override
    public void deletePermissions(List<Long> ids) {
        baseMapper.deleteByIdsParentIds(ids);
    }

    @Override
    public List<MkSecPermission> userPermissions(Long secUserId) {
        return baseMapper.selectPermissionsBySecUserId(secUserId);
    }
}
