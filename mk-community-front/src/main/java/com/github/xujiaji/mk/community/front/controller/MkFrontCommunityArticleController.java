package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.community.front.playload.CommunityArticleAddCondition;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityArticleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    private final UserUtil userUtil;

    /**
     * 发布帖子
     */
    @PostMapping
    public ApiResponse<?> add(@RequestBody @Valid CommunityArticleAddCondition request) {
        val userId = userUtil.currentUserIdNotNull();
        articleService.addByRequest(userId, request);
        return successAdd();
    }
}
