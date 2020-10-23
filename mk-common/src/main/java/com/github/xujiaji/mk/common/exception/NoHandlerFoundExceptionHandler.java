package com.github.xujiaji.mk.common.exception;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.NoHandlerFoundException;

@Component
@Slf4j
public class NoHandlerFoundExceptionHandler implements IExceptionHandler<NoHandlerFoundException> {

    @Override
    public Class<NoHandlerFoundException> exceptionClass() {
        return NoHandlerFoundException.class;
    }

    @Override
    public ApiResponse<?> handle(Exception e) {
        log.error("【全局异常拦截】NoHandlerFoundException: 请求方法 {}, 请求路径 {}", ((NoHandlerFoundException) e).getRequestURL(), ((NoHandlerFoundException) e).getHttpMethod());
        return ApiResponse.ofStatus(Status.REQUEST_NOT_FOUND);
    }
}
 
