package com.github.xujiaji.mk.security.service.impl;

import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.mapper.MkSecPermissionMapper;
import com.github.xujiaji.mk.security.service.IMkSecPermissionService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import lombok.val;
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
    public void deletePermission(Long id) {
        val childCount = baseMapper.countByParentId(id);
        if (childCount > 0) {
            throw new RequestActionException("如果要删除这个父级权限，请先删除下面的所有子级权限");
        }
        deleteById(id);
    }

    @Override
    public List<MkSecPermission> userPermissions(Long secUserId) {
        return baseMapper.selectPermissionsBySecUserId(secUserId);
    }
}
