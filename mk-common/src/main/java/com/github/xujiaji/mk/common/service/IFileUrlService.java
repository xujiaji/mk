package com.github.xujiaji.mk.common.service;

/**
 * @author jiajixu
 * @date 2020/10/26 23:16
 */
public interface IFileUrlService {

    /**
     *  是否启用自动补全url
     * @param key 字段名
     */
    boolean isEnableUrlAutoFull(String key);

    /**
     *  通过文件的ID或路径等，得到url连接
     */
    String getUrlBy(Object obj);
}
