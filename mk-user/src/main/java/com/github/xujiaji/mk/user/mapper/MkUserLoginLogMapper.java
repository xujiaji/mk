package com.github.xujiaji.mk.user.mapper;

import com.github.xujiaji.mk.common.entity.MkUserLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 认证用户日志 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-30
 */
public interface MkUserLoginLogMapper extends BaseMapper<MkUserLoginLog> {

    int countTodayLog(@Param("userId") Long userId);

    MkUserLoginLog lastLoginLogBy(@Param("userId") Long userId);
}
