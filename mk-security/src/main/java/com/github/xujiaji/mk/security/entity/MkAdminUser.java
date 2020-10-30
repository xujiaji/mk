package com.github.xujiaji.mk.security.entity;

import com.github.xujiaji.mk.common.entity.MkUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/10/30 13:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MkAdminUser extends MkUser {

    /**
     * 安全组中的用户ID
     */
    private Long secUserId;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status;
}
