package com.github.xujiaji.mk.community.dto;

import lombok.Data;

@Data
public class FrontCategoryDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面
     */
    private String thumb;
}
