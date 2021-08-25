package com.github.xujiaji.mk.common.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 配置添加请求
 * @author jiajixu
 * @date 2020/10/26 14:18
 */
@Data
public class EditConfigCondition {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 配置的key
     */
    private String configKey;

    /**
     * 配置的值
     */
    private String configValue;

    /**
     * 配置的描述
     */
    private String description;
}
