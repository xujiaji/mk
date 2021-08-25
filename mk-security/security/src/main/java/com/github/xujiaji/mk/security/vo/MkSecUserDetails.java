package com.github.xujiaji.mk.security.vo;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author jiajixu
 * @date 2021/8/8 20:45
 */
public interface MkSecUserDetails extends UserDetails {
    /**
     * 用户id
     */
    Long getId();

    /**
     * 用户角色列表
     */
    List<String> getRoles();

}
