package com.github.xujiaji.mk.security.admin.service;

import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.mapper.MkSecPermissionMapper;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val mkUser = userInfoService.getUserByUsername(username);
        if (mkUser == null) {
            throw new UsernameNotFoundException("没有这个用户");
        }
        val secUser = mkSecUserMapper.selectByUserId(mkUser.getId());
        val secRoles = mkSecRoleMapper.selectBySecUserId(secUser.getId());
        List<Long> roleIds = secRoles.stream()
                .map(MkSecRole::getId)
                .collect(Collectors.toList());
        val secPermissions = mkSecPermissionMapper.selectByRoleIdList(roleIds);
        return UserPrincipal.create(secUser, mkUser, secRoles, secPermissions);
    }
}
