package com.github.xujiaji.mk.security.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.payload.AdminStatusChangeCondition;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.entity.MkAdminUser;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.playload.AdminAddCondition;
import com.github.xujiaji.mk.security.service.impl.MkSecUserServiceImpl;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @menu 权限-管理员管理
 * @author jiajixu
 * @date 2020/10/29 11:36
 */
@RestController
@RequestMapping("/admin/sec/user")
public class MkSecAdminUserController extends BaseController {

    @Autowired
    private MkSecUserServiceImpl secUserService;

    /**
     * 管理员列表
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<MkAdminUser>> adminUserPage(@Valid PageCondition request) {
        return success(secUserService.adminUserPage(mapPage(request)));
    }

    /**
     * 管理员添加
     */
    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody @Valid AdminAddCondition request) {
        if (StrUtil.isBlank(request.getPhone()) && StrUtil.isBlank(request.getUsername())) {
            throw new RequestActionException("手机号和用户名必须传一个");
        } else if (StrUtil.isNotBlank(request.getPhone()) && StrUtil.isNotBlank(request.getUsername())) {
            throw new RequestActionException("手机号和用户名只能选填一个");
        }
        secUserService.addAdmin(request.getPhone(), request.getUsername(), request.getRoleId(), request.getPassword());
        return successAdd();
    }

    /**
     * 禁用或启用管理员
     */
    @PutMapping("/status")
    public ApiResponse<?> disable(@RequestBody @Valid AdminStatusChangeCondition request) {
        val mkSecUser = new MkSecUser();
        mkSecUser.setId(request.getSecUserId());
        mkSecUser.setStatus(Integer.valueOf(request.getStatus()));
        secUserService.editById(mkSecUser);
        return successUpdate();
    }
}
