package com.github.xujiaji.mk.security.entity;

import com.github.xujiaji.mk.common.entity.MkUserLoginLog;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiajixu
 * @date 2020/10/30 13:29
 */
@Data
public class MkAdminUser {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    /**
     * 用户名
     */
    private String username;

    /**
     * id编号
     */
    private String no;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 1男 2女
     */
    private Integer sex;

    /**
     * 简介个性签名
     */
    private String bio;

    /**
     * 生日
     */
    private LocalDateTime birthday;

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

    /**
     * 安全组中的用户ID
     */
    private Long secUserId;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status;

    /**
     * 角色
     */
    private String role;

    /**
     * 登录日志
     */
    private MkUserLoginLog loginLog;
}
