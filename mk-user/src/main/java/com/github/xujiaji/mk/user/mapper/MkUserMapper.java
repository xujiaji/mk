package com.github.xujiaji.mk.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.user.dto.ThirdBindStatusDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-30
 */
public interface MkUserMapper extends BaseMapper<MkUser> {
    /**
     * 是否有人注册了这个手机号
     */
    boolean isExistMobile(@Param("mobile") String mobile);

    /**
     * 通过手机号获取用户
     * @param mobile 手机号
     */
    MkUser selectByPhone(@Param("mobile") String mobile);

    /**
     * 获取三方绑定状态
     * @param userId 用户ID
     * @return 所有绑定状态
     */
    ThirdBindStatusDTO selectBindStatus(@Param("userId") Long userId);
}
