package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.entity.MkCommon;
import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.common.payload.AddConfigCondition;
import com.github.xujiaji.mk.common.payload.EditConfigCondition;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-26
 */
public interface IMkCommonService extends BaseIService<MkCommon> {

    /**
     * 添加配置
     */
    void addConfig(AddConfigCondition config);

    /**
     * 编辑配置
     */
    void editConfig(EditConfigCondition config);
}
