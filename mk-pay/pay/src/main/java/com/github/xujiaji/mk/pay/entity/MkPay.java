package com.github.xujiaji.mk.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_pay")
public class MkPay extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 多少钱
     */
    private Long money;

    /**
     * 支付类型1 QQ，2微信，3支付宝，4iOS
     */
    private Integer type;

    /**
     * 支付状态0未支付，1已支付，2已退款
     */
    private Integer state;

    /**
     * 交易类型1app，2h5，3快应用，4微信小程序
     */
    private Integer tradeType;


}
