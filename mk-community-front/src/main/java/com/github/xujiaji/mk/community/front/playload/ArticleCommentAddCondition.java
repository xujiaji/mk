package com.github.xujiaji.mk.community.front.playload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiajixu
 * @date 2020/11/13 15:17
 */
@Data
public class ArticleCommentAddCondition {

    /**
     * 评论的帖子id
     */
    private Long articleId;

    /**
     * 回复的回复id
     */
    private Long replyId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论不能为空")
    private String content;

    /**
     * 评论添加的图片
     */
    private List<String> images;
}
