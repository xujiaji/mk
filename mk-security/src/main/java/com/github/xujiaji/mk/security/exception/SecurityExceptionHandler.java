package com.github.xujiaji.mk.security.exception;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.exception.IExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.NoHandlerFoundException;

@Component
@Slf4j
public class SecurityExceptionHandler implements IExceptionHandler<SecurityException> {

    @Override
    public Class<SecurityException> exceptionClass() {
        return SecurityException.class;
    }

    @Override
    public ApiResponse<?> handle(Exception e) {
        log.error("【全局异常拦截】SecurityException: 请求方法 {}, 请求路径 {}", ((SecurityException) e).getCode(), e.getMessage());
        return ApiResponse.of(((SecurityException) e).getCode(), e.getMessage(), null);
    }
}
 
