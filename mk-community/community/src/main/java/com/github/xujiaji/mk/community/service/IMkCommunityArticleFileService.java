package com.github.xujiaji.mk.community.service;

import com.github.xujiaji.mk.community.entity.MkCommunityArticleFile;
import com.github.xujiaji.mk.common.base.BaseIService;

import java.util.List;

/**
 * <p>
 * 文件使用 服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface IMkCommunityArticleFileService extends BaseIService<MkCommunityArticleFile> {

    /**
     * 通过文章id获取图片url列表
     * @param articleId
     * @return
     */
    List<String> getUrlsByArticleId(Long articleId, Integer articleFileType);

    /**
     * 通过年月获取图片
     * @param yearMonth
     * @param articleFileType
     * @return
     */
    List<String> getUrlsByYearMonth(Long userId, String yearMonth, Integer articleFileType);
}
