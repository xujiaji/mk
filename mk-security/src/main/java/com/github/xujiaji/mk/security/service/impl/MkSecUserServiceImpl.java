package com.github.xujiaji.mk.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.entity.MkAdminUser;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.entity.MkSecUserRole;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserRoleMapper;
import com.github.xujiaji.mk.security.service.IMkSecUserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Service
public class MkSecUserServiceImpl extends BaseServiceImpl<MkSecUserMapper, MkSecUser> implements IMkSecUserService {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private MkSecUserRoleMapper secUserRoleMapper;
    @Autowired
    private MkSecRoleMapper secRoleMapper;

    @Override
    public PageVO<MkAdminUser> adminUserPage(Page<MkSecUser> page) {
        IPage<MkSecUser> secUserPage = page(page);
        List<MkAdminUser> list = new ArrayList<>();
        val userDetailsList = userInfoService.getUserDetailsList(secUserPage.getRecords().stream().map(MkSecUser::getUserId).collect(Collectors.toList()));

        for (MkSecUser record : secUserPage.getRecords()) {
            val mkUser = userDetailsList.stream().filter(f -> f.getId().equals(record.getUserId())).findFirst().orElseGet(MkUser::new);
            val mkAdminUser = BeanUtil.copyProperties(mkUser, MkAdminUser.class);
            mkAdminUser.setStatus(record.getStatus());
            mkAdminUser.setSecUserId(record.getId());
            list.add(mkAdminUser);
        }

        val mkUserPageVO = new PageVO<MkAdminUser>();
        mkUserPageVO.setPages(secUserPage.getCurrent());
        mkUserPageVO.setList(list);
        mkUserPageVO.setTotal(secUserPage.getTotal());
        mkUserPageVO.setSize(secUserPage.getSize());
        return mkUserPageVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAdmin(String phone, String username, Long roleId, String password) {
        // 判断是否有这个角色
        val mkSecRole = secRoleMapper.selectById(roleId);
        if (mkSecRole == null) {
            throw new RequestActionException("没有这个角色！你选的啥哦～");
        }

        MkUser mkUser = null;
        if (StrUtil.isBlank(phone)) {
            mkUser = userInfoService.getUserByPhone(phone);
        } else if (StrUtil.isBlank(username)) {
            mkUser = userInfoService.getUserByUsername(username);
        }

        if (mkUser == null) { // 这个用户不存在，那么创建这个用户
            mkUser = userInfoService.createUserByPhoneOrUsername(phone, username, password);
        }

        if (baseMapper.selectCount(new QueryWrapper<MkSecUser>().eq("user_id", mkUser.getId())) > 0) {
            throw new RequestActionException("该用户已经就是管理员了");
        }

        val mkSecUser = new MkSecUser();
        mkSecUser.setStatus(Consts.ENABLE);
        mkSecUser.setUserId(mkUser.getId());
        checkInsertSuccess(baseMapper.insert(mkSecUser));

        checkInsertSuccess(secUserRoleMapper.addSecUserRole(mkSecUser.getId(), roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdminUserBySecUserId(Long secUserId) {
        deleteById(secUserId);
        checkDeleteSuccess(secUserRoleMapper.deleteBySecUserId(secUserId));
    }
}
