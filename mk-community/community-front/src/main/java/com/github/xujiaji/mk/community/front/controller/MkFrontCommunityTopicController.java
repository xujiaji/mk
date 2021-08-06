package com.github.xujiaji.mk.community.front.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.community.dto.FrontTopicDTO;
import com.github.xujiaji.mk.community.front.service.MkFrontCommunityTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @menu 前端-社区动态—帖子-话题
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/community/topic")
public class MkFrontCommunityTopicController extends BaseController {

    private final MkFrontCommunityTopicService topicService;

    /**
     * 所有话题
     */
    @GetMapping("/all")
    public ApiResponse<List<FrontTopicDTO>> all() {
        return success(topicService.all());
    }
}
