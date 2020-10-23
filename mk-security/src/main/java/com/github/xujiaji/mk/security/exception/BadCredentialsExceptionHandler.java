package com.github.xujiaji.mk.security.exception;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.exception.IExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BadCredentialsExceptionHandler implements IExceptionHandler<BadCredentialsException> {

    @Override
    public Class<BadCredentialsException> exceptionClass() {
        return BadCredentialsException.class;
    }

    @Override
    public ApiResponse<?> handle(Exception e) {
        log.error("【全局异常拦截】BadCredentialsException: 错误信息 {}", e.getMessage());
        return ApiResponse.ofStatus(Status.USERNAME_PASSWORD_ERROR);
    }
}
 
