package com.github.xujiaji.mk.community.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiajixu
 * @date 2020/11/13 15:20
 */
@Data
public class FrontArticleCommentDTO {

    /**
     * 评论id
     */
    private Long id;

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
     * 评论内容
     */
    private String content;

    /**
     * 点赞数量
     */
    private Long praiseNum;

    /**
     * 是否已经点赞
     */
    private Integer praised = 0;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

    /**
     * 子评论
     */
    private List<FrontArticleCommentDTO> child;
}
