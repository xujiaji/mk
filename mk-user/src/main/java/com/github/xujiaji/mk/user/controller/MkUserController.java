package com.github.xujiaji.mk.user.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.user.entity.MkUser;
import com.github.xujiaji.mk.user.service.impl.MkUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @menu 用户
 * @author xujiaji
 * @since 2020-10-25
 */
@RestController
@RequestMapping("/user")
public class MkUserController extends BaseController {

    @Autowired
    private MkUserServiceImpl mkUserService;

    /**
     * 用户基本信息
     * @param id 用户ID
     * @return {@link MkUser}
     */
    @GetMapping("/info")
    public ApiResponse<MkUser> info(Long id) {
        return ApiResponse.ofSuccess(mkUserService.info(id));
    }
}
