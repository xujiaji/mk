package com.github.xujiaji.mk.pay.front.pay;

import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.pay.entity.MkPay;
import com.github.xujiaji.mk.pay.mapper.MkPayMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 * @author jiajixu
 * @date 2020/11/27 17:45
 */
@Slf4j
public abstract class BaseMkPay {

    @Autowired
    private MkPayMapper payMapper;

    /**
     * 调用支付
     * @param user 用户
     * @param request 支付请求
     */
    public abstract Map<String, Object> pay(
            MkUser user,
            PayRequest request) throws Exception;

    /**
     * 支付通知信息
     * @param notify 通知信息
     * @return 返回对方处理结果
     */
    public abstract Object notify(String notify, HttpServletRequest request);

    /**
     * 创建支付
     * @param payId 支付id
     * @param tradeType 交易类型
     * @param userId 用户id
     * @param money 支付的钱
     */
    public void createPay(long payId, int tradeType, long userId, long money) {
        val mkPay = new MkPay();
        mkPay.setId(payId);
        mkPay.setState(Consts.Pay.State.UNPAID);
        mkPay.setTradeType(tradeType);
        mkPay.setUserId(userId);
        mkPay.setType(Consts.Pay.Type.WX);
        mkPay.setMoney(money);
        if (payMapper.insert(mkPay) == Consts.DISABLE) {
            throw new RequestActionException("创建支付失败");
        }
    }

    public Map<String, Object> payInfo(long payId, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("orderNo", payId);
        return map;
    }

    /**
     * 支付成功后的持久化操作
     * @param payId 支付id
     * @param attach 附加信息
     */
    public void paySuccess(String payId, String attach) {
        log.info("支付成功：{}", payId);
        val mkPay = payMapper.selectById(payId);
        if (mkPay == null) {
            throw new RequestActionException("没有这个订单");
        }
        mkPay.setState(Consts.Pay.State.PAID);
        if (payMapper.updateById(mkPay) == Consts.DISABLE) {
            throw new RequestActionException("订单状态修改失败");
        }
    }
}
