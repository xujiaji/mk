package com.github.xujiaji.mk.sms.channel;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.sms.dto.SmsSendDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class Sms253Channel implements ISmsChannel {

    private final MkCommonServiceImpl commonService;

    public void sendMsgCode(int type, String mobile, String code) {
        //短信内容
        String content = null;
        switch (type) {
            case Consts.Sms.NORMAL:
                content = "请在10分钟内按提示输入验证码";
                break;
            case Consts.Sms.REGISTER:
                content = "您正在注册成为新用户，感谢您的支持！";
                break;
            case Consts.Sms.LOGIN:
                content = "您正在登录，若非本人操作，请勿泄露。";
                break;
            case Consts.Sms.MODIFY:
                content = "您正在尝试变更重要信息，请妥善保管账户信息。";
                break;
            case Consts.Sms.MODIFY_MOBILE:
                content = "您正在尝试修改手机号，请妥善保管账户信息。";
                break;
            default:
                break;
        }

        //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
        String smsSingleRequestServerUrl = commonService.valueByKey(Consts.ConfigKey.sms253SendUrl) + "/msg/send/json";

        //状态报告
        String report= "true";

        val requestData = MapUtil.<String, Object>builder()
                .put("account", commonService.valueByKey(Consts.ConfigKey.sms253Account))
                // 用户密码，必填
                .put("password", commonService.valueByKey(Consts.ConfigKey.sms253Password))
                // 短信内容。长度不能超过536个字符，必填
                .put("msg", String.format("【%s】您的验证码：%s，%s", commonService.valueByKey(Consts.ConfigKey.sms253SignName), code, content))
                // 机号码。多个手机号码使用英文逗号分隔，必填
                .put("phone", mobile)
                // 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，默认立即发送，选填
                .put("sendtime", null)
                // 是否需要状态报告（默认false），选填
                .put("report", report)
                // 下发短信号码扩展码，纯数字，建议1-3位，选填
                .put("extend", null)
                .build();


        String requestJson = JSONUtil.toJsonStr(requestData);

        System.out.println("before request string is: " + requestJson);

        String response = HttpUtil.post(smsSingleRequestServerUrl, requestJson);

        System.out.println("response after request result is :" + response);

        SmsSendDTO smsSingleResponse = JSONUtil.toBean(response, SmsSendDTO.class);

        System.out.println("response  toString is :" + smsSingleResponse);

        if (!smsSingleResponse.getCode().equalsIgnoreCase("0")){
            throw new RequestActionException("验证码发送失败，" + smsSingleResponse.getErrorMsg());
        }
    }
}
