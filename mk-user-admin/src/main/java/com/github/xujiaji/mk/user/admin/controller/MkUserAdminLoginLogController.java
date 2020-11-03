package com.github.xujiaji.mk.user.admin.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.user.entity.MkUserLoginLog;
import com.github.xujiaji.mk.user.entity.MkUserLoginLogView;
import com.github.xujiaji.mk.user.payload.LogPageCondition;
import com.github.xujiaji.mk.user.service.IMkUserLoginLogViewService;
import com.github.xujiaji.mk.user.service.impl.MkUserLoginLogServiceImpl;
import com.github.xujiaji.mk.user.service.impl.MkUserLoginLogViewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @menu 登录日志管理
 * @author xujiaji
 * @since 2020-10-30
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/user/login/log")
public class MkUserAdminLoginLogController extends BaseController {

    private final MkUserLoginLogServiceImpl mkUserLoginLogService;

    private final MkUserLoginLogViewServiceImpl userLoginLogViewService;

    /**
     * 登录日志分页获取
     * @param request 分页请求
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<MkUserLoginLogView>> page(@Valid LogPageCondition request) {
        return successPage(userLoginLogViewService.pageSearch(request, mapPage(request)));
    }

    /**
     * 删除登录日志
     * @param id 要删除的登录日志ID
     */
    @DeleteMapping
    public ApiResponse<?> delete(@NotNull(message = "请传入要删除的登录日志ID") Long id) {
        mkUserLoginLogService.deleteById(id);
        return successDelete();
    }


}
