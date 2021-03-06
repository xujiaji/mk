package com.github.xujiaji.mk.common.base;

import lombok.Getter;


/**
 * 通用状态码
 * @author jiajixu
 */
@Getter
public enum Status implements IStatus {
    /**
     * 操作成功！
     */
    SUCCESS(200, "操作成功！"),

    /**
     * 请求异常
     */
    REQUEST_ERROR(201, "异常！"),

    /**
     * 操作异常！
     */
    ERROR(500, "操作异常！"),

    /**
     * 退出成功！
     */
    LOGOUT(200, "退出成功！"),

    /**
     * 请先登录！
     */
    UNAUTHORIZED(401, "请先登录！"),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(403, "权限不足！"),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(404, "请求不存在！"),

    /**
     * 请求方式不支持！
     */
    HTTP_BAD_METHOD(405, "请求方式不支持！"),

    /**
     * 请求content type不支持！
     */
    HTTP_BAD_CONTENT_TYPE(406, "请求ContentType不支持！"),

    /**
     * 请求异常！
     */
    BAD_REQUEST(400, "请求异常！"),

    /**
     * 参数不匹配！
     */
    PARAM_NOT_MATCH(400, "参数不匹配！"),

    /**
     * 参数不能为空！
     */
    PARAM_NOT_NULL(400, "参数不能为空！"),

    /**
     * 当前用户已被锁定，请联系管理员解锁！
     */
    USER_DISABLED(403, "当前用户已被锁定，请联系管理员解锁！"),

    /**
     * 手机号已注册
     */
    ERROR_PHONE_REGISTERED(1000, "该手机号已注册！"),

    /**
     * 手机号已绑定
     */
    ERROR_PHONE_BOUND(1000, "该手机号已绑定！"),

    /**
     * 请传入时间戳！
     */
    NOT_TIMESTAMP(4011, "请求时间戳错误！"),

    /**
     * 请求版本
     */
    NOT_VERSION(4012, "请求版本错误"),

    /**
     * 请求签名
     */
    NOT_SIGN(4013, "请求签名错误"),

    /**
     * 请求签名
     */
    REQUEST_INVALID(4014, "请求签名无效"),

    /**
     * 用户名或密码错误！
     */
    USERNAME_PASSWORD_ERROR(5001, "用户名或密码错误！"),

    /**
     * token 已过期，请重新登录！
     */
    TOKEN_EXPIRED(5002, "token 已过期，请重新登录！"),

    /**
     * token 解析失败，请尝试重新登录！
     */
    TOKEN_PARSE_ERROR(5002, "token 解析失败，请尝试重新登录！"),

    /**
     * 当前用户已在别处登录，请尝试更改密码或重新登录！
     */
    TOKEN_OUT_OF_CTRL(5003, "当前用户已在别处登录，请尝试更改密码或重新登录！"),

    /**
     * 无法手动踢出自己，请尝试退出登录操作！
     */
    KICKOUT_SELF(5004, "无法手动踢出自己，请尝试退出登录操作！"),


    /**
     * 数据库插入异常
     */
    DB_INSERT_ERROR(6000, "添加失败"),

    /**
     * 数据库更新异常
     */
    DB_UPDATE_ERROR(6001, "更新失败"),

    /**
     * 数据库删除异常
     */
    DB_DELETE_ERROR(6002, "删除失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Status fromCode(Integer code) {
        Status[] statuses = Status.values();
        for (Status status : statuses) {
            if (status.getCode()
                    .equals(code)) {
                return status;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return String.format(" Status:{code=%s, message=%s} ", getCode(), getMessage());
    }

}
