package com.github.xujiaji.mk.user.mapper;

import com.github.xujiaji.mk.user.entity.MkAppVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * App版本管理 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-19
 */
public interface MkAppVersionMapper extends BaseMapper<MkAppVersion> {

    /**
     * 获取当前版本
     * @return
     */
    MkAppVersion selectCurrentVersion();
}
