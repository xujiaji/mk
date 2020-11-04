package com.github.xujiaji.mk.common.util;

/**
 * @author jiajixu
 * @date 2020/11/4 14:12
 */
public interface UserUtil {
    /**
     * 当前请求用户的ID，不可能为空
     */
    Long currentUserIdNotNull();

    /**
     * 当前请求用户的ID，可能为空
     */
    Long currentUserIdNullable();
}
