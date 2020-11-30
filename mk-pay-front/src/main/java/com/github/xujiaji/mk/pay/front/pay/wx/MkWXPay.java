package com.github.xujiaji.mk.pay.front.pay.wx;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.pay.front.pay.BaseMkPay;
import com.github.xujiaji.mk.pay.front.pay.PayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付处理
 * @author jiajixu
 * @date 2020/11/27 17:45
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MkWXPay extends BaseMkPay {

    private final MkCommonServiceImpl commonService;
    private final Snowflake snowflake;
    private static final String TRADE_TYPE_APP = "APP";
    private static final String TRADE_TYPE_JSAPI = "JSAPI";

    @Override
    public Map<String, Object> pay(
            MkUser user,
            PayRequest request) throws Exception {
        log.info("微信下单");
        MkWXConfig config = new MkWXConfig(
                commonService.valueByKey(Consts.ConfigKey.wxAppId),
                commonService.valueByKey(Consts.ConfigKey.wxMerchant),
                commonService.valueByKey(Consts.ConfigKey.wxPayKey)
        );
        WXPay wxPay = new WXPay(config);

        final long payId = snowflake.nextId();

        val data = MapUtil
                .<String, String>builder()
                .put("appid", config.getAppID())
                .put("mch_id", config.getMchID())
                .put("nonce_str", WXPayUtil.generateNonceStr())
                .put("body", StrUtil.emptyIfNull(request.getBody()))
                .put("out_trade_no", String.valueOf(payId))
                .put("total_fee", String.valueOf(request.getMoney()))
                // 自己的服务器IP地址
                .put("spbill_create_ip", commonService.valueByKey(Consts.ConfigKey.serverIp))
                // 异步通知地址（请注意必须是外网）
                .put("notify_url", commonService.valueByKey(Consts.ConfigKey.baseApiUrl) + Consts.Pay.Notify.WX_URL_PATH);

        // 微信小程序支付
        if (request.getTradeType() == Consts.Pay.TradeType.WX_MINI_APP) {
            //交易类型
            data
                    .put("trade_type", TRADE_TYPE_JSAPI)
                    .put("openid", user.getWxMiniOpenId());
        } else {
            // APP支付
            //交易类型
            data.put("trade_type", TRADE_TYPE_APP);
        }
        //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
        data
                .put("attach", request.getAttach())
                .put("sign", WXPayUtil.generateSignature(data.build(), config.getKey(), WXPayConstants.SignType.MD5));
        //使用官方API请求预付订单
        Map<String, String> response = wxPay.unifiedOrder(data.build());
        log.info("微信订单创建结果：{}", JSONUtil.toJsonStr(response));
        if ("SUCCESS".equals(response.get("return_code"))) {
            //主要返回以下5个参数
            Map<String, String> param = new HashMap<>();
            if (request.getTradeType() == Consts.Pay.TradeType.WX_MINI_APP) { // 小程序支付
                param.put("appId", config.getAppID());
                param.put("nonceStr", WXPayUtil.generateNonceStr());
                param.put("package", "prepay_id=" + response.get("prepay_id"));
                param.put("signType", "MD5");
                param.put("timeStamp", System.currentTimeMillis() / 1000 + "");
            } else {
                param.put("appid", config.getAppID());
                param.put("partnerid", response.get("mch_id"));
                param.put("prepayid", response.get("prepay_id"));
                param.put("package", "Sign=WXPay");
                param.put("noncestr", WXPayUtil.generateNonceStr());
                param.put("timestamp", System.currentTimeMillis() / 1000 + "");
            }
            param.put("sign", WXPayUtil.generateSignature(param, config.getKey(), WXPayConstants.SignType.MD5));
            createPay(payId, request.getTradeType(), user.getId(), request.getMoney());
            return payInfo(payId, param);
        }
        throw new RequestActionException("下单失败");
    }

    private String handleWxNotifyRequest(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        //将InputStream转换成xmlString
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            IoUtil.close(inputStream);
        }
        return sb.toString();
    }

    private String handleWxRequestXml(String resXml) {
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(resXml);         // 调用官方SDK转换成map类型数据

            MkWXConfig config = new MkWXConfig(
                    commonService.valueByKey(Consts.ConfigKey.wxAppId),
                    commonService.valueByKey(Consts.ConfigKey.wxMerchant),
                    commonService.valueByKey(Consts.ConfigKey.wxPayKey)
            );

            WXPay wxpay = new WXPay(config);

            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {//验证签名是否有效，有效则进一步处理

                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//商户订单号
                String attach = notifyMap.get("attach");//商户订单号
                if (return_code.equals("SUCCESS")) {
                    if (out_trade_no != null) {
                        // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                        // 注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                        //特殊情况清楚

//                        if (attach.equals(Consts.Pay.SHOPPING_MAIL)) {
//                            MailOrderDO mailOrderDO = mailOrderAppMapper.getOrderByOrderNumber(out_trade_no);
//                            if (mailOrderDO.getState() != 0) {
//                                String ok = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
//                                return ok;
//                            }
//                        }
                        //业务数据持久化
                        paySuccess(out_trade_no, attach);
                        log.info("微信手机支付回调成功订单号:{}", out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                        log.info("微信手机支付回调失败订单号:{}", out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
                log.error("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            log.error("手机支付回调通知失败", e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

    @Override
    public Object notify(String notify, HttpServletRequest request) {
        log.info("进入微信回调函数");
        try {
            val resXml = handleWxNotifyRequest(request);
            return handleWxRequestXml(resXml);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("微信手机支付失败:" + e.getMessage());
            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
    }

}
