package com.github.xujiaji.mk.security.exception;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.exception.IExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityExceptionHandler implements IExceptionHandler<SecurityException> {

    @Override
    public Class<SecurityException> exceptionClass() {
        return SecurityException.class;
    }

    @Override
    public ApiResponse<?> handle(Exception e) {
        log.error(errMessage(e));
        return ApiResponse.of(((SecurityException) e).getCode(), e.getMessage(), null);
    }

    @Override
    public Status errStatus(Exception e) {
        return Status.fromCode(((SecurityException) e).getCode());
    }

    @Override
    public String errMessage(Exception e) {
        return String.format("【全局异常拦截】SecurityException: 请求方法 %s, 请求路径 %s", ((SecurityException) e).getCode(), e.getMessage());
    }
}
 
