package com.github.xujiaji.mk.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 认证用户日志
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_user_login_log")
public class MkUserLoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 1.QQ登录；2.微信登录；3.手机密码登录；4.手机验证码登录；5.邮箱登录；6.iOS登录；7.用户名密码登录；8.微信小程序
     */
    private Integer loginType;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 登录设备
     */
    private String loginDevice;


}
