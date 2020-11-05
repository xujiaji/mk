package com.github.xujiaji.mk.security.front.service;

import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    private final IUserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val mkUser = userInfoService.getUserByUsername(username);
        if (mkUser == null) {
            throw new UsernameNotFoundException("没有这个用户");
        }
        return UserPrincipal.create(mkUser);
    }
}
