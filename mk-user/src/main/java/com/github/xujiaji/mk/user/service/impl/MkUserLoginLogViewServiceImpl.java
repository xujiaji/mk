package com.github.xujiaji.mk.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.user.entity.MkUserLoginLogView;
import com.github.xujiaji.mk.user.mapper.MkUserLoginLogViewMapper;
import com.github.xujiaji.mk.user.payload.LogPageCondition;
import com.github.xujiaji.mk.user.service.IMkUserLoginLogViewService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-03
 */
@Service
public class MkUserLoginLogViewServiceImpl extends BaseServiceImpl<MkUserLoginLogViewMapper, MkUserLoginLogView> implements IMkUserLoginLogViewService {

    @Override
    public IPage<MkUserLoginLogView> pageSearch(LogPageCondition request, Page<MkUserLoginLogView> page) {
        return baseMapper.pageSearch(page, request.getUserType(), request.getDevice(), request.getLocation(), request.getLoginIp(), request.getUsername());
    }
}
