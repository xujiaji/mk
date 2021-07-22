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
public class UserArticlePageCondition extends PageCondition {

    /**
     * 用户id
     */
    @NotNull(message = "请传入用户id")
    private Long userId;

    /**
     * 0自己发布的动态 1收藏的动态
     */
    private Integer type;

    public interface Type {
        int OWN = 0;
        int COLLECT = 1;
    }
}
