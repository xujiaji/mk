package com.github.xujiaji.mk.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IUserLoginLogService;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.file.service.impl.MkFileServiceImpl;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserRoleMapper;
import com.github.xujiaji.mk.security.playload.AdminAddCondition;
import com.github.xujiaji.mk.security.playload.AdminEditCondition;
import com.github.xujiaji.mk.security.playload.AdminLoginCondition;
import com.github.xujiaji.mk.security.service.IMkSecUserService;
import com.github.xujiaji.mk.security.util.JwtUtil;
import com.github.xujiaji.mk.security.vo.AdminLoginSuccessVO;
import com.github.xujiaji.mk.security.vo.MkSecUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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

    private final MkSecUserRoleMapper secUserRoleMapper;
    private final MkSecRoleMapper secRoleMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordServiceImpl passwordService;
    private final MkFileServiceImpl fileService;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;

    @Autowired(required = false)
    private IUserLoginLogService userLoginLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminAdd(AdminAddCondition request) {
        // 判断是否有这个角色
        val mkSecRole = secRoleMapper.selectById(request.getRoleId());
        if (mkSecRole == null) {
            throw new RequestActionException("没有这个角色！你选的啥哦～");
        }

        if (baseMapper.selectCount(new QueryWrapper<MkSecUser>().eq("username", request.getUsername())) > 0) {
            throw new RequestActionException("已添加过该管理员了");
        }

        val mkSecUser = new MkSecUser();
        mkSecUser.setStatus(Consts.ENABLE);
        mkSecUser.setUsername(request.getUsername());
        mkSecUser.setPassword(passwordService.encode(request.getPassword()));
        checkInsertSuccess(baseMapper.insert(mkSecUser));

        checkInsertSuccess(secUserRoleMapper.addSecUserRole(mkSecUser.getId(), request.getRoleId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminEdit(AdminEditCondition request) {

        val mkSecUser = baseMapper.selectById(request.getId());
        if (mkSecUser == null) {
            throw new RequestActionException("没有这个管理员无法编辑");
        }

        if (request.getRoleId() != null) {
            // 判断是否有这个角色
            val mkSecRole = secRoleMapper.selectById(request.getRoleId());
            if (mkSecRole == null) {
                throw new RequestActionException("没有这个角色！你选的啥哦～");
            }
            // 先删除之前的角色
            checkDeleteSuccess(secUserRoleMapper.deleteBySecUserId(mkSecUser.getId()));
            // 再添加现在的角色
            checkInsertSuccess(secUserRoleMapper.addSecUserRole(mkSecUser.getId(), request.getRoleId()));
        }

        if (StrUtil.isNotBlank(request.getUsername())) {
            if (baseMapper.selectCount(new QueryWrapper<MkSecUser>().eq("username", request.getUsername())) > 0) {
                throw new RequestActionException("该用户名已被占用");
            }
            mkSecUser.setUsername(request.getUsername());
        }

        mkSecUser.setNickname(request.getNickname());
        mkSecUser.setAvatar(request.getAvatarId());

        if (StrUtil.isNotBlank(request.getPassword())) {
            mkSecUser.setPassword(passwordService.encode(request.getPassword()));
        }
        checkUpdateSuccess(updateById(mkSecUser));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdminUserBySecUserId(Long secUserId) {
        deleteById(secUserId);
        checkDeleteSuccess(secUserRoleMapper.deleteBySecUserId(secUserId));
    }

    @Override
    public AdminLoginSuccessVO login(AdminLoginCondition request, HttpServletRequest hsr) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, request.getRememberMe());

        val principal = (MkSecUserDetails) authentication.getPrincipal();

        if (userLoginLogService != null) {
            userLoginLogService.insertLog(principal.getId(), Consts.LoginType.USERNAME, hsr);
        }

        return AdminLoginSuccessVO.builder()
                .authorization(jwt)
                .authorizationType("Bearer")
                .user(principal)
                .build();
    }
}
