package com.github.xujiaji.mk.security.mapper;

import com.github.xujiaji.mk.security.entity.MkSecUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface MkSecUserRoleMapper extends BaseMapper<MkSecUserRole> {

    int addSecUserRole(@Param("secUserId") Long secUserId, @Param("roleId") Long roleId);
}
