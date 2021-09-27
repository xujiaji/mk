package com.github.xujiaji.mk.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IUserLoginLogService;
import com.github.xujiaji.mk.common.util.RedisUtil;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.file.service.impl.MkFileServiceImpl;
import com.github.xujiaji.mk.security.config.JwtConfig;
import com.github.xujiaji.mk.security.dto.MkSecUserDTO;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.entity.MkSecUserRole;
import com.github.xujiaji.mk.security.mapper.MkSecRoleMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.security.mapper.MkSecUserRoleMapper;
import com.github.xujiaji.mk.security.playload.AdminAddCondition;
import com.github.xujiaji.mk.security.playload.AdminEditCondition;
import com.github.xujiaji.mk.security.playload.AdminLoginCondition;
import com.github.xujiaji.mk.security.service.IMkSecUserService;
import com.github.xujiaji.mk.security.util.JwtUtil;
import com.github.xujiaji.mk.security.util.SecurityUtil;
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

    private final MkSecUserRoleMapper secUserRoleMapper;
    private final MkSecRoleMapper secRoleMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordServiceImpl passwordService;
    private final MkFileServiceImpl fileService;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;
    private final RedisUtil redisUtil;
    private final JwtConfig jwtConfig;

    @Autowired(required = false)
    private IUserLoginLogService userLoginLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminAdd(AdminAddCondition request) {
        // 判断是否有这些角色
        if (secRoleMapper.countRoleByRoleIds(request.getRoleIds()) == request.getRoleIds().size()) {
            throw new RequestActionException("没有这个角色！你选的啥哦～");
        }

        if (baseMapper.selectCount(new QueryWrapper<MkSecUser>().eq("username", request.getUsername())) > 0) {
            throw new RequestActionException("已添加过该管理员了");
        }

        val mkSecUser = new MkSecUser();
        mkSecUser.setStatus(Consts.ENABLE);
        mkSecUser.setUsername(request.getUsername());
        mkSecUser.setNickname(request.getNickname());
        mkSecUser.setPassword(passwordService.encode(request.getPassword()));
        checkInsertSuccess(baseMapper.insert(mkSecUser));

        for (Long roleId : request.getRoleIds()) {
            checkInsertSuccess(secUserRoleMapper.addSecUserRole(mkSecUser.getId(), roleId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminEdit(AdminEditCondition request) {

        val mkSecUser = baseMapper.selectById(request.getId());
        if (mkSecUser == null) {
            throw new RequestActionException("没有这个管理员无法编辑");
        }


        if (CollectionUtil.isNotEmpty(request.getRoleIds())) {
            // 判断是否有这些角色
            if (secRoleMapper.countRoleByRoleIds(request.getRoleIds()) == request.getRoleIds().size()) {
                throw new RequestActionException("没有这个角色！你选的啥哦～");
            }
            // 先删除之前的角色
            checkDeleteSuccess(secUserRoleMapper.deleteBySecUserId(mkSecUser.getId()));
            // 再添加现在的角色
            for (Long roleId : request.getRoleIds()) {
                checkInsertSuccess(secUserRoleMapper.addSecUserRole(mkSecUser.getId(), roleId));
            }
        }

        if (StrUtil.isNotBlank(request.getUsername())) {
            if (baseMapper.selectCount(new QueryWrapper<MkSecUser>().ne("id", request.getId()).eq("username", request.getUsername())) > 0) {
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
    public IPage<MkSecUserDTO> adminUserPage(Page<MkSecUserDTO> page) {
        IPage<MkSecUserDTO> result = baseMapper.selectUserPage(page);
        for (MkSecUserDTO record : result.getRecords()) {
            record.setRoles(secUserRoleMapper.selectSecRoleBySecUserId(record.getId()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdminUserBySecUserIdList(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
        checkDeleteSuccess(secUserRoleMapper.delete(new QueryWrapper<MkSecUserRole>().in("sec_user_id", ids)));
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

    @Override
    public void logout() {
        redisUtil.delete(jwtConfig.getRedisJwtKeyPrefix() + SecurityUtil.getCurrentUsername());
    }

    @Override
    public AdminLoginSuccessVO tokenRefresh() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = jwtUtil.createJWT(authentication, true);
        return AdminLoginSuccessVO.builder()
                .authorization(jwt)
                .authorizationType("Bearer")
                .build();
    }
}
