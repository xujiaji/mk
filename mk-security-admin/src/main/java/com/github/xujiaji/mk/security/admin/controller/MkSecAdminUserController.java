package com.github.xujiaji.mk.security.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.payload.AdminStatusChangeCondition;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.util.RedisUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.playload.AdminLoginCondition;
import com.github.xujiaji.mk.security.vo.AdminLoginSuccessVO;
import com.github.xujiaji.mk.security.admin.vo.VerifyVO;
import com.github.xujiaji.mk.security.entity.MkAdminUser;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.playload.AdminAddCondition;
import com.github.xujiaji.mk.security.service.impl.MkSecUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @menu 权限-管理员管理
 * @author jiajixu
 * @date 2020/10/29 11:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sec/user")
public class MkSecAdminUserController extends BaseController {

    private final MkSecUserServiceImpl secUserService;
    private final RedisUtil redisUtil;

    /**
     * 得到验证码
     */
    @GetMapping("/verifyCode")
    public ApiResponse<VerifyVO> createVerify() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 30, 4, 3);
        val uuid = UUID.randomUUID().toString();
        redisUtil.setGuidAndVerifyCode(uuid, circleCaptcha.getCode());
        return ApiResponse.ofSuccess(new VerifyVO(uuid, circleCaptcha.getImageBase64()));
    }

    /**
     * 管理员登录
     */
    @PutMapping("/login")
    public ApiResponse<AdminLoginSuccessVO> login(@Valid @RequestBody AdminLoginCondition request) {
        val verifyCodeByGuid = redisUtil.getVerifyCodeByGuid(request.getVerify());
        if (verifyCodeByGuid == null) {
            throw new RequestActionException("验证码已过期请重新获取验证码");
        }
        if (!verifyCodeByGuid.equals(request.getCode())) {
            throw new RequestActionException("验证码错误");
        }
        return ApiResponse.ofSuccess(secUserService.login(request));
    }

    /**
     * 管理员列表
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<MkAdminUser>> adminUserPage(@Valid PageCondition request) {
        return success(secUserService.adminUserPage(mapPage(request)));
    }

    /**
     * 管理员添加
     */
    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody @Valid AdminAddCondition request) {
        secUserService.addAdmin(request.getUsername(), request.getRoleId(), request.getPassword());
        return successAdd();
    }

    /**
     * 禁用或启用管理员
     */
    @PutMapping("/status")
    public ApiResponse<?> disable(@RequestBody @Valid AdminStatusChangeCondition request) {
        val mkSecUser = new MkSecUser();
        mkSecUser.setId(request.getSecUserId());
        mkSecUser.setStatus(Integer.valueOf(request.getStatus()));
        secUserService.editById(mkSecUser);
        return successUpdate();
    }

    /**
     * 管理员删除
     * @param secUserId 管理员secUserId
     */
    @DeleteMapping
    public ApiResponse<?> delete(@NotNull(message = "管理员secUserId不能为空")
                                             Long secUserId) {
        secUserService.deleteAdminUserBySecUserId(secUserId);
        return successDelete();
    }
}
