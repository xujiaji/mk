package com.github.xujiaji.mk.user.service.impl;

import com.github.xujiaji.mk.user.entity.MkSms;
import com.github.xujiaji.mk.user.mapper.MkSmsMapper;
import com.github.xujiaji.mk.user.service.IMkSmsService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p>
 * 短信验证码表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
@Service
public class MkSmsServiceImpl extends BaseServiceImpl<MkSmsMapper, MkSms> implements IMkSmsService {

    @Override
    public boolean isValid(String mobile, int type, int code) {
        LocalDateTime nowSub10 = LocalDateTime.now();
        Date nowSub10Date = Date.from(nowSub10.minusMinutes(10).atZone(ZoneId.systemDefault()).toInstant());
        return baseMapper.isExpireByPhoneAndTypeAndGreaterCreateTime(mobile, type, nowSub10Date, code);
    }
}
