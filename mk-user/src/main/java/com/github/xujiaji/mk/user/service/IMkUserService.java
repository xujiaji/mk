package com.github.xujiaji.mk.user.service;

import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.user.entity.MkUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
public interface IMkUserService extends BaseIService<MkUser> {

    MkUser info(Long id);
}
