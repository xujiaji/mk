package com.github.xujiaji.mk.security.admin.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.admin.payload.RoleAddCondition;
import com.github.xujiaji.mk.security.admin.payload.RoleEditCondition;
import com.github.xujiaji.mk.security.admin.payload.RoleSetPermissionsCondition;
import com.github.xujiaji.mk.security.dto.RoleDTO;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.service.impl.MkSecRoleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @menu 权限-角色管理
 * @author jiajixu
 * @date 2020/10/28 14:22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sec/role")
public class MkSecAdminRoleController extends BaseController {

    private final MkSecRoleServiceImpl secRoleService;

    /**
     * 角色列表
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<RoleDTO>> page(@Valid PageCondition request) {
        val page = secRoleService.rolePage(mapPage(request));
        return successPage(page);
    }

    /**
     * 角色添加
     */
    @PostMapping("/add")
    public ApiResponse<?> roleAdd(@RequestBody @Valid RoleAddCondition request) {
        secRoleService.add(request2Entity(request, MkSecRole.class));
        return successAdd();
    }

    /**
     * 角色编辑
     */
    @PutMapping("/edit")
    public ApiResponse<?> roleEdit(@RequestBody @Valid RoleEditCondition request) {
        secRoleService.editById(request2Entity(request, MkSecRole.class));
        return successUpdate();
    }

    /**
     * 角色删除
     * @param id 角色id
     */
    @DeleteMapping
    public ApiResponse<?> roleDelete(@NotNull(message = "角色ID不能为空") Long id) {
        secRoleService.deleteRoleById(id);
        return successDelete();
    }

    /**
     * 为角色设置权限
     */
    @PutMapping("/set/permission")
    public ApiResponse<?> roleSetPermissions(@RequestBody @Valid RoleSetPermissionsCondition request) {
        secRoleService.roleSetPermissions(request.getId(), request.getPermissionIds());
        return successMessage("设置角色权限成功");
    }
}
