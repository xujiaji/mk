package com.github.xujiaji.mk.security.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.admin.service.MonitorService;
import com.github.xujiaji.mk.security.admin.vo.OnlineUser;
import com.github.xujiaji.mk.security.exception.SecurityException;
import com.github.xujiaji.mk.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 监控 Controller，在线用户，手动踢出用户等功能
 * </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitor")
public class MonitorController {
    private final MonitorService monitorService;

    /**
     * 在线用户列表
     *
     * @param pageCondition 分页参数
     */
    @GetMapping("/online/user")
    public ApiResponse<PageVO<OnlineUser>> onlineUser(@Valid PageCondition pageCondition) {
        return ApiResponse.ofSuccess(monitorService.onlineUser(pageCondition));
    }

    /**
     * 批量踢出在线用户
     *
     * @param names 用户名列表
     */
    @DeleteMapping("/online/user/kickout")
    public ApiResponse<?> kickoutOnlineUser(@RequestBody List<String> names) {
        if (CollUtil.isEmpty(names)) {
            throw new SecurityException(Status.PARAM_NOT_NULL);
        }
        if (names.contains(SecurityUtil.getCurrentUsername())){
            throw new SecurityException(Status.KICKOUT_SELF);
        }
        monitorService.kickout(names);
        return ApiResponse.ofSuccess();
    }
}
