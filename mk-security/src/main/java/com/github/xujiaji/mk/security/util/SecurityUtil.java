package com.github.xujiaji.mk.security.util;

import cn.hutool.core.util.ObjectUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Spring Security工具类
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户用户名
     *
     * @return 当前登录用户用户名
     */
    public static String getCurrentUsername() {
        UserPrincipal currentUser = getCurrentUser();
        return ObjectUtil.isNull(currentUser) ? Consts.ANONYMOUS_NAME : currentUser.getUsername();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息，匿名登录时，为null
     */
    public static UserPrincipal getCurrentUser() {
        Object userInfo = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (userInfo instanceof UserDetails) {
            return (UserPrincipal) userInfo;
        }
        return null;
    }
}
