package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.community.dto.FrontArticleCategoryDTO;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityArticleCategoryService;
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
@RequestMapping("/community/article/category")
public class MkFrontCommunityArticleCategoryController extends BaseController {

    private final MkFrontCommunityArticleCategoryService categoryService;

    /**
     * 所有分类
     */
    @GetMapping("/all")
    public ApiResponse<List<FrontArticleCategoryDTO>> all() {
        return success(categoryService.all());
    }
}
