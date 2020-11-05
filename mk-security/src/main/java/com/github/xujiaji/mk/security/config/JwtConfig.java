package com.github.xujiaji.mk.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置
 * @author jiajixu
 */
@ConfigurationProperties(prefix = "mk.jwt.config")
@Data
public class JwtConfig {

    /**
     * jwt 加密 key，默认值：mk-security!!!...
     */
    private String key = "mk-security!!!...";

    /**
     * JWT 在 Redis 中保存的key前缀
     */
    private String redisJwtKeyPrefix = "mk:security:jwt:";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;
}
