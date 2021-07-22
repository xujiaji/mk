package com.github.xujiaji.mk.common.config;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * API版本控制
 * @author jiajixu
 * @date 2020/11/3 00:08
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    /**
     * 版本
     */
    String value();
}
