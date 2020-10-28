package com.github.xujiaji.mk.common.exception;

import com.github.xujiaji.mk.common.base.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 状态异常
 * @author jiajixu
 * @date 2020-05-27 20:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StatusException extends RuntimeException {
    private final Status status;
}
