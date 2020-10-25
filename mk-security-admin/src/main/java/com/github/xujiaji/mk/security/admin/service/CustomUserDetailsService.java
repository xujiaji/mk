package com.github.xujiaji.mk.security.admin.service;

import com.github.xujiaji.mk.security.entity.SecRole;
import com.github.xujiaji.mk.security.mapper.SecPermissionMapper;
import com.github.xujiaji.mk.security.mapper.SecRoleMapper;
import com.github.xujiaji.mk.security.mapper.SecUserMapper;
import com.github.xujiaji.mk.security.service.IUserInfoService;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SecUserMapper secUserMapper;
    @Autowired
    private SecRoleMapper secRoleMapper;
    @Autowired
    private SecPermissionMapper secPermissionMapper;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String secUserId) throws UsernameNotFoundException {
        val secUser = secUserMapper.selectById(secUserId);
        val user = userInfoService.getUser(secUser.getUserId());
        val secRoles = secRoleMapper.selectBySecUserId(Long.parseLong(secUserId));
        List<Long> roleIds = secRoles.stream()
                .map(SecRole::getId)
                .collect(Collectors.toList());
        val secPermissions = secPermissionMapper.selectByRoleIdList(roleIds);
        return UserPrincipal.create(secUser, user, secRoles, secPermissions);
    }
}
