package com.github.xujiaji.mk.common.config;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花主键生成器
 */
@Configuration
public class IdConfig {

    /**
     * 雪花生成器
     */
    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }
}
