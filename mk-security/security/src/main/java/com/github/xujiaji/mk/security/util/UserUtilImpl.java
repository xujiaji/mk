package com.github.xujiaji.mk.security.util;

import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.util.UserUtil;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * @author jiajixu
 * @date 2020/11/4 14:13
 */
@Component
public class UserUtilImpl implements UserUtil {

    @Override
    public Long currentUserIdNotNull() {
        val currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            throw new RequestActionException("没有得到用户信息");
        }
        return currentUser.getId();
    }

    @Override
    public Long currentUserIdNullable() {
        val currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        return currentUser.getId();
    }
}
