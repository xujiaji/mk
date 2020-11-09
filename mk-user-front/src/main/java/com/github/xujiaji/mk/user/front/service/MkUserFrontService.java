package com.github.xujiaji.mk.user.front.service;

import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.user.service.impl.MkUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * @author jiajixu
 * @date 2020/10/26 10:47
 */
@RequiredArgsConstructor
@Service
public class MkUserFrontService extends MkUserServiceImpl {

    private final CommonUtil commonUtil;

    public MkUser getUserHidePhoneAndEmailById(Long id) {
        val userView = getById(id);
        userView.setPhone(commonUtil.hidePhone(userView.getPhone()));
        userView.setEmail(commonUtil.hideEmail(userView.getEmail()));
        return userView;
    }
}
