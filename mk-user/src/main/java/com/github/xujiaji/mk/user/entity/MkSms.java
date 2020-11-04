package com.github.xujiaji.mk.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信验证码表
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_sms")
public class MkSms extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private Integer code;

    /**
     * 1普通短信，2注册，3登录，4信息变更，5修改绑定手机号
     */
    private Integer type;


}
