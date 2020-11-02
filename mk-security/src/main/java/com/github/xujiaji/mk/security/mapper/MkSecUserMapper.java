package com.github.xujiaji.mk.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface MkSecUserMapper extends BaseMapper<MkSecUser> {
    /**
     * 通过用户ID获取管理用户信息
     * @param userId 用户ID
     */
    MkSecUser selectByUserId(@Param("userId") Long userId);
}
