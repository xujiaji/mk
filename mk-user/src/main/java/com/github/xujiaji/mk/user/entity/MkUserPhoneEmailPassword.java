package com.github.xujiaji.mk.user.entity;

import com.github.xujiaji.mk.common.entity.IUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/10/26 11:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MkUserPhoneEmailPassword extends MkUser implements IUser {
    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
