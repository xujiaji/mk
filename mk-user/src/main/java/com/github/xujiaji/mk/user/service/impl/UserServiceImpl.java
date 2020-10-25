package com.github.xujiaji.mk.user.service.impl;

import com.github.xujiaji.mk.user.entity.User;
import com.github.xujiaji.mk.user.mapper.UserMapper;
import com.github.xujiaji.mk.user.service.IUserService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

}
