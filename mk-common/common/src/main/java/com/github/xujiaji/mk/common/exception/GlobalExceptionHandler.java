package com.github.xujiaji.mk.common.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseException;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.service.ILogService;
import com.github.xujiaji.mk.common.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.List;

/**
 * <p>
 * 全局统一异常处理
 * </p>
 *
 */
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 注入的异常处理
    @Autowired
    private List<IExceptionHandler<?>> exceptionHandlers;
    @Autowired(required = false)
    private ILogService logService;
    private final UserUtil userUtil;

    private void add2Log(Status status, String message) {
        HttpServletRequest request = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        logService.addLog(status, message, userUtil.currentUserIdNullable(), request);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse<?> handlerException(Exception e) {
        val first = exceptionHandlers.stream()
                .filter(p -> p.exceptionClass().isInstance(e))
                .findFirst();
        String msg = "";
        Status status = null;
        try {
            log.info("异常： " + e.getClass().getSimpleName());
            if (first.isPresent()) {
                log.info("匹配到异常 first.isPresent()");
                val handler = first.get();
                msg = handler.errMessage(e);
                status = handler.errStatus(e);
                return handler.handle(e);
            } else if (e instanceof HttpMediaTypeNotSupportedException) {
                msg = String.format("【全局异常拦截】HttpMediaTypeNotSupportedException: 当前请求内容类型 %s, 支持请求类型 %s", ((HttpMediaTypeNotSupportedException) e).getContentType(), JSONUtil.toJsonStr(((HttpMediaTypeNotSupportedException) e).getSupportedMediaTypes()));
                status = Status.HTTP_BAD_CONTENT_TYPE;
                return ApiResponse.ofStatus(Status.HTTP_BAD_CONTENT_TYPE);
            } else if (e instanceof StatusException) {
                msg = String.format("【全局异常拦截】RequestActionStatusException: 错误信息 %s", ((StatusException) e).getStatus().getMessage());
                status = ((StatusException) e).getStatus();
                return ApiResponse.of(((StatusException) e).getStatus().getCode(), ((StatusException) e).getStatus().getMessage(), null);
            } else if (e instanceof RequestActionException) {
                msg = String.format("【全局异常拦截】RequestActionException: 错误信息 %s", e.getMessage());
                status = Status.REQUEST_ERROR;
                return ApiResponse.of(Status.REQUEST_ERROR.getCode(), e.getMessage(), null);
            } else if (e instanceof HttpRequestMethodNotSupportedException) {
                msg = String.format("【全局异常拦截】HttpRequestMethodNotSupportedException: 当前请求方式 %s, 支持请求方式 %s", ((HttpRequestMethodNotSupportedException) e).getMethod(), JSONUtil.toJsonStr(((HttpRequestMethodNotSupportedException) e).getSupportedHttpMethods()));
                status = Status.HTTP_BAD_METHOD;
                return ApiResponse.ofStatus(Status.HTTP_BAD_METHOD);
            } else if (e instanceof MethodArgumentNotValidException) {
                msg = String.format("【全局异常拦截】MethodArgumentNotValidException: %s", e.getMessage());
                status = Status.BAD_REQUEST;
                return ApiResponse.of(Status.BAD_REQUEST.getCode(), ((MethodArgumentNotValidException) e).getBindingResult()
                        .getAllErrors()
                        .get(0)
                        .getDefaultMessage(), null);
            } else if (e instanceof BindException) {
                msg = String.format("【全局异常拦截】BindException: %s", e.getMessage());
                status = Status.BAD_REQUEST;
                return ApiResponse.of(Status.BAD_REQUEST.getCode(), ((BindException) e).getBindingResult()
                        .getAllErrors()
                        .get(0)
                        .getDefaultMessage(), null);
            } else if (e instanceof UnexpectedTypeException) {
                msg = String.format("【全局异常拦截】UnexpectedTypeException: %s", e.getMessage());
                status = Status.BAD_REQUEST;
                return ApiResponse.of(Status.BAD_REQUEST.getCode(), "请检查参数有没有传或是类型是否正确", null);
            } else if (e instanceof ConstraintViolationException) {
                msg = String.format("【全局异常拦截】ConstraintViolationException: %s", e.getMessage());
                status = Status.BAD_REQUEST;
                return ApiResponse.of(Status.BAD_REQUEST.getCode(), CollUtil.getFirst(((ConstraintViolationException) e).getConstraintViolations())
                        .getMessage(), null);
            } else if (e instanceof MethodArgumentTypeMismatchException) {
                msg = String.format("【全局异常拦截】MethodArgumentTypeMismatchException: 参数名 %s, 异常信息 %s", ((MethodArgumentTypeMismatchException) e).getName(), ((MethodArgumentTypeMismatchException) e).getMessage());
                status = Status.PARAM_NOT_MATCH;
                return ApiResponse.ofStatus(Status.PARAM_NOT_MATCH);
            } else if (e instanceof HttpMessageNotReadableException) {
                msg = String.format("【全局异常拦截】HttpMessageNotReadableException: 错误信息 %s", ((HttpMessageNotReadableException) e).getMessage());
                status = Status.PARAM_NOT_NULL;
                return ApiResponse.ofStatus(Status.PARAM_NOT_NULL);
            } else if (e instanceof BaseException) {
                msg = String.format("【全局异常拦截】BaseException: 状态码 %s, 异常信息 %s", ((BaseException) e).getCode(), e.getMessage());
                status = Status.ERROR;
                return ApiResponse.ofException((BaseException) e);
            }
            msg = String.format("【全局异常拦截】: 异常信息 %s", e.getMessage());
            status = Status.ERROR;
            return ApiResponse.ofStatus(Status.ERROR);
        } finally {
            log.error(msg);
            e.getStackTrace();
            add2Log(status, msg + "\n" + ExceptionUtil.stacktraceToString(e));
        }
    }
}
