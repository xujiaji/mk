package com.github.xujiaji.mk.common.service;

import com.github.xujiaji.mk.common.entity.MkUser;

import java.util.List;

/**
 * @author jiajixu
 * @date 2020/10/24 01:00
 */
public interface IUserInfoService {

    /**
     * 获取用户详细信息
     * @param userId 用户id
     * @return 返回用户数据
     */
    MkUser getUserDetails(Long userId);

    /**
     * 通过用户id列表获取用户信息列表
     * @param userIds 用户ID列表
     * @return 用户列表
     */
    List<MkUser> getUserDetailsList(List<Long> userIds);

    /**
     * 通过用户名得到用户
     * @param username 用户名
     * @return 返回用户信息
     */
    MkUser getUserByUsername(String username);

    /**
     * 通过手机号得到用户
     * @param phone 手机号
     * @return 返回用户信息
     */
    MkUser getUserByPhone(String phone);

    /**
     * 通过用户名或手机号创建用户
     * @param phone 手机号
     * @param username 用户民
     * @param password 用户名
     * @return 返回创建的用户
     */
    MkUser createUserByPhoneOrUsername(String phone, String username, String password);
}
