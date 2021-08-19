package com.github.xujiaji.mk.security.service;

import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.playload.AdminAddCondition;
import com.github.xujiaji.mk.security.playload.AdminEditCondition;
import com.github.xujiaji.mk.security.playload.AdminLoginCondition;
import com.github.xujiaji.mk.security.vo.AdminLoginSuccessVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
public interface IMkSecUserService extends BaseIService<MkSecUser> {
    /**
     * 管理员添加
     */
    void adminAdd(AdminAddCondition request);

    void adminEdit(AdminEditCondition request);

    /**
     * 管理员删除
     * @param ids 管理员id列表
     */
    void deleteAdminUserBySecUserIdList(List<Long> ids);

    /**
     * 管理员登录
     */
    AdminLoginSuccessVO login(AdminLoginCondition request, HttpServletRequest hsr);
}
