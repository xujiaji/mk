package com.github.xujiaji.mk.community.front.controller;

import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityNoticeService;
import com.github.xujiaji.mk.community.dto.CommentNoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ApiResponse<PageVO<CommentNoticeDTO>> commentPage(@Valid PageCondition request) {
        val userId = userUtil.currentUserIdNotNull();
        return successPage(frontCommunityNoticeService.commentPage(userId, mapPage(request)));
    }
}
