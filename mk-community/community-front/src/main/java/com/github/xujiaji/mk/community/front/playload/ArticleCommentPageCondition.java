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
public class ArticleCommentPageCondition extends PageCondition {
    /**
     * 帖子id
     */
    @NotNull(message = "帖子id不能为空")
    private Long articleId;

    /**
     * 类型0最新，1最早
     */
    private Integer type;
}
