package com.github.xujiaji.mk.community.front.service;

import cn.hutool.core.bean.BeanUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.community.entity.MkCommunityArticle;
import com.github.xujiaji.mk.community.entity.MkCommunityArticleFile;
import com.github.xujiaji.mk.community.front.playload.CommunityArticleAddCondition;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleFileServiceImpl;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleServiceImpl;
import com.github.xujiaji.mk.file.service.IMkFileService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 社区动态—帖子表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@Service
public class MkFrontCommunityArticleService extends MkCommunityArticleServiceImpl {

    private final IMkFileService fileService;
    private final MkCommunityArticleFileServiceImpl articleFileService;

    @Transactional(rollbackFor = Exception.class)
    public void addByRequest(Long userId, CommunityArticleAddCondition request) {
        val article = BeanUtil.copyProperties(request, MkCommunityArticle.class);
        article.setUserId(userId);
        // 添加文章到数据库
        add(article);
        for (int i = 0; i < request.getImages().size(); i++) {
            val mkFile = fileService.uploadBase64(userId, request.getImages().get(i), Consts.FileType.IMAGE);
            val articleFile = new MkCommunityArticleFile();
            articleFile.setArticleId(article.getId());
            articleFile.setFileId(mkFile.getId());
            articleFile.setType(Consts.ArticleFileType.IMAGE);
            articleFile.setOrderNum(i);
            articleFileService.add(articleFile);

            val mkThumbnailFile = fileService.generateThumbnail(mkFile);
            val articleThumbnailFile = new MkCommunityArticleFile();
            articleThumbnailFile.setArticleId(article.getId());
            articleThumbnailFile.setFileId(mkThumbnailFile.getId());
            articleThumbnailFile.setType(Consts.ArticleFileType.IMAGE_THUMBNAIL);
            articleThumbnailFile.setOrderNum(i);
            articleFileService.add(articleThumbnailFile);
        }

    }
}
