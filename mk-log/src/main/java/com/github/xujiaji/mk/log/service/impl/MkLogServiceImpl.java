package com.github.xujiaji.mk.log.service.impl;

import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.service.ILogService;
import com.github.xujiaji.mk.common.util.IPUtil;
import com.github.xujiaji.mk.log.entity.MkLog;
import com.github.xujiaji.mk.log.mapper.MkLogMapper;
import com.github.xujiaji.mk.log.service.IMkLogService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 日志记录 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-26
 */
@RequiredArgsConstructor
@Service
public class MkLogServiceImpl extends BaseServiceImpl<MkLogMapper, MkLog> implements IMkLogService, ILogService {

    private final IPUtil ipUtil;

    @Override
    public void addLog(Status type, String content, @Nullable Long userId, @Nullable HttpServletRequest request) {
        val mkLog = new MkLog();
        val sb = new StringBuilder();
        if (request != null) {
            String loginIp = ipUtil.getClientIpAddress(request);
            if (loginIp != null && loginIp.contains(":")) {
                loginIp = loginIp.substring(0, loginIp.indexOf(":"));
            }
            sb
                    .append(" IP：").append(loginIp).append("\n")
                    .append("位置：").append(ipUtil.getCityInfo(loginIp)).append("\n")
                    .append(" UA：").append(request.getHeader("User-Agent")).append("\n");
        }
        sb.append("内容：").append(content);
        mkLog.setLogType(type.getCode());
        mkLog.setContent(sb.toString());
        mkLog.setUserId(userId);
        add(mkLog);
    }

    @Override
    public void addLog(Status type, String content) {
        this.addLog(type, content, null, null);
    }

    @Override
    public void addLog(Status type, String content, Long userId) {
        this.addLog(type, content, userId, null);
    }

    @Override
    public void addLog(Status type, String content, HttpServletRequest request) {
        this.addLog(type, content, null, request);
    }
}
