package com.github.xujiaji.mk.common.mapper;

import com.github.xujiaji.mk.common.entity.MkCommon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
}
