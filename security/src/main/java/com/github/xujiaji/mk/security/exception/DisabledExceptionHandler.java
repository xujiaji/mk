package com.github.xujiaji.mk.security.exception;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.exception.IExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DisabledExceptionHandler implements IExceptionHandler<DisabledException> {

    @Override
    public Class<DisabledException> exceptionClass() {
        return DisabledException.class;
    }

    @Override
    public ApiResponse<?> handle(Exception e) {
        log.error(errMessage(e));
        return ApiResponse.ofStatus(errStatus(e));
    }

    @Override
    public Status errStatus(Exception e) {
        return Status.USER_DISABLED;
    }

    @Override
    public String errMessage(Exception e) {
        return String.format("【全局异常拦截】DisabledException: 错误信息 %s", e.getMessage());
    }
}
 
