package com.github.xujiaji.mk.user.front.service;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.common.util.ConstellationUtil;
import com.github.xujiaji.mk.file.service.impl.MkFileServiceImpl;
import com.github.xujiaji.mk.user.front.vo.UserSimpleInfoVO;
import com.github.xujiaji.mk.user.service.impl.MkUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author jiajixu
 * @date 2020/10/26 10:47
 */
@RequiredArgsConstructor
@Service
public class MkUserFrontService extends MkUserServiceImpl {

    private final CommonUtil commonUtil;
    private final MkFileServiceImpl fileService;

    public MkUser getUserHidePhoneAndEmailById(Long id) {
        val userView = getById(id);
        userView.setPhone(commonUtil.hidePhone(userView.getPhone()));
        userView.setEmail(commonUtil.hideEmail(userView.getEmail()));
        return userView;
    }

    public UserSimpleInfoVO simpleInfo(Long userId) {
        val user = getById(userId);
        if (user == null) {
            throw new RequestActionException("没有这个用户");
        }
        val userSimpleInfo = BeanUtil.copyProperties(user, UserSimpleInfoVO.class);
        if (user.getAvatar() != null) {
            userSimpleInfo.setAvatar(fileService.getPathById(user.getAvatar()));
        }
        userSimpleInfo.setBirthInApp(Duration.between(user.getCreateTime(), LocalDateTime.now()).toDays() + "天");
        if (userSimpleInfo.getBirthday() != null) {
            int year = userSimpleInfo.getBirthday().getYear();
            userSimpleInfo.setAfterYear(((year % 100) - (year % 100) % 5) + "后");
            userSimpleInfo.setConstellation(ConstellationUtil.getConstellation(userSimpleInfo.getBirthday()));
        }
        return userSimpleInfo;
    }
}
