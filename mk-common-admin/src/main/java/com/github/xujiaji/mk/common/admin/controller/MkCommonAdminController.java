package com.github.xujiaji.mk.common.admin.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.payload.AddConfigCondition;
import com.github.xujiaji.mk.common.payload.EditConfigCondition;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @menu 通用配置管理
 * @author jiajixu
 * @date 2020/10/26 14:13
 */
@RestController
@RequestMapping("/common-admin/config")
public class MkCommonAdminController extends BaseController {
    @Autowired
    private MkCommonServiceImpl mkCommonService;

    /**
     * 添加配置
     * @param: {@link AddConfigCondition}
     * @return: {@link ApiResponse}
     */
    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody @Valid AddConfigCondition config) {
        mkCommonService.addConfig(config);
        return successAdd();
    }

    /**
     * 编辑配置
     * @param: {@link EditConfigCondition}
     * @return: {@link ApiResponse}
     */
    @PutMapping("/edit")
    public ApiResponse<?> edit(@RequestBody @Valid EditConfigCondition config) {
        mkCommonService.editConfig(config);
        return successUpdate();
    }
}
