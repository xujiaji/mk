package com.github.xujiaji.mk.security.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.security.admin.payload.PermissionAddCondition;
import com.github.xujiaji.mk.security.admin.payload.PermissionEditCondition;
import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.service.impl.MkSecPermissionServiceImpl;
import com.github.xujiaji.mk.security.util.SecurityUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @menu 权限管理
 * @author jiajixu
 * @date 2020/10/28 15:08
 */
@RestController
@RequestMapping("/admin/sec/permission")
public class MkSecAdminPermissionController extends BaseController {
    @Autowired
    MkSecPermissionServiceImpl permissionService;

    /**
     * 获取权限树
     */
    @GetMapping("/tree")
    public ApiResponse<List<Map<String, Object>>> tree() {
        val permissions = permissionService.query().orderByAsc("sort").list();
        return success(treeIdAndParentId(permissions));
    }

    /**
     * 用户权限树
     * @return 用户权限树
     */
    @GetMapping("/user/tree")
    public ApiResponse<List<Map<String, Object>>> userTree() {
        val currentSecUserId = SecurityUtil.getCurrentSecUserId();
        val permissions = permissionService.userPermissions(currentSecUserId);
        return success(treeIdAndParentId(permissions));
    }

    private void checkTypeMethod(Integer type, String method) {
        // 如果是按钮，那么必须传入请求方式GET POST PUT ...
        if (type.equals(Consts.PermissionType.BOTTOM) && StrUtil.isEmpty(method)) {
            throw new RequestActionException("如果是按钮类型的接口请求方式（type = 2），那么必须传入请求方式如：GET POST PUT 等");
        }
    }

    /**
     * 添加权限
     */
    @PostMapping("/add")
    public ApiResponse<?> permissionAdd(@RequestBody @Valid PermissionAddCondition request) {
        checkTypeMethod(request.getType(), request.getMethod());
        permissionService.add(request2Entity(request, MkSecPermission.class));
        return successAdd();
    }

    /**
     * 编辑权限
     */
    @PutMapping("/edit")
    public ApiResponse<?> permissionEdit(@RequestBody @Valid PermissionEditCondition request) {
        checkTypeMethod(request.getType(), request.getMethod());
        permissionService.editById(request2Entity(request, MkSecPermission.class));
        return successUpdate();
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/del")
    public ApiResponse<?> permissionDelete(Long id) {
        permissionService.deletePermission(id);
        return successDelete();
    }

}