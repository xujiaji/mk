package com.github.xujiaji.mk.common.admin.service;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.entity.MkCommon;
import com.github.xujiaji.mk.common.payload.AddConfigCondition;
import com.github.xujiaji.mk.common.payload.EditConfigCondition;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * @author jiajixu
 * @date 2020/10/26 22:17
 */
@Service
public class MkCommonAdminService extends MkCommonServiceImpl {

    /**
     * 添加配置
     */
    public void addConfig(AddConfigCondition config) {
        val mkCommon = BeanUtil.copyProperties(config, MkCommon.class);
        checkInsertSuccess(baseMapper.insert(mkCommon));
    }

    /**
     * 编辑配置
     */
    public void editConfig(EditConfigCondition config) {
        val mkCommon = BeanUtil.copyProperties(config, MkCommon.class);
        checkUpdateSuccess(baseMapper.updateById(mkCommon));
    }

}
