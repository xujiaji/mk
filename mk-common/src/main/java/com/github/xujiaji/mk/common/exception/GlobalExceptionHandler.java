package com.github.xujiaji.mk.common.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseException;
import com.github.xujiaji.mk.common.base.Status;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.List;

/**
 * <p>
 * 全局统一异常处理
 * </p>
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 注入的异常处理
    @Autowired
    private List<IExceptionHandler<?>> exceptionHandlers;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse<?> handlerException(Exception e) {
        val first = exceptionHandlers.stream()
                .filter(p -> p.exceptionClass().isInstance(e))
                .findFirst();
        if (first.isPresent()) {
            return first.get().handle(e);
        } else if (e instanceof StatusException) {
            log.error("【全局异常拦截】RequestActionStatusException: 错误信息 {}", ((StatusException) e).getStatus().getMessage());
            return ApiResponse.of(((StatusException) e).getStatus().getCode(), ((StatusException) e).getStatus().getMessage(), null);
        } else if (e instanceof RequestActionException) {
            log.error("【全局异常拦截】RequestActionException: 错误信息 {}", e.getMessage());
            return ApiResponse.of(Status.REQUEST_ERROR.getCode(), e.getMessage(), null);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.error("【全局异常拦截】HttpRequestMethodNotSupportedException: 当前请求方式 {}, 支持请求方式 {}", ((HttpRequestMethodNotSupportedException) e).getMethod(), JSONUtil.toJsonStr(((HttpRequestMethodNotSupportedException) e).getSupportedHttpMethods()));
            return ApiResponse.ofStatus(Status.HTTP_BAD_METHOD);
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("【全局异常拦截】MethodArgumentNotValidException", e);
            return ApiResponse.of(Status.BAD_REQUEST.getCode(), ((MethodArgumentNotValidException) e).getBindingResult()
                    .getAllErrors()
                    .get(0)
                    .getDefaultMessage(), null);
        } else if (e instanceof BindException) {
            log.error("【全局异常拦截】BindException", e);
            return ApiResponse.of(Status.BAD_REQUEST.getCode(), ((BindException) e).getBindingResult()
                    .getAllErrors()
                    .get(0)
                    .getDefaultMessage(), null);
        } else if (e instanceof UnexpectedTypeException) {
            log.error("【全局异常拦截】UnexpectedTypeException", e);
            return ApiResponse.of(Status.BAD_REQUEST.getCode(), "请检查参数有没有传或是类型是否正确", null);
        } else if (e instanceof ConstraintViolationException) {
            log.error("【全局异常拦截】ConstraintViolationException", e);
            return ApiResponse.of(Status.BAD_REQUEST.getCode(), CollUtil.getFirst(((ConstraintViolationException) e).getConstraintViolations())
                    .getMessage(), null);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            log.error("【全局异常拦截】MethodArgumentTypeMismatchException: 参数名 {}, 异常信息 {}", ((MethodArgumentTypeMismatchException) e).getName(), ((MethodArgumentTypeMismatchException) e).getMessage());
            return ApiResponse.ofStatus(Status.PARAM_NOT_MATCH);
        } else if (e instanceof HttpMessageNotReadableException) {
            log.error("【全局异常拦截】HttpMessageNotReadableException: 错误信息 {}", ((HttpMessageNotReadableException) e).getMessage());
            return ApiResponse.ofStatus(Status.PARAM_NOT_NULL);
        } else if (e instanceof BaseException) {
            log.error("【全局异常拦截】DataManagerException: 状态码 {}, 异常信息 {}", ((BaseException) e).getCode(), e.getMessage());
            return ApiResponse.ofException((BaseException) e);
        }

        log.error("【全局异常拦截】: 异常信息 {} ", e.getMessage());
        return ApiResponse.ofStatus(Status.ERROR);
    }
}
