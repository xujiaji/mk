package com.github.xujiaji.mk.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_user_login_log_view")
public class MkUserLoginLogView extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像文件id
     */
    private Long avatar;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 1.QQ登录；2.微信登录；3.手机密码登录；4.手机验证码登录；5.邮箱登录；6.iOS登录；7.用户名密码登录
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
