package com.github.xujiaji.mk.security.admin.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.admin.service.AdminMonitorService;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.exception.SecurityException;
import com.github.xujiaji.mk.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @menu 监控在线管理员用户
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sec/monitor")
public class MkSecAdminMonitorController {
    private final AdminMonitorService adminMonitorService;

    /**
     * 在线用户列表
     * @param pageCondition 分页参数
     */
    @GetMapping("/online/user")
    public ApiResponse<PageVO<MkSecUser>> onlineUser(@Valid PageCondition pageCondition) {
        return ApiResponse.ofSuccess(adminMonitorService.onlineUser(pageCondition));
    }

    /**
     *
     * 批量踢出在线用户
     *
     * @param ids 用户id列表
     */
    @DeleteMapping("/online/user/kickout")
    public ApiResponse<?> kickoutOnlineUser(@NotEmpty(message = "ids不能为空") @RequestParam List<Long> ids) {
        val currentUserId = SecurityUtil.getCurrentUserId();
        if (ids.stream().anyMatch(it -> it.equals(currentUserId))){
            throw new SecurityException(Status.KICKOUT_SELF);
        }
        adminMonitorService.kickout(ids);
        return ApiResponse.ofSuccess();
    }
}
