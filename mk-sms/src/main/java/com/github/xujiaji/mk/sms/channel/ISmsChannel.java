package com.github.xujiaji.mk.sms.channel;

/**
 * @author jiajixu
 * @date 2020/11/26 11:50
 */
public interface ISmsChannel {
     /**
      * 发送验证码
      * @param type 验证码类型{@link com.github.xujiaji.mk.common.base.Consts.Sms}
      * @param mobile 手机号
      * @param code 验证码
      */
     void sendMsgCode(int type, String mobile, String code);
}
