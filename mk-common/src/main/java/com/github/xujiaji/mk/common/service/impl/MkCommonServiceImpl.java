package com.github.xujiaji.mk.common.service.impl;

import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkCommon;
import com.github.xujiaji.mk.common.mapper.MkCommonMapper;
import com.github.xujiaji.mk.common.service.IMkCommonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-26
 */
@Service
public class MkCommonServiceImpl extends BaseServiceImpl<MkCommonMapper, MkCommon> implements IMkCommonService {


    @Override
    public String valueByKey(String key) {
        return baseMapper.getConfigValueByKey(key);
    }

    @Override
    public int updateValueByKey(String key, String value) {
        return baseMapper.updateValueByKey(key, value);
    }

    @Override
    public List<MkCommon> entitiesByKeys(String... keys) {
        return baseMapper.selectEntitiesByKeys(keys);
    }

    @Override
    public String baseLocalPath() {
        return valueByKey(Consts.ConfigKey.basePath);
    }

    @Override
    public String baseFileUrl() {
        return valueByKey(Consts.ConfigKey.baseFileUrl);
    }
}
