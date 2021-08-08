package com.github.xujiaji.mk.security.admin.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.vo.MkSecUserDetails;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * security 中的用户信息
 * </p>
 */
@Getter
@Setter
public class MkSecAdminUserPrincipal extends MkSecUser implements MkSecUserDetails {

    /**
     * 用户角色列表
     */
    private List<String> roles;

    /**
     * 用户权限列表
     */
    private Collection<? extends GrantedAuthority> authorities;

    public static MkSecAdminUserPrincipal create(MkSecUser mkSecUser, List<MkSecRole> roles, List<MkSecPermission> permissions) {
        List<String> roleNames = roles.stream()
                .map(MkSecRole::getName)
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = permissions.stream()
                .filter(permission -> StrUtil.isNotBlank(permission.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        val mkSecAdminUserPrincipal = BeanUtil.copyProperties(mkSecUser, MkSecAdminUserPrincipal.class);
        mkSecAdminUserPrincipal.roles = roleNames;
        mkSecAdminUserPrincipal.authorities = authorities;
        return mkSecAdminUserPrincipal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(getStatus(), Consts.ENABLE);
    }

}
