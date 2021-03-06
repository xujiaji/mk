package com.github.xujiaji.mk.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiajixu
 * @date 2020/11/20 12:58
 */
@Data
public class FrontCollectAndPraiseDTO {

    /**
     * 通知id
     */
    private Long id;

    /**
     * 帖子id
     */
    private Long articleId;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 类型0帖子评论，1评论回复，2帖子点赞，3回复点赞，4帖子收藏，5评论收藏
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

}
