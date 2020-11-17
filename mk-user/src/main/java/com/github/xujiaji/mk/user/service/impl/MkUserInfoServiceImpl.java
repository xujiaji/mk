package com.github.xujiaji.mk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.entity.MkUserLoginLog;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IPasswordService;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.user.mapper.MkUserLoginLogMapper;
import com.github.xujiaji.mk.user.mapper.MkUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现用户信息服务
 * @author jiajixu
 * @date 2020/10/26 10:22
 */
@Service
@RequiredArgsConstructor
public class MkUserInfoServiceImpl implements IUserInfoService {

    private final MkUserMapper mkUserMapper;
    private final MkUserLoginLogMapper mkUserLoginLogMapper;
    private final IPasswordService passwordService;


    @Override
    public MkUser getUserDetails(Long userId) {
        return mkUserMapper.selectById(userId);
    }

    @Override
    public List<MkUser> getUserDetailsList(List<Long> userIds) {
        return mkUserMapper.selectBatchIds(userIds);
    }

    @Override
    public MkUser getUserByUsername(String username) {
        return mkUserMapper.selectOne(new QueryWrapper<MkUser>().eq("username", username));
    }

    @Override
    public MkUser getUserByPhone(String phone) {
        return mkUserMapper.selectOne(new QueryWrapper<MkUser>().eq("phone", phone));
    }

    @Override
    public MkUser createUserByUsername(String username, String password) {
        val mkUser = new MkUser();
        mkUser.setUsername(username);
        mkUser.setPassword(passwordService.encode(password));
        if (mkUserMapper.insert(mkUser) == Consts.NEGATIVE) {
            throw new RequestActionException("用户创建失败");
        }
        return mkUser;
    }

    @Override
    public int updateUser(MkUser user) {
        return mkUserMapper.updateById(user);
    }

    @Override
    public MkUserLoginLog lastLoginLogBy(Long userId) {
        return mkUserLoginLogMapper.lastLoginLogBy(userId);
    }
}
