package com.github.xujiaji.mk.user.front.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.user.entity.MkUserView;
import com.github.xujiaji.mk.user.front.payload.UserEditCondition;
import com.github.xujiaji.mk.user.front.service.MkUserFrontService;
import com.github.xujiaji.mk.user.service.impl.MkUserViewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

/**
 * @menu 前端-用户编辑自己的信息
 * @author jiajixu
 * @date 2020/10/26 10:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/front/user")
public class MkUserFrontController extends BaseController {

    private final MkUserFrontService mkUserFrontService;
    private final MkUserViewServiceImpl userViewService;
    private final UserUtil userUtil;

    /**
     * 用户信息修改
     */
    @PutMapping("/edit")
    public ApiResponse<?> edit(@RequestBody UserEditCondition request) {
        val userId = userUtil.currentUserIdNotNull();
        val mkUser = BeanUtil.copyProperties(request, MkUser.class);
        mkUser.setId(userId);
        mkUserFrontService.editById(mkUser);
        return successUpdate();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public ApiResponse<MkUserView> info() {
        val userId = userUtil.currentUserIdNotNull();
        return success(userViewService.getUserViewHidePhoneAndEmailById(userId));
    }
}
