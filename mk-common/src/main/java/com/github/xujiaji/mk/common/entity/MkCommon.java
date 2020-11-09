package com.github.xujiaji.mk.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_common")
public class MkCommon extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 描述
     */
    private String description;


}
