package com.github.xujiaji.mk.pay.front.pay.ali;

import cn.hutool.core.lang.Snowflake;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.pay.front.pay.BaseMkPay;
import com.github.xujiaji.mk.pay.front.pay.PayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jiajixu
 * @date 11/30/20 3:19 PM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MkAliPay extends BaseMkPay {

    private final MkCommonServiceImpl commonService;
    private final Snowflake snowflake;
    //编码类型
    private static String CHARSET = "UTF-8";
    //数据类型
    private static String FORMAT = "json";
    //签名类型
    private static String SIGN_TYPE = "RSA2";

    @Override
    public Map<String, Object> pay(MkUser user, PayRequest request) throws Exception {
        log.info("支付宝下单");
        //支付宝需要的参数serverUrl、appId、private_key、format、charset、public_key、signType
        AlipayClient alipayClient = new DefaultAlipayClient(
                commonService.valueByKey(Consts.ConfigKey.aliGatewayUrl),
                commonService.valueByKey(Consts.ConfigKey.aliAppId),
                commonService.valueByKey(Consts.ConfigKey.aliPrivateKey),
                FORMAT,
                CHARSET,
                commonService.valueByKey(Consts.ConfigKey.aliPublicKey),
                SIGN_TYPE);
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        val payId = snowflake.nextId();
        //商品名称
        model.setSubject(request.getBody());
        //订单号
        model.setOutTradeNo(String.valueOf(payId));
        //支付超时时间
        model.setTimeoutExpress("30m");
        // 支付金额
        model.setTotalAmount(Money.ofMinor(CurrencyUnit.of("CNY"), request.getMoney()).getAmount().toString());
        //将商品类型区分 放在公告回传参数
        model.setPassbackParams(request.getAttach());
        alipayTradeAppPayRequest.setBizModel(model);
        // 回调地址(充值订单)
        // 回调地址
        alipayTradeAppPayRequest.setNotifyUrl(commonService.valueByKey(Consts.ConfigKey.baseApiUrl) + Consts.Pay.Notify.ALI_URL_PATH);
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
        createPay(payId, request.getTradeType(), user.getId(), request.getMoney());
        return payInfo(payId, response.getBody());
    }

    @Override
    public Object notify(String notify, HttpServletRequest request) {
        log.info("进入支付宝回调函数");
        Map<String, String> params = new HashMap<String, String>();
        //从支付宝回调的request域中取值
        Map<String, String[]> requestParams = request.getParameterMap();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //商品订单号
        String out_trade_no = request.getParameter("out_trade_no");            // 商户订单号
        // 当前交易状态
        String tradeStatus = request.getParameter("trade_status");            //交易状态
        // 支付金额
        String totalAmount = request.getParameter("total_amount");            //交易状态
        // 支付时间
        String payDate = request.getParameter("gmt_payment");            //交易状态

        String attach = request.getParameter("passback_params");
        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    commonService.valueByKey(Consts.ConfigKey.aliPublicKey),
                    CHARSET,
                    SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //返回状态存入redis中
        //对验签进行处理
        if (signVerified) {
            //验签通过
            if (tradeStatus.equals("TRADE_SUCCESS")) {
                //支付成功后的业务处理
                paySuccess(out_trade_no, attach);
                log.info("支付成功");
                return "success";
            }
        } else {  //验签不通过
            log.info("验签失败");
            return "failure";
        }
        log.info("支付失败");
        return "failure";
    }
}
