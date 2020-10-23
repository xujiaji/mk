package com.github.xujiaji.mk.common.exception;

import com.github.xujiaji.mk.common.base.ApiResponse;

/**
 * @author jiajixu
 * @date 2020/10/23 21:40
 */
public interface IExceptionHandler<T extends Exception> {
    Class<T> exceptionClass();

    ApiResponse<?> handle(Exception e);
}
