package com.github.xujiaji.mk.user.front.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IUserLoginLogService;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.file.service.impl.MkFileServiceImpl;
import com.github.xujiaji.mk.security.util.JwtUtil;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import com.github.xujiaji.mk.user.dto.ThirdBindStatusDTO;
import com.github.xujiaji.mk.user.front.payload.*;
import com.github.xujiaji.mk.user.front.service.MkAuthUserService;
import com.github.xujiaji.mk.user.front.vo.LoginSuccessVO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * @menu 前端-用户认证与安全
 * @author jiajixu
 * @date 2020/11/4 10:29
 */
@RequiredArgsConstructor
@RequestMapping("/front/auth/user")
@RestController
public class MkAuthUserController extends BaseController {

    private final MkAuthUserService authUserService;
    private final IUserLoginLogService userLoginLogService;
    private final CommonUtil commonUtil;
    private final UserUtil userUtil;
    private final JwtUtil jwtUtil;
    private final MkFileServiceImpl fileService;

    private ApiResponse<LoginSuccessVO> loginSuccessHandle(MkUser mkUser, Integer loginType, HttpServletRequest hsr) {
        LoginSuccessVO mkUserView = BeanUtil.copyProperties(mkUser, LoginSuccessVO.class);
        mkUserView.setPhone(commonUtil.hidePhone(mkUserView.getPhone()));
        mkUserView.setEmail(commonUtil.hideEmail(mkUserView.getEmail()));
        userLoginLogService.insertLog(mkUserView.getId(), loginType, hsr);

        UserDetails userDetails = UserPrincipal.create(mkUser);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(hsr));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String jwt = jwtUtil.createJWT(authentication, true);
        mkUserView.setAvatar(fileService.getPathById(mkUser.getAvatar()));
        mkUserView.setAuthorization(jwt);
        mkUserView.setAuthorizationType("Bearer");
        return success(mkUserView);
    }

    /**
     * 刷新登录状态
     */
    @PutMapping("/refresh/login")
    public ApiResponse<LoginSuccessVO> refreshLogin(HttpServletRequest hsr) {
        val userId = userUtil.currentUserIdNotNull();
        if (userLoginLogService.isExistTodayLog(userId)) {
            return ApiResponse.ofSuccess();
        }
        val user = authUserService.getById(userId);
        return loginSuccessHandle(user, Consts.LoginType.REFRESH, hsr);
    }

    /**
     * 三方登录
     */
    @PostMapping("/third/login")
    public ApiResponse<LoginSuccessVO> thirdLogin(@RequestBody @Valid ThirdLoginCondition request, HttpServletRequest hsr) {
        MkUser mkUser;
        val loginType = Integer.parseInt(request.getType());
        switch (loginType) {
            case Consts.LoginType.QQ:
                if (StrUtil.isBlank(request.getTokenId())) {
                    throw new RequestActionException("请传入三方登录token");
                }
                mkUser = authUserService.authQQ(request, null);
                break;
            case Consts.LoginType.WX:
                if (StrUtil.isBlank(request.getTokenId())) {
                    throw new RequestActionException("请传入三方登录token");
                }
                mkUser = authUserService.authWeiXin(request, null);
                break;
            case Consts.LoginType.IOS:
                mkUser = authUserService.authIOS(request, null);
                break;
            default:
                throw new RequestActionException("没有这个登录类型");
        }

        return loginSuccessHandle(mkUser, loginType, hsr);
    }

    /**
     * 微信小程序登录
     */
    @PostMapping("/mini/wx/login")
    public ApiResponse<LoginSuccessVO> miniWxLogin(@Valid @RequestBody MiniWxLoginCondition request, HttpServletRequest hsr) {
        MkUser user = authUserService.authMiniWeiXin(request);
        return loginSuccessHandle(user, Consts.LoginType.WX_MINI, hsr);
    }

    /**
     * 微信小程序注册通过用户信息
     */
    @PostMapping("/mini/wx/info/login")
    public ApiResponse<LoginSuccessVO> miniWxLoginByInfo(@Valid @RequestBody MiniWxInfoLoginCondition request, HttpServletRequest hsr) {
        MkUser user = authUserService.authMiniWeiXinByInfo(request);
        return loginSuccessHandle(user, Consts.LoginType.WX_MINI, hsr);
    }

    /**
     * 获取账号三方绑定状态
     */
    @GetMapping("/bind/status")
    public ApiResponse<ThirdBindStatusDTO> thirdBindStatus() {
        return success(authUserService.bindStatus(userUtil.currentUserIdNotNull()));
    }

    /**
     * 绑定三方登录
     */
    @PutMapping("/third/login/bind")
    public ApiResponse<?> bindThirdLogin(@RequestBody @Valid ThirdLoginCondition request) {
        authUserService.bindThirdLogin(userUtil.currentUserIdNotNull(), request);
        return successMessage("绑定成功");
    }

    /**
     * 解绑：1 qq; 2 微信; 6 iOS
     */
    @DeleteMapping("/third/login/unbind")
    public ApiResponse<?> unBindThirdLogin(@Pattern(regexp = "[126]", message = "没有这个类型") String type) {
        authUserService.unBindThirdLogin(type, userUtil.currentUserIdNotNull());
        return successMessage("解绑成功");
    }

    /**
     * 手机号密码注册
     */
    @PostMapping("/register")
    public ApiResponse<LoginSuccessVO> register(@RequestBody @Valid RegisterCondition register, HttpServletRequest hsr) {
        MkUser newUser = authUserService.registerByPhone(register);
        return loginSuccessHandle(newUser, Consts.LoginType.PHONE_SMS, hsr);
    }

    /**
     * 手机号密码登录
     */
    @PostMapping("/mobile/login")
    public ApiResponse<LoginSuccessVO> mobileLogin(@RequestBody @Valid MobileLoginCondition mobileLoginRequest, HttpServletRequest hsr) {
        MkUser user = authUserService.loginByMobile(mobileLoginRequest);
        return loginSuccessHandle(user, Consts.LoginType.PHONE_PASSWORD, hsr);
    }

    /**
     * 手机验证码登录
     */
    @PostMapping("/verify/login")
    public ApiResponse<LoginSuccessVO> verifyLogin(@RequestBody @Valid MobileSmsLoginCondition mobileSmsLoginRequest, HttpServletRequest hsr) {
        MkUser user = authUserService.loginByMobileSms(mobileSmsLoginRequest);
        return loginSuccessHandle(user, Consts.LoginType.PHONE_SMS, hsr);
    }

    /**
     * 忘记密码（通过验证码设置密码）
     */
    @PostMapping("/forget/password")
    public ApiResponse<?> forgetPassword(@RequestBody @Valid ForgetPasswordCondition forgetPasswordRequest) {
        authUserService.forgetPassword(forgetPasswordRequest);
        return successMessage("密码找回成功");
    }

    /**
     * 设置登录密码（没有密码的时候）
     */
    @PostMapping("/set/password")
    public ApiResponse<?> setPassword(@RequestBody @Valid SetPasswordCondition setPasswordRequest) {
        authUserService.setPassword(userUtil.currentUserIdNotNull(), setPasswordRequest);
        return successMessage("密码设置成功");
    }

    /**
     * 绑定手机号同时设置密码
     */
    @PostMapping("/bind/mobile")
    public ApiResponse<?> bindMobileAndPassword(@RequestBody @Valid BindMobileCondition bindMobileRequest) {
        authUserService.bindMobileAndPassword(userUtil.currentUserIdNotNull(), bindMobileRequest);
        return successMessage("绑定成功");
    }

    /**
     * 更改绑定手机号
     */
    @PutMapping("/change/mobile")
    public ApiResponse<?> changeMobile(@RequestBody @Valid ChangeMobileCondition bindMobileRequest) {
        authUserService.changeMobile(userUtil.currentUserIdNotNull(), bindMobileRequest);
        return successMessage("绑定成功");
    }

    /**
     * 修改登录密码
     */
    @PostMapping("/modify/password")
    public ApiResponse<?> modifyPassword(@RequestBody @Valid ModifyPasswordCondition modifyPasswordRequest) {
        authUserService.modifyPassword(userUtil.currentUserIdNotNull(), modifyPasswordRequest);
        return successMessage("密码修改成功");
    }

}
