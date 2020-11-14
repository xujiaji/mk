package com.github.xujiaji.mk.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiajixu
 * @date 2020/11/14 14:42
 */
@Data
public class FrontArticleImageDTO {

    /**
     * 年月
     */
    private String yearMonth;

    /**
     * 缩略图列表
     */
    private List<String> thumbnails = new ArrayList<>();

    /**
     * 图片链接
     */
    private List<String> images = new ArrayList<>();
}
