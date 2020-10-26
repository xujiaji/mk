package com.github.xujiaji.mk.common.admin.controller;

import com.github.xujiaji.mk.common.admin.service.MkCommonAdminService;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.entity.MkCommon;
import com.github.xujiaji.mk.common.payload.AddConfigCondition;
import com.github.xujiaji.mk.common.payload.EditConfigCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @menu 通用配置管理
 * @author jiajixu
 * @date 2020/10/26 14:13
 */
@RestController
@RequestMapping("/common-admin/config")
public class MkCommonAdminController extends BaseController {
    @Autowired
    private MkCommonAdminService mkCommonAdminService;

    /**
     * 添加配置
     */
    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody @Valid AddConfigCondition config) {
        mkCommonAdminService.addConfig(config);
        return successAdd();
    }

    /**
     * 编辑配置
     */
    @PutMapping("/edit")
    public ApiResponse<?> edit(@RequestBody @Valid EditConfigCondition config) {
        mkCommonAdminService.editConfig(config);
        return successUpdate();
    }

    /**
     * 配置列表
     */
    @GetMapping("/list")
    public ApiResponse<List<MkCommon>> list() {
        return success(mkCommonAdminService.list());
    }

}
