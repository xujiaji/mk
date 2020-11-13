package com.github.xujiaji.mk.community.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiajixu
 * @date 2020/11/13 12:38
 */
@Data
public class FrontArticleDTO {

    /**
     * 文章id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类标题
     */
    private String categoryTitle;

    /**
     * 话题列表
     */
    private List<FrontTopicDTO> topics = new ArrayList<>();

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 收藏数量
     */
    private Long collectNum;

    /**
     * 点赞数
     */
    private Long praiseNum;

    /**
     * 1已收藏 0未收藏
     */
    private Integer collected = 0;

    /**
     * 1已点赞 0未点赞
     */
    private Integer praised = 0;

    /**
     * 评论数量
     */
    private Long commentNum = 0L;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 多久之前
     */
    private String beforeText;

    /**
     * 缩略图列表
     */
    private List<String> thumbnails = new ArrayList<>();

    /**
     * 图片链接
     */
    private List<String> images = new ArrayList<>();

}
