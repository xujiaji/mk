package com.github.xujiaji.mk.user.service.impl;

import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.user.entity.MkUserPhoneEmailPassword;
import com.github.xujiaji.mk.user.mapper.MkUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现用户信息服务
 * @author jiajixu
 * @date 2020/10/26 10:22
 */
@Service
public class MkUserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private MkUserMapper mkUserMapper;

    @Override
    public MkUserPhoneEmailPassword getUserWithPhoneEmailPassword(Long userId) {
        return mkUserMapper.selectUserWithPhoneEmailPasswordById(userId);
    }

}
