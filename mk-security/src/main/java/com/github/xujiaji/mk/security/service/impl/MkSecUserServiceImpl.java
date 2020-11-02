package com.github.xujiaji.mk.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IPasswordService;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.entity.MkAdminUser;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserRoleMapper;
import com.github.xujiaji.mk.security.playload.AdminLoginCondition;
import com.github.xujiaji.mk.security.service.IMkSecUserService;
import com.github.xujiaji.mk.security.util.JwtUtil;
import com.github.xujiaji.mk.security.vo.AdminLoginSuccessVO;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@RequiredArgsConstructor
@Service
public class MkSecUserServiceImpl extends BaseServiceImpl<MkSecUserMapper, MkSecUser> implements IMkSecUserService {

    private final IUserInfoService userInfoService;
    private final MkSecUserRoleMapper secUserRoleMapper;
    private final MkSecUserMapper secUserMapper;
    private final MkSecRoleMapper secRoleMapper;
    private final IPasswordService passwordService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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
    public void addAdmin(String username, Long roleId, String password) {
        // 判断是否有这个角色
        val mkSecRole = secRoleMapper.selectById(roleId);
        if (mkSecRole == null) {
            throw new RequestActionException("没有这个角色！你选的啥哦～");
        }

        MkUser mkUser = userInfoService.getUserByUsername(username);

        if (mkUser == null) { // 这个用户不存在，那么创建这个用户
            mkUser = userInfoService.createUserByUsername(username, password);
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

    @Override
    public AdminLoginSuccessVO login(AdminLoginCondition request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, request.getRememberMe());

        return AdminLoginSuccessVO.builder()
                .authorization(jwt)
                .authorizationType("Bearer")
                .user((UserPrincipal) authentication.getPrincipal())
                .build();
    }
}
