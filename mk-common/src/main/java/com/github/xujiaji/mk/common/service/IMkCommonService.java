package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.common.entity.MkCommon;

import java.util.List;

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

    /**
     * 通过key修改值
     * @return 修改成功数量
     */
    int updateValueByKey(String key, String value);

    /**
     * 通过key获取配置实体列表
     * @param keys key列表
     * @return 配置实体列表
     */
    List<MkCommon> entitiesByKeys(String ... keys);

    /**
     * 基础本地存储地址
     * @return
     */
    String baseLocalPath();

    /**
     * 基础链接url
     * @return
     */
    String baseFileUrl();
}
