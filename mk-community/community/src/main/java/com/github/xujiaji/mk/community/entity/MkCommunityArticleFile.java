package com.github.xujiaji.mk.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件使用
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_community_article_file")
public class MkCommunityArticleFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    private Long articleId;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 类型：0缩略图，1原图片，2视频缩略图，3视频
     */
    private Integer type;

    /**
     * 展示次数
     */
    private Long displayNum;

    /**
     * 排序编号
     */
    private Integer orderNum;


}
