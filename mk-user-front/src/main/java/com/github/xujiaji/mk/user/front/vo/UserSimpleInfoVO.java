package com.github.xujiaji.mk.user.front.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
@Data
public class UserSimpleInfoVO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户编号
     */
    private String no;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像文件id
     */
    private String avatar;

    /**
     * 1男 2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 简介个性签名
     */
    private String bio;

    /**
     * app年龄
     */
    private String birthInApp;

    /**
     * 多少后如90后00后
     */
    private String afterYear;

    /**
     * 星座
     */
    private String constellation;

}
