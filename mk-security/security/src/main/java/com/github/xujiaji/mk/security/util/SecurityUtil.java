package com.github.xujiaji.mk.security.util;

import cn.hutool.core.util.ObjectUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.exception.StatusException;
import com.github.xujiaji.mk.security.vo.MkSecUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        MkSecUserDetails currentUser = getCurrentUser();
        return ObjectUtil.isNull(currentUser) ? Consts.ANONYMOUS_NAME : currentUser.getUsername();
    }

    /**
     * 获取当前登录用户Sec ID
     * @return 当前用户Sec ID
     */
    public static Long getCurrentUserId() {
        MkSecUserDetails currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new StatusException(Status.UNAUTHORIZED);
        }
        return currentUser.getId();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息，匿名登录时，为null
     */
    public static MkSecUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object userInfo = authentication.getPrincipal();
        if (userInfo instanceof MkSecUserDetails) {
            return (MkSecUserDetails) userInfo;
        }
        return null;
    }
}
