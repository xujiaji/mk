package com.github.xujiaji.mk.community.front.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.community.dto.FrontCollectAndPraiseDTO;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityNoticeService;
import com.github.xujiaji.mk.community.dto.FrontCommentNoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @menu 前端-通知
 * @author jiajixu
 * @date 2020/11/19 16:37
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/community/notice")
public class MkFrontCommunityNoticeController extends BaseController {

    private final MkFrontCommunityNoticeService frontCommunityNoticeService;
    private final UserUtil userUtil;

    /**
     * 评论通知列表
     * @param request
     * @return
     */
    @GetMapping("/comment/page")
    public ApiResponse<PageVO<FrontCommentNoticeDTO>> commentPage(@Valid PageCondition request) {
        val userId = userUtil.currentUserIdNotNull();
        return successPage(frontCommunityNoticeService.commentPage(userId, mapPage(request)));
    }

    /**
     * 收藏点赞通知
     */
    @GetMapping("/collect-praise/page")
    public ApiResponse<PageVO<FrontCollectAndPraiseDTO>> collectAndPraise(@Valid PageCondition request) {
        val userId = userUtil.currentUserIdNotNull();
        return successPage(frontCommunityNoticeService.collectAndPraise(userId, mapPage(request)));
    }

    /**
     * 获取未看消息数量
     */
    @GetMapping("/unread/num")
    public ApiResponse<Long> unreadNum() {
        val userId = userUtil.currentUserIdNotNull();
        return success(frontCommunityNoticeService.unreadNum(userId));
    }

    /**
     * 标记已看过全部消息
     */
    @PutMapping("/read/all")
    public ApiResponse<?> readAll() {
        val userId = userUtil.currentUserIdNotNull();
        frontCommunityNoticeService.readAll(userId);
        return successMessage("阅读了所有消息");
    }
}
