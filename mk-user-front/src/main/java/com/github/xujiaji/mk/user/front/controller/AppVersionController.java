package com.github.xujiaji.mk.user.front.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.user.entity.MkAppVersion;
import com.github.xujiaji.mk.user.service.impl.MkAppVersionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @menu 前端-app版本管理
 * @author jiajixu
 * @date 2020/11/19 11:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/front/app/version")
public class AppVersionController extends BaseController {

    private final MkAppVersionServiceImpl appVersionService;

    /**
     * 获取当前版本
     * @return 版本信息实体
     */
    @GetMapping
    public ApiResponse<MkAppVersion> version() {
        return success(appVersionService.getBaseMapper().selectCurrentVersion());
    }
}
