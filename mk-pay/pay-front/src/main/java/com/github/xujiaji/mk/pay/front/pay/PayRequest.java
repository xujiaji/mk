package com.github.xujiaji.mk.pay.front.pay;

import com.github.xujiaji.mk.common.base.Consts;
import lombok.Data;

/**
 * @author jiajixu
 * @date 11/30/20 6:19 PM
 */
@Data
public class PayRequest {
    /**
     * 支付类型 {@link Consts.Pay.Type}
     */
    private int payType;
    /**
     * 交易类型 {@link Consts.Pay.TradeType}
     */
    private int tradeType;
    /**
     * 支付多少钱
     */
    private long money;
    /**
     * 支付的时候显示的支付详情信息(微信|支付宝)
     */
    private String body;
    /**
     * 附属信息(微信|支付宝)
     */
    private String attach;

    // *******************ios特要**********************
    /**
     * iOS支付数据
     */
    private String iosData;

    /**
     * ios支付模式 1 正式
     */
    private int iosState;

    /**
     * ios交易id
     */
    private String iosTransactionId;
}
