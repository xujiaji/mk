package com.github.xujiaji.mk.security.exception;

import com.github.xujiaji.mk.common.base.BaseException;
import com.github.xujiaji.mk.common.base.Status;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 全局异常
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
public class SecurityException extends BaseException {
    public SecurityException(Status status) {
        super(status);
    }

    public SecurityException(Status status, Object data) {
        super(status, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
