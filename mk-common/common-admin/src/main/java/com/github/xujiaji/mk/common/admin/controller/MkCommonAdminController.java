package com.github.xujiaji.mk.common.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.entity.MkCommon;
import com.github.xujiaji.mk.common.payload.AddConfigCondition;
import com.github.xujiaji.mk.common.payload.EditConfigCondition;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @menu 通用配置管理
 * @author jiajixu
 * @date 2020/10/26 14:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/common/config")
public class MkCommonAdminController extends BaseController {
    private final MkCommonServiceImpl mkCommonService;

    /**
     * 添加配置
     */
    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody @Valid AddConfigCondition config) {
        mkCommonService.add(BeanUtil.copyProperties(config, MkCommon.class));
        return successAdd();
    }

    /**
     * 编辑配置
     */
    @PutMapping("/edit")
    public ApiResponse<?> edit(@RequestBody @Valid EditConfigCondition config) {
        mkCommonService.editById(BeanUtil.copyProperties(config, MkCommon.class));
        return successUpdate();
    }

    /**
     * 配置列表
     */
    @GetMapping("/list")
    public ApiResponse<List<MkCommon>> list() {
        return success(mkCommonService.list());
    }

}
