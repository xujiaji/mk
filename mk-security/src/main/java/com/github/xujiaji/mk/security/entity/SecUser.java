package com.github.xujiaji.mk.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_user")
public class SecUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status;


}
