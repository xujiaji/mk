package com.github.xujiaji.mk.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.security.entity.SecRole;
import com.github.xujiaji.mk.security.entity.SecUser;
import com.github.xujiaji.mk.security.mapper.SecPermissionMapper;
import com.github.xujiaji.mk.security.mapper.SecRoleMapper;
import com.github.xujiaji.mk.security.mapper.SecUserMapper;
import com.github.xujiaji.mk.security.service.ISecUserService;
import com.github.xujiaji.mk.security.service.IUserInfoService;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Service
public class SecUserServiceImpl extends BaseServiceImpl<SecUserMapper, SecUser> implements ISecUserService {

    @Autowired
    private SecRoleMapper secRoleMapper;
    @Autowired
    private SecPermissionMapper secPermissionMapper;
    @Autowired
    private IUserInfoService userInfoService;

    public UserDetails loadUserBySecUserId(String secUserId) {
        val secUser = baseMapper.selectById(secUserId);
        val user = userInfoService.getUser(secUser.getUserId());
        val secRoles = secRoleMapper.selectBySecUserId(Long.parseLong(secUserId));
        List<Long> roleIds = secRoles.stream()
                .map(SecRole::getId)
                .collect(Collectors.toList());
        val secPermissions = secPermissionMapper.selectByRoleIdList(roleIds);
        return UserPrincipal.create(secUser, user, secRoles, secPermissions);
    }
}
