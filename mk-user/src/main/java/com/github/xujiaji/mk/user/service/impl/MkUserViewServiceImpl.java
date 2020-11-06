package com.github.xujiaji.mk.user.service.impl;

import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.user.entity.MkUserView;
import com.github.xujiaji.mk.user.mapper.MkUserViewMapper;
import com.github.xujiaji.mk.user.service.IMkUserViewService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
@RequiredArgsConstructor
@Service
public class MkUserViewServiceImpl extends BaseServiceImpl<MkUserViewMapper, MkUserView> implements IMkUserViewService {

    private final CommonUtil commonUtil;

    @Override
    public MkUserView getUserViewHidePhoneAndEmailById(Long id) {
        val userView = getById(id);
        userView.setPhone(commonUtil.hidePhone(userView.getPhone()));
        userView.setEmail(commonUtil.hideEmail(userView.getEmail()));
        return userView;
    }
}
