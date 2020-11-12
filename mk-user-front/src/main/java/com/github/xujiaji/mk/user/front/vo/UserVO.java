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
public class UserVO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

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
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;


}
