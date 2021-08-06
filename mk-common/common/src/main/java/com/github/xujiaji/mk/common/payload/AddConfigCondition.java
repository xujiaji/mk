package com.github.xujiaji.mk.common.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 配置添加请求
 * @author jiajixu
 * @date 2020/10/26 14:18
 */
@Data
public class AddConfigCondition {

    /**
     * 标题
     */
    @NotNull(message = "请输入标题")
    private String title;

    /**
     * 配置的key
     */
    @NotNull(message = "请输入配置的key")
    private String configKey;

    /**
     * 配置的值
     */
    @NotNull(message = "请输入配置的值")
    private String configValue;

    /**
     * 配置的描述
     */
    @NotNull(message = "请输入配置的描述")
    private String description;
}
