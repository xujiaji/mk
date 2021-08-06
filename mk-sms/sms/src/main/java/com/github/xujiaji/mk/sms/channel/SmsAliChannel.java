package com.github.xujiaji.mk.sms.channel;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.sms.dto.AliSmsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * @author jiajixu
 * @date 2020/11/25 11:10
 */
@Slf4j
@RequiredArgsConstructor
public class SmsAliChannel implements ISmsChannel {
    private final MkCommonServiceImpl commonService;

    public void sendMsgCode(int type, String mobile, String code) {

        String templateCodeKey = null;
        switch (type) {
            case Consts.Sms.NORMAL:
                templateCodeKey = Consts.ConfigKey.smsTemplateNormal;
                break;
            case Consts.Sms.REGISTER:
                templateCodeKey = Consts.ConfigKey.smsTemplateRegister;
                break;
            case Consts.Sms.LOGIN:
                templateCodeKey = Consts.ConfigKey.smsTemplateLogin;
                break;
            case Consts.Sms.MODIFY:
                templateCodeKey = Consts.ConfigKey.smsTemplateModify;
                break;
            case Consts.Sms.MODIFY_MOBILE:
                templateCodeKey = Consts.ConfigKey.smsTemplateModifyPhone;
                break;
            default:
                break;
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", commonService.valueByKey(Consts.ConfigKey.aliSmsKey), commonService.valueByKey(Consts.ConfigKey.aliSmsSecret));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", commonService.valueByKey(Consts.ConfigKey.aliSmsSignName));
        request.putQueryParameter("TemplateCode", commonService.valueByKey(templateCodeKey));
        request.putQueryParameter("TemplateParam",
                String.format("{" +
                                "\"phone\": \"%s\", " +
                                "\"code\": \"%s\", " +
                                "\"product\": \"dsd\"}",
                        mobile, code));
        request.putQueryParameter("SmsUpExtendCode", "1234567");
        request.putQueryParameter("OutId", "yourOutId");
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RequestActionException("验证码发送请求失败");
        }
        log.info("短信发送结果：{}", response.getData());
        val aliSmsModel = JSONUtil.toBean(response.getData(), AliSmsDTO.class);
        if (!"OK".equals(aliSmsModel.getCode())) {
            throw new RequestActionException("验证码发送失败");
        }
    }
}
