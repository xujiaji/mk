package com.github.xujiaji.mk.community.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/11/19 17:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FrontCommentNoticeDTO extends FrontCollectAndPraiseDTO {

    /**
     * 内容
     */
    private String content;

    /**
     * 回复的目标用户id
     */
    private Long targetUserId;

    /**
     * 回复的目标用户
     */
    private String targetNickname;

    /**
     * 回复的目标内容
     */
    private String targetContent;
}
