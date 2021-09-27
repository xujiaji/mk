package com.github.xujiaji.mk.user.front.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.vo.MkSecUserDetails;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class MkUserPrincipal extends MkUser implements UserDetails {

    /**
     * 用户权限列表
     */
    private Collection<? extends GrantedAuthority> authorities;

    public static MkUserPrincipal create(MkUser mkUser) {
        val mkSecAdminUserPrincipal = BeanUtil.copyProperties(mkUser, MkUserPrincipal.class);
        mkSecAdminUserPrincipal.authorities = ListUtil.toList(new SimpleGrantedAuthority("ROLE_USER"));
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
        return Objects.equals(getEnableStatus(), Consts.ENABLE);
    }

}
