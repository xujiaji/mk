package com.github.xujiaji.mk.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 认证用户
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_auth_user")
public class MkAuthUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * qq id 标识
     */
    private String qqId;

    /**
     * 微信id标识
     */
    private String wxId;

    /**
     * 微信小程序openId
     */
    private String wxMiniOpenId;

    /**
     * iOS id标识
     */
    private String iosId;


}
