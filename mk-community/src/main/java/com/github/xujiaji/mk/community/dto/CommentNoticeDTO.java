package com.github.xujiaji.mk.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiajixu
 * @date 2020/11/19 17:45
 */
@Data
public class CommentNoticeDTO {

    /**
     * 通知id
     */
    private Long id;

    /**
     * 目标id
     */
    private Long targetId;

    /**
     * 类型0帖子 1评论
     */
    private Integer type;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 通知描述
     */
    private String description;

    /**
     * 多久之前
     */
    private String beforeText;

    private LocalDateTime createTime;

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
    private String targetUserName;

    /**
     * 回复的目标内容
     */
    private String targetContent;
}
