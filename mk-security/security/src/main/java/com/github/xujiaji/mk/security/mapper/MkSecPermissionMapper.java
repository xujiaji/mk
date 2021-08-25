package com.github.xujiaji.mk.security.mapper;

import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface MkSecPermissionMapper extends BaseMapper<MkSecPermission> {

    List<MkSecPermission> selectByRoleIdList(@Param("roleIds") List<Long> roleIds);

    void deleteByIdsParentIds(@Param("ids") List<Long> ids);

    List<MkSecPermission> selectPermissionsBySecUserId(@Param("secUserId") Long secUserId);
}
