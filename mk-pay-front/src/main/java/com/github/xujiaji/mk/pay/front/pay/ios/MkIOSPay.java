package com.github.xujiaji.mk.pay.front.pay.ios;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.pay.front.pay.BaseMkPay;
import com.github.xujiaji.mk.pay.front.pay.PayRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author jiajixu
 * @date 11/30/20 3:19 PM
 */
@RequiredArgsConstructor
@Component
public class MkIOSPay extends BaseMkPay {
    private final MkCommonServiceImpl commonService;
    private final Snowflake snowflake;

    @Override
    public Map<String, Object> pay(MkUser user, PayRequest request) {
        String verifyResult = IOS_Verify.buyAppVerify(request.getIosData(), request.getIosState() != 1);
        if (verifyResult == null) {
            throw new RequestActionException("验证失败");
        }
        //跟苹果验证有返回结果------------------


        JSONObject job = new JSONObject(verifyResult);
        System.out.println(job);
        String states = job.getStr("status");
        System.out.println(states);
        //验证成功
        if (states.equals("0")) {
            String r_receipt = job.getStr("receipt");
            JSONObject returnJson = new JSONObject(r_receipt);
            String inApp = returnJson.getStr("in_app");

            JSONArray inApps = JSONUtil.parseArray(inApp);

            if (inApps.size() == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new RequestActionException("未获取到交易列表");
            }

            JSONObject currentTransaction = null;
            for (JSONObject app : inApps.jsonIter()) {
                if (request.getIosTransactionId().equals(app.get("transaction_id"))) {
                    currentTransaction = app;
                }
            }

            if (currentTransaction == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new RequestActionException("当前交易不在交易列表中");
            }

            //产品ID
            String product_id = currentTransaction.get("product_id").toString();
            // ios 交易订单
            String transaction_id = currentTransaction.get("transaction_id").toString();

            val payId = snowflake.nextId();
//            createPay(payId, Consts.Pay.TradeType.APP, user.getId(), );
            paySuccess(String.valueOf(payId), null);

        }
        throw new RequestActionException("交易失败");
    }

    @Override
    public Object notify(String notify, HttpServletRequest request) {
        return null;
    }
}
