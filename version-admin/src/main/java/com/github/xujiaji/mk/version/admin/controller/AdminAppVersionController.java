package com.github.xujiaji.mk.version.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.version.admin.payload.AppVersionAddCondition;
import com.github.xujiaji.mk.version.admin.payload.AppVersionEditCondition;
import com.github.xujiaji.mk.version.entity.MkAppVersion;
import com.github.xujiaji.mk.version.service.impl.MkAppVersionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 后端管理-App版本信息
 * @author jiajixu
 * @date 2020/6/28 10:25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/app/version")
public class AdminAppVersionController extends BaseController {
    private final MkAppVersionServiceImpl appVersionService;

    /**
     * 获取版本列表
     * @return 版本信息实体
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<MkAppVersion>> versions(PageCondition request) {
        return successPage(appVersionService.page(mapPage(request)));
    }

    /**
     * 添加新版本
     * @param request 新版本添加请求json实体
     * @return 是否添加成功
     */
    @PostMapping
    public ApiResponse<?> add(@Valid @RequestBody AppVersionAddCondition request) {
        appVersionService.add(BeanUtil.copyProperties(request, MkAppVersion.class));
        return successAdd();
    }

    /**
     * 版本修改
     * @return 是否修改成功
     */
    @PutMapping
    public ApiResponse<?> modify(@RequestBody AppVersionEditCondition request) {
        appVersionService.editById(BeanUtil.copyProperties(request, MkAppVersion.class));
        return successUpdate();
    }

    /**
     * 删除这个版本
     * @param id 版本id
     */
    @DeleteMapping
    public ApiResponse<?> delete(@NotNull(message = "id不能为空") Long id) {
        appVersionService.deleteById(id);
        return successDelete();
    }
}
