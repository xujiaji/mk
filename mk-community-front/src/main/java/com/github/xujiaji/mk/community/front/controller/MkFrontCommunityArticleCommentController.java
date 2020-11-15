package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleCommentDetailsDTO;
import com.github.xujiaji.mk.community.front.playload.ArticleCommentAddCondition;
import com.github.xujiaji.mk.community.front.playload.ArticleCommentDetailsPageCondition;
import com.github.xujiaji.mk.community.front.playload.ArticleCommentPageCondition;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityArticleCommentService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @menu 前端-社区动态—帖子-评论
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/community/article/comment")
public class MkFrontCommunityArticleCommentController extends BaseController {

    private final UserUtil userUtil;
    private final MkFrontCommunityArticleCommentService articleCommentService;

    /**
     * 评论帖子
     */
    @PostMapping
    public ApiResponse<?> commentAdd(@RequestBody @Valid ArticleCommentAddCondition request) {
        val userId = userUtil.currentUserIdNotNull();
        articleCommentService.commentAdd(userId, request);
        return successMessage("评论成功");
    }

    /**
     * 评论点赞
     * @param commentId 评论id
     * @param type 1点赞 0取消点赞
     */
    @PostMapping("/praise")
    public ApiResponse<?> commentPraise(
            @NotNull(message = "评论id不能为空") @RequestParam Long commentId,
            @NotNull(message = "1点赞 0取消点赞") @RequestParam Integer type) {
        val userId = userUtil.currentUserIdNotNull();
        articleCommentService.commentPraise(userId, commentId, type);
        return successMessage(type == Consts.DISABLE ? "取消点赞成功" : "点赞成功");
    }

    /**
     * 评论列表
     */
    @GetMapping("/page")
    public ApiResponse<PageVO<FrontArticleCommentDTO>> commentPage(@Valid ArticleCommentPageCondition request) {
        val userId = userUtil.currentUserIdNullable();
        return successPage(articleCommentService.commentPage(userId, mapPage(request), request.getArticleId(), request.getType()));
    }

    /**
     * 评论详情列表
     */
    @GetMapping("/details/page")
    public ApiResponse<PageVO<FrontArticleCommentDetailsDTO>> commentDetailsPage(@Valid ArticleCommentDetailsPageCondition request) {
        val userId = userUtil.currentUserIdNullable();
        return successPage(articleCommentService.commentDetailsPage(userId, mapPage(request), request.getCommentId()));
    }


}
