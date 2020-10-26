package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.common.entity.MkCommon;

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
     * 通过key得到值
     */
    String valueByKey(String key);
}
