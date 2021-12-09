package com.github.xujiaji.mk.user.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.security.front.vo.MkUserPrincipal;
import com.github.xujiaji.mk.user.mapper.MkUserMapper;
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

    private final MkUserMapper mkUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = mkUserMapper.selectOne(new QueryWrapper<MkUser>().eq("username", username));
        return MkUserPrincipal.create(user);
    }
}
