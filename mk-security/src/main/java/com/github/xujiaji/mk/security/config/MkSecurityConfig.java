package com.github.xujiaji.mk.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义配置
 * @author jiajixu
 * @date 2020/10/22 17:54
 */
@ConfigurationProperties(prefix = "mk.security.config")
@Data
public class MkSecurityConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;

    /**
     * 是否开启API版本管理
     */
    private Boolean enableApiVersion = false;

    /**
     * 是否开启API请求加密
     */
    private Boolean enableApiRequestEncrypt = true;

    /**
     * 开启API请求加密后，排除的不加密的接口
     */
    private IgnoreConfig apiRequestEncryptExclude;
}
