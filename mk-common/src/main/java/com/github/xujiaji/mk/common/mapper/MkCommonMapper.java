package com.github.xujiaji.mk.common.mapper;

import com.github.xujiaji.mk.common.entity.MkCommon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-26
 */
public interface MkCommonMapper extends BaseMapper<MkCommon> {

    /**
     * 通过key得到配置value
     * @param key key
     * @return 配置值
     */
    String getConfigValueByKey(@Param("key") String key);

    /**
     * 通过key列表获取配置实体列表
     * @param keys key列表
     * @return 配置实体列表
     */
    List<MkCommon> selectEntitiesByKeys(@Param("keys") String[] keys);
}
