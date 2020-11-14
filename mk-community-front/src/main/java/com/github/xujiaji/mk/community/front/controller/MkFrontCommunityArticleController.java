package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.payload.UserPageCondition;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.community.dto.FrontArticleDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleImageDTO;
import com.github.xujiaji.mk.community.front.playload.ArticlePageCondition;
import com.github.xujiaji.mk.community.front.playload.CommunityArticleAddCondition;
import com.github.xujiaji.mk.community.front.playload.UserArticlePageCondition;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityArticleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    /**
     * 帖子列表
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<FrontArticleDTO>> page(@Valid ArticlePageCondition request) {
        val userId = userUtil.currentUserIdNullable();
        return successPage(articleService.articlePage(userId, mapPage(request), request.getCategoryId(), request.getType()));
    }

    /**
     * 查询某个人的动态文章和收藏的动态文章
     */
    @GetMapping("/own/page")
    public ApiResponse<PageVO<FrontArticleDTO>> page(@Valid UserArticlePageCondition request) {
        val userId = userUtil.currentUserIdNullable();
        return successPage(articleService.articleOwnPage(userId, mapPage(request), request.getUserId(), request.getType()));
    }

    /**
     * 获取某人的文章图集
     */
    @GetMapping("/image/page")
    public ApiResponse<PageVO<FrontArticleImageDTO>> imagePage(@Valid UserPageCondition request) {
        return successPage(articleService.imagePage(request.getUserId(), mapPage(request)));
    }

    /**
     * 帖子详情
     */
    @GetMapping
    public ApiResponse<FrontArticleDTO> details(@NotNull(message = "帖子id不能为空") Long articleId) {
        val userId = userUtil.currentUserIdNullable();
        return success(articleService.details(userId, articleId));
    }

    /**
     * 帖子收藏喜欢
     * @param articleId 帖子id
     * @param type 1收藏 0取消收藏
     */
    @PostMapping("/collect")
    public ApiResponse<?> articleCollect(
            @NotNull(message = "帖子id不能为空") @RequestParam Long articleId,
            @NotNull(message = "1收藏 0取消收藏") @RequestParam Integer type) {
        val userId = userUtil.currentUserIdNotNull();
        articleService.articleCollect(userId, articleId, type);
        return successMessage(type == Consts.DISABLE ? "取消收藏成功" : "收藏成功");
    }


    /**
     * 帖子点赞
     * @param articleId 帖子id
     * @param type 1点赞 0取消点赞
     */
    @PostMapping("/praise")
    public ApiResponse<?> articlePraise(
            @NotNull(message = "帖子id不能为空") @RequestParam Long articleId,
            @NotNull(message = "1点赞 0取消点赞") @RequestParam Integer type) {
        val userId = userUtil.currentUserIdNotNull();
        articleService.articlePraise(userId, articleId, type);
        return successMessage(type == Consts.DISABLE ? "取消点赞成功" : "点赞成功");
    }
}
