package com.github.xujiaji.mk.sms.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.exception.StatusException;
import com.github.xujiaji.mk.common.service.IMkSmsService;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.sms.front.payload.SmsCondition;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @menu 前端-短信验证码
 *
 * @author xujiaji
 * @since 2020-11-26
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/front/sms")
public class MkSmsFrontController extends BaseController {

    private final UserUtil userUtil;
    private final IUserInfoService userInfoService;
    private final IMkSmsService smsService;

    /**
     * 发送验证码
     */
    @PostMapping("/send")
    public ApiResponse<?> sendSms(@RequestBody @Valid SmsCondition request) {
        switch (request.getType()) {
            case Consts.Sms.REGISTER:
                if (userInfoService.isExistMobile(request.getMobile())) {
                    throw new StatusException(Status.ERROR_PHONE_REGISTERED);
                }
                break;
            case Consts.Sms.MODIFY_MOBILE:
                if (userInfoService.isExistMobile(request.getMobile())) {
                    throw new StatusException(Status.ERROR_PHONE_BOUND);
                }
                break;
        }
        smsService.sendSms(request.getMobile(), request.getType());
        return ApiResponse.ofSuccess();
    }

    /**
     * 发送修改信息验证码
     */
    @PostMapping("/send/modify")
    public ApiResponse<?> sendSmsModify() {
        val user = userInfoService.getUserDetails(userUtil.currentUserIdNotNull());
        smsService.sendSms(user.getPhone(), Consts.Sms.MODIFY);
        return ApiResponse.ofSuccess();
    }
}
