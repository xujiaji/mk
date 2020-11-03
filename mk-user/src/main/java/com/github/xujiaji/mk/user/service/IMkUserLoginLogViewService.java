package com.github.xujiaji.mk.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.user.entity.MkUserLoginLogView;
import com.github.xujiaji.mk.common.base.BaseIService;
import com.github.xujiaji.mk.user.payload.LogPageCondition;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-03
 */
public interface IMkUserLoginLogViewService extends BaseIService<MkUserLoginLogView> {

    /**
     * 通过条件获取登录日志
     */
    IPage<MkUserLoginLogView> pageSearch(LogPageCondition request, Page<MkUserLoginLogView> page);
}
