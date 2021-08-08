package com.github.xujiaji.mk.security.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.payload.AdminStatusChangeCondition;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.util.RedisUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.admin.vo.MkSecAdminUserPrincipal;
import com.github.xujiaji.mk.security.admin.vo.VerifyVO;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.playload.AdminAddCondition;
import com.github.xujiaji.mk.security.playload.AdminEditCondition;
import com.github.xujiaji.mk.security.playload.AdminLoginCondition;
import com.github.xujiaji.mk.security.service.impl.MkSecUserServiceImpl;
import com.github.xujiaji.mk.security.util.SecurityUtil;
import com.github.xujiaji.mk.security.vo.AdminLoginSuccessVO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

//    @ApiVersion("1.0.1")
//    @GetMapping("/verifyCode")
//    public ApiResponse<VerifyVO> createVerify1_0_1() {
//        return ApiResponse.ofSuccess(new VerifyVO("aaaa", "11111"));
//    }

    /**
     * 管理员登录
     */
    @PutMapping("/login")
    public ApiResponse<AdminLoginSuccessVO> login(@Valid @RequestBody AdminLoginCondition request, HttpServletRequest hsr) {
        val verifyCodeByGuid = redisUtil.getVerifyCodeByGuid(request.getVerify());
        if (verifyCodeByGuid == null) {
            throw new RequestActionException("验证码已过期请重新获取验证码");
        }
        if (!verifyCodeByGuid.equals(request.getCode())) {
            throw new RequestActionException("验证码错误");
        }
        return ApiResponse.ofSuccess(secUserService.login(request, hsr));
    }

    /**
     * 管理员信息
     */
    @GetMapping("/info")
    public ApiResponse<MkSecAdminUserPrincipal> info() {
        return success((MkSecAdminUserPrincipal) SecurityUtil.getCurrentUser());
    }

    /**
     * 管理员列表
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<MkSecUser>> adminUserPage(@Valid PageCondition request) {
        return successPage(secUserService.page(mapPage(request)));
    }

    /**
     * 管理员添加
     */
    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody @Valid AdminAddCondition request) {
        secUserService.adminAdd(request);
        return successAdd();
    }

    /**
     * 管理员编辑
     */
    @PutMapping("/edit")
    public ApiResponse<?> edit(@RequestBody @Valid AdminEditCondition request) {
        secUserService.adminEdit(request);
        return successUpdate();
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
