package com.github.xujiaji.mk.user.service;

import com.github.xujiaji.mk.user.entity.MkUserIdNumber;
import com.github.xujiaji.mk.common.base.BaseIService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
public interface IMkUserIdNumberService extends BaseIService<MkUserIdNumber> {

    /**
     * 创建一个普通的id number
     */
    MkUserIdNumber newNormalIdNumber();
}
