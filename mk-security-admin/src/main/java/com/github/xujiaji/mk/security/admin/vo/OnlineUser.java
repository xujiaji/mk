package com.github.xujiaji.mk.security.admin.vo;

import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.security.entity.IUser;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 在线用户 VO
 * </p>
 */
@Data
public class OnlineUser {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * secUserId
     */
    private Long secUserId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 性别，男-1，女-2
     */
    private Integer sex;

    public static OnlineUser create(MkSecUser mkSecUser, IUser iUser) {
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setSecUserId(mkSecUser.getId());
        onlineUser.setUserId(mkSecUser.getUserId());
        onlineUser.setNickname(iUser.getNickname());
        // 脱敏
        onlineUser.setPhone(StrUtil.hide(iUser.getPhone(), 3, 7));
        onlineUser.setEmail(StrUtil.hide(iUser.getEmail(), 1, StrUtil.indexOfIgnoreCase(iUser.getEmail(), Consts.SYMBOL_EMAIL)));
        onlineUser.setBirthday(iUser.getBirthday());
        onlineUser.setSex(iUser.getSex());
        return onlineUser;
    }
}
