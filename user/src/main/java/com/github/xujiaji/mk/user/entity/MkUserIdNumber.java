package com.github.xujiaji.mk.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_user_id_number")
public class MkUserIdNumber extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 0未使用，1已使用
     */
    private Integer state;

    /**
     * 靓号规则（如果是靓号的情况下）
     */
    private String good;


}
