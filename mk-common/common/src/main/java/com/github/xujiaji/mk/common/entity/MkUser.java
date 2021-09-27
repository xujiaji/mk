package com.github.xujiaji.mk.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_user")
public class MkUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 头像文件id
     */
    private Long avatar;

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
     * 启用状态，1启用，0禁用
     */
    private Integer enableStatus;
}
