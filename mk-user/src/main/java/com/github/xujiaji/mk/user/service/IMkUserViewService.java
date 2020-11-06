package com.github.xujiaji.mk.user.service;

import com.github.xujiaji.mk.user.entity.MkUserView;
import com.github.xujiaji.mk.common.base.BaseIService;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
public interface IMkUserViewService extends BaseIService<MkUserView> {

    MkUserView getUserViewHidePhoneAndEmailById(Long id);
}
