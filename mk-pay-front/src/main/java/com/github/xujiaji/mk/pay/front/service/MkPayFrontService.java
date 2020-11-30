package com.github.xujiaji.mk.pay.front.service;

import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.pay.front.pay.BaseMkPay;
import com.github.xujiaji.mk.pay.front.pay.PayRequest;
import com.github.xujiaji.mk.pay.front.pay.ali.MkAliPay;
import com.github.xujiaji.mk.pay.front.pay.ios.MkIOSPay;
import com.github.xujiaji.mk.pay.front.pay.qq.MkQQPay;
import com.github.xujiaji.mk.pay.front.pay.wx.MkWXPay;
import com.github.xujiaji.mk.pay.service.impl.MkPayServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author jiajixu
 * @date 2020/11/26 15:00
 */
@RequiredArgsConstructor
@Service
public class MkPayFrontService extends MkPayServiceImpl {

    private final IUserInfoService userInfoService;
    private final MkWXPay wxPay;
    private final MkQQPay qqPay;
    private final MkAliPay aliPay;
    private final MkIOSPay iosPay;

    private BaseMkPay getMkPayByType(int payType) {
        switch (payType) {
            case Consts.Pay.Type.QQ:
                return qqPay;
            case Consts.Pay.Type.WX:
                return wxPay;
            case Consts.Pay.Type.ALI:
                return aliPay;
            case Consts.Pay.Type.IOS:
                return iosPay;
            default:
                throw new RequestActionException("没有这个支付类型");
        }
    }

    /**
     * 支付
     * @param userId 用户id
     * @return 给前端去支付的信息
     */
    public Map<String, Object> pay(Long userId, PayRequest request) {
        val user = userInfoService.getUserDetails(userId);
        if (user == null) {
            throw new RequestActionException("没有这个用户");
        }
        try {
            return getMkPayByType(request.getPayType())
                    .pay(user, request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RequestActionException("下单失败");
        }
    }

    /**
     * 支付结果通知
     * @param payType 支付类型
     * @param notify 通知信息
     * @param request 通知请求
     */
    public Object payNotify(int payType, @Nullable String notify, HttpServletRequest request) {
        return getMkPayByType(payType).notify(notify, request);
    }

}
