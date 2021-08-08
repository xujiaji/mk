package com.github.xujiaji.mk.security.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.mapper.MkSecPermissionMapper;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.security.admin.vo.MkSecAdminUserPrincipal;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val secUser = mkSecUserMapper.selectOne(new QueryWrapper<MkSecUser>().eq("username", username));
        val secRoles = mkSecRoleMapper.selectBySecUserId(secUser.getId());
        List<Long> roleIds = secRoles.stream()
                .map(MkSecRole::getId)
                .collect(Collectors.toList());
        val secPermissions = mkSecPermissionMapper.selectByRoleIdList(roleIds);
        return MkSecAdminUserPrincipal.create(secUser, secRoles, secPermissions);
    }
}
