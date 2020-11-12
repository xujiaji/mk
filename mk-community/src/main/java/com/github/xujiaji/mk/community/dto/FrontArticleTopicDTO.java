package com.github.xujiaji.mk.community.dto;

import lombok.Data;

@Data
public class FrontArticleTopicDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 话题图片
     */
    private String image;

    /**
     * 略缩图
     */
    private String thumb;

    /**
     * 话题描述
     */
    private String content;
}
