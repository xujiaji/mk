package com.github.xujiaji.mk.security.admin.controller;

import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.security.service.impl.MkSecRolePermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @menu 权限-角色权限管理
 * @author jiajixu
 * @date 2020/10/28 15:08
 */
@RestController
@RequestMapping("/admin/sec/role/permission")
public class MkSecAdminRolePermissionController extends BaseController {
    @Autowired
    MkSecRolePermissionServiceImpl rolePermissionService;


}
