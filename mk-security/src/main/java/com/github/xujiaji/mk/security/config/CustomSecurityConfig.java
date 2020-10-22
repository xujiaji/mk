package com.github.xujiaji.mk.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义配置
 * @author jiajixu
 * @date 2020/10/22 17:54
 */
@ConfigurationProperties(prefix = "custom.security.config")
@Data
public class CustomSecurityConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
