package com.github.xujiaji.mk.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.entity.MkCommon;
import com.github.xujiaji.mk.common.mapper.MkCommonMapper;
import com.github.xujiaji.mk.common.payload.AddConfigCondition;
import com.github.xujiaji.mk.common.payload.EditConfigCondition;
import com.github.xujiaji.mk.common.service.IMkCommonService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;

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
    public void addConfig(AddConfigCondition config) {
        val mkCommon = BeanUtil.copyProperties(config, MkCommon.class);
        checkInsertSuccess(baseMapper.insert(mkCommon));
    }

    @Override
    public void editConfig(EditConfigCondition config) {
        val mkCommon = BeanUtil.copyProperties(config, MkCommon.class);
        checkUpdateSuccess(baseMapper.updateById(mkCommon));
    }
}
