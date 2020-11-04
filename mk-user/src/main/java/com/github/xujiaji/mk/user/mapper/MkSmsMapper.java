package com.github.xujiaji.mk.user.mapper;

import com.github.xujiaji.mk.user.entity.MkSms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 短信验证码表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
public interface MkSmsMapper extends BaseMapper<MkSms> {

    boolean isExpireByPhoneAndTypeAndGreaterCreateTime(@Param("mobile") String mobile, @Param("type") int type, @Param("nowSub10Date") Date nowSub10Date, @Param("code") Integer code);
}
