package com.github.xujiaji.mk.pay.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.pay.front.pay.PayRequest;
import com.github.xujiaji.mk.pay.front.service.MkPayFrontService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  前端-支付
 *
 * @author xujiaji
 * @since 2020-11-26
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/front/pay")
public class MkPayFrontController extends BaseController {

    private final MkPayFrontService payFrontService;
    private final UserUtil userUtil;

    /**
     * 苹果iap支付
     */
    @PostMapping("/ios")
    public ApiResponse<?> iosPay(@RequestBody PayRequest payRequest) {
        payFrontService.pay(userUtil.currentUserIdNotNull(), payRequest);
        return successMessage("支付成功");
    }

    /**
     * 微信回调函数
     */
    @PostMapping("/wx/notify")
    public Object wxPayNotify(HttpServletRequest request) {
        return payFrontService.payNotify(Consts.Pay.Type.WX, null, request);
    }

    /**
     * 阿里回调函数
     */
    @PostMapping("/ali/notify")
    public Object aliPayNotify(HttpServletRequest request) {
        return payFrontService.payNotify(Consts.Pay.Type.ALI, null, request);
    }

}
