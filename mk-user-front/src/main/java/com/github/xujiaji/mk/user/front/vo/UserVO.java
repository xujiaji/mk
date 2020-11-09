package com.github.xujiaji.mk.user.front.vo;

import com.github.xujiaji.mk.common.entity.BaseWithIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseWithIdEntity {

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
