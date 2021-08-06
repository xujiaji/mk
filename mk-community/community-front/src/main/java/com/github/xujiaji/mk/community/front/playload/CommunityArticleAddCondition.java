package com.github.xujiaji.mk.community.front.playload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiajixu
 * @date 2020/11/12 18:18
 */
@Data
public class CommunityArticleAddCondition {
    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空")
    private Long categoryId;

    /**
     * 话题列表
     */
    private List<Long> topicIds = new ArrayList<>();

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空")
    private String content;

    /**
     * 图片base64列表
     */
    private List<String> images = new ArrayList<>();
}
