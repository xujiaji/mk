package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @menu 前端-社区动态—帖子
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/community/article")
public class MkFrontCommunityArticleController extends BaseController {

    private final MkFrontCommunityArticleService articleService;

    /**
     * 发布帖子
     */
    @PostMapping
    public ApiResponse<?> add() {
        return successAdd();
    }
}
