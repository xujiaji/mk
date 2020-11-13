package com.github.xujiaji.mk.community.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/11/13 23:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FrontArticleCommentDetailsDTO extends FrontArticleCommentDTO {
    /**
     * 回复对象的昵称
     */
    private String replyNickname;
}
