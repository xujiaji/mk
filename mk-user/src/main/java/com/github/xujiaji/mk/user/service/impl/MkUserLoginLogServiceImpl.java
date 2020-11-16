package com.github.xujiaji.mk.user.service.impl;

import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.service.IUserLoginLogService;
import com.github.xujiaji.mk.common.util.IPUtil;
import com.github.xujiaji.mk.user.entity.MkUserLoginLog;
import com.github.xujiaji.mk.user.mapper.MkUserLoginLogMapper;
import com.github.xujiaji.mk.user.service.IMkUserLoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 认证用户日志 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-30
 */
@RequiredArgsConstructor
@Service
public class MkUserLoginLogServiceImpl extends BaseServiceImpl<MkUserLoginLogMapper, MkUserLoginLog> implements IMkUserLoginLogService, IUserLoginLogService {

    private final IPUtil ipUtil;

    @Override
    public void insertLog(Long userId, Integer loginType, HttpServletRequest request) {
        val log = new MkUserLoginLog();
        String loginIp = ipUtil.getClientIpAddress(request);
        if (loginIp != null && loginIp.contains(":")) {
            loginIp = loginIp.substring(0, loginIp.indexOf(":"));
        }
        log.setLoginIp(loginIp);
        String cityInfo = ipUtil.getCityInfo(log.getLoginIp());
        log.setLoginLocation(cityInfo != null ? cityInfo : "未获取到位置信息");
        log.setLoginType(loginType);
        log.setLoginDevice(request.getHeader("User-Agent"));
        log.setUserId(userId);
        add(log);
    }

    @Override
    public boolean isExistTodayLog(Long userId) {
        return baseMapper.countTodayLog(userId) > 0;
    }
}
