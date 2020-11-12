package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.entity.MkUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiajixu
 * @date 2020/11/3 13:30
 */
public interface IUserLoginLogService {
    /**
     * 插入登录日志
     * @param userId 用户ID
     * @param loginType 登录类型
     * @param request 请求
     */
    void insertLog(Long userId, Integer loginType, HttpServletRequest request);

    /**
     * 是否存在今日的刷新登录日志
     * @param userId
     * @return
     */
    boolean isExistTodayLog(Long userId);
}
