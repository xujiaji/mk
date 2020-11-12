package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.community.dto.FrontCategoryDTO;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @menu 前端-社区动态—帖子-分类
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/community/category")
public class MkFrontCommunityCategoryController extends BaseController {

    private final MkFrontCommunityCategoryService categoryService;

    /**
     * 所有分类
     */
    @GetMapping("/all")
    public ApiResponse<List<FrontCategoryDTO>> all() {
        return success(categoryService.all());
    }
}
