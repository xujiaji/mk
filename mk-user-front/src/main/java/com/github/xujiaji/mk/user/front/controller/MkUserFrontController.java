package com.github.xujiaji.mk.user.front.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.file.service.impl.MkFileServiceImpl;
import com.github.xujiaji.mk.user.front.payload.UserEditCondition;
import com.github.xujiaji.mk.user.front.service.MkUserFrontService;
import com.github.xujiaji.mk.user.front.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

/**
 * @menu 前端-用户查看或编辑自己的信息
 * @author jiajixu
 * @date 2020/10/26 10:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/front/user")
public class MkUserFrontController extends BaseController {
    private final MkUserFrontService mkUserFrontService;
    private final UserUtil userUtil;
    private final MkFileServiceImpl fileService;

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
    public ApiResponse<UserVO> info() {
        val userId = userUtil.currentUserIdNotNull();
        val user = mkUserFrontService.getUserHidePhoneAndEmailById(userId);
        val userVO = BeanUtil.copyProperties(user, UserVO.class);
        userVO.setAvatar(fileService.getPathById(user.getAvatar()));
        return success(userVO);
    }
}
