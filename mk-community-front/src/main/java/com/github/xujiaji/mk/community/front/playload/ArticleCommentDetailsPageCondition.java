package com.github.xujiaji.mk.community.front.playload;

import com.github.xujiaji.mk.common.payload.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/13 17:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCommentDetailsPageCondition extends PageCondition {
    /**
     * 评论id
     */
    @NotNull(message = "评论id不能为空")
    private Long commentId;

}
