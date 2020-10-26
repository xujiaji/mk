package com.github.xujiaji.mk.user.mapper;

import com.github.xujiaji.mk.common.entity.IUser;
import com.github.xujiaji.mk.user.entity.MkUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xujiaji.mk.user.entity.MkUserPhoneEmailPassword;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
public interface MkUserMapper extends BaseMapper<MkUser> {

    /**
     * 获取用户，包括用户的手机号、邮箱和密码
     * @param userId 用户id
     * @return 用户
     */
    MkUserPhoneEmailPassword selectUserWithPhoneEmailPasswordById(@Param("userId") Long userId);
}
