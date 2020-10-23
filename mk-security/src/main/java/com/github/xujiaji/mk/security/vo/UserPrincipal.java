package com.github.xujiaji.mk.security.vo;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.security.entity.IUser;
import com.github.xujiaji.mk.security.entity.SecPermission;
import com.github.xujiaji.mk.security.entity.SecRole;
import com.github.xujiaji.mk.security.entity.SecUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 自定义User
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    /**
     * 主键
     */
    private Long secUserId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机
     */
    private String phone;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 性别，男-1，女-2
     */
    private Integer sex;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 状态，启用-1，禁用-0
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户角色列表
     */
    private List<String> roles;

    /**
     * 用户权限列表
     */
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(SecUser secUser, IUser iUser, List<SecRole> roles, List<SecPermission> permissions) {
        List<String> roleNames = roles.stream()
                .map(SecRole::getName)
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = permissions.stream()
                .filter(permission -> StrUtil.isNotBlank(permission.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                secUser.getId(),
                iUser.getId(),
                iUser.getNickname(),
                iUser.getPhone(),
                iUser.getBirthday(),
                iUser.getSex(),
                iUser.getPassword(),
                secUser.getStatus(),
                secUser.getCreateTime(),
                secUser.getUpdateTime(),
                roleNames,
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
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
        return Objects.equals(this.status, Consts.ENABLE);
    }
}