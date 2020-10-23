package com.github.xujiaji.mk.security.mapper;

import com.github.xujiaji.mk.security.entity.SecRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface SecRoleMapper extends BaseMapper<SecRole> {

    List<SecRole> selectBySecUserId(Long secUserId);
}
