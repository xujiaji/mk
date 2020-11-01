package com.github.xujiaji.mk.security.admin.service;

import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.mapper.MkSecPermissionMapper;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MkSecUserMapper mkSecUserMapper;
    private final MkSecRoleMapper mkSecRoleMapper;
    private final MkSecPermissionMapper mkSecPermissionMapper;
    private final IUserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String secUserId) throws UsernameNotFoundException {
        val secUser = mkSecUserMapper.selectById(secUserId);
        val user = userInfoService.getUserDetails(secUser.getUserId());
        val secRoles = mkSecRoleMapper.selectBySecUserId(Long.parseLong(secUserId));
        List<Long> roleIds = secRoles.stream()
                .map(MkSecRole::getId)
                .collect(Collectors.toList());
        val secPermissions = mkSecPermissionMapper.selectByRoleIdList(roleIds);
        return UserPrincipal.create(secUser, user, secRoles, secPermissions);
    }
}
