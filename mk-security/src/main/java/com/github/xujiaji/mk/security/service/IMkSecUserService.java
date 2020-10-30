package com.github.xujiaji.mk.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.entity.MkAdminUser;
import com.github.xujiaji.mk.security.entity.MkSecUser;

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
     * 管理员列表
     */
    PageVO<MkAdminUser> adminUserPage(Page<MkSecUser> mapPage);

    /**
     * 管理员添加
     * @param phone 手机号
     * @param username 用户名
     * @param roleId 角色ID
     * @param password 密码
     */
    void addAdmin(String phone, String username, Long roleId, String password);
}
