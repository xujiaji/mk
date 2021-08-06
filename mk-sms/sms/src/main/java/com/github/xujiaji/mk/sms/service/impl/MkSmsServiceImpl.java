package com.github.xujiaji.mk.sms.service.impl;

import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.entity.MkSms;
import com.github.xujiaji.mk.common.service.IMkSmsService;
import com.github.xujiaji.mk.sms.channel.ISmsChannel;
import com.github.xujiaji.mk.sms.mapper.MkSmsMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * 短信验证码表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-26
 */
@RequiredArgsConstructor
@Service
public class MkSmsServiceImpl extends BaseServiceImpl<MkSmsMapper, MkSms> implements IMkSmsService {

    private final ISmsChannel smsChannel;

    @Override
    public boolean isValid(String mobile, int type, int code) {
        LocalDateTime nowSub10 = LocalDateTime.now();
        Date nowSub10Date = Date.from(nowSub10.minusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());
        return baseMapper.isExpireByPhoneAndTypeAndGreaterCreateTime(mobile, type, nowSub10Date, code);
    }

    @Override
    public void sendSms(String mobile, int type) {
        // 生成验证码
        final String code = String.valueOf(new Random().nextInt(8999) + 1000);
        smsChannel.sendMsgCode(type, mobile, code);
        val mkSms = new MkSms();
        mkSms.setCode(Integer.valueOf(code));
        mkSms.setMobile(mobile);
        mkSms.setType(type);
        add(mkSms);
    }
}
