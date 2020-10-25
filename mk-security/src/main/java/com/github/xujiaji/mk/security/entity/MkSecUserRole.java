package com.github.xujiaji.mk.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_user_role")
public class MkSecUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    private Long secUserId;

    /**
     * 角色主键
     */
    private Long roleId;


}
