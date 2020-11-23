package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.base.Status;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jiajixu
 * @date 2020/11/23 16:39
 */
public interface ILogService {
    /**
     * 添加日志
     * @param type
     * @param content
     * @param userId
     */
    void addLog(Status type, String content, @Nullable Long userId, @Nullable HttpServletRequest request);

    void addLog(Status type, String content);

    void addLog(Status type, String content, Long userId);

    void addLog(Status type, String content, HttpServletRequest request);
}
