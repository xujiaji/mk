package com.github.xujiaji.mk.community.front.playload;

import com.github.xujiaji.mk.common.payload.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/13 12:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlePageCondition extends PageCondition {

    /**
     * 分类id
     */
    @NotNull(message = "请传入分类id")
    private Long categoryId;

    /**
     * 0最新更新  1最新发布
     */
    private Integer type;
}
