package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息编辑
 * @author jiajixu
 * @date 2020/11/6 09:36
 */
@Data
public class UserEditCondition {

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

}
