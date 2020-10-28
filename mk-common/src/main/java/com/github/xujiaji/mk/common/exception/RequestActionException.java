package com.github.xujiaji.mk.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端行为异常
 * @author jiajixu
 * @date 2020-05-27 20:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RequestActionException extends RuntimeException {
    public RequestActionException(String message) {
        super(message);
    }
}
