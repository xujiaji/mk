package com.github.xujiaji.mk.community.front.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.community.dto.FrontArticleDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleImageDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityArticle;
import com.github.xujiaji.mk.community.entity.MkCommunityArticleFile;
import com.github.xujiaji.mk.community.entity.MkCommunityCollect;
import com.github.xujiaji.mk.community.entity.MkCommunityPraise;
import com.github.xujiaji.mk.community.front.playload.CommunityArticleAddCondition;
import com.github.xujiaji.mk.community.front.playload.UserArticlePageCondition;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleFileServiceImpl;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleServiceImpl;
import com.github.xujiaji.mk.community.service.impl.MkCommunityCollectServiceImpl;
import com.github.xujiaji.mk.community.service.impl.MkCommunityPraiseServiceImpl;
import com.github.xujiaji.mk.file.entity.MkFile;
import com.github.xujiaji.mk.file.service.IMkFileService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

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
    private final MkCommunityPraiseServiceImpl praiseService;
    private final MkCommunityCollectServiceImpl collectService;
    private final CommonUtil commonUtil;
    private final MkFrontCommunityNoticeService noticeService;
    private final MkCommonServiceImpl commonService;

    @Transactional(rollbackFor = Exception.class)
    public void addByRequest(Long userId, CommunityArticleAddCondition request) {
        val baseFileUrl = commonService.baseFileUrl();
        val article = BeanUtil.copyProperties(request, MkCommunityArticle.class);
        article.setUserId(userId);
        // 添加文章到数据库
        add(article);
        for (int i = 0; i < request.getImages().size(); i++) {
            val image = request.getImages().get(i);
            // 如果开头的是文件url开头的那么就是图片链接
            MkFile mkFile;
            if (image.startsWith(baseFileUrl)) {
                mkFile = fileService.cloneNewFile(fileService.getByPath(image.replace(baseFileUrl, "")));
            } else {
                mkFile = fileService.uploadBase64(userId, image, Consts.FileType.IMAGE);
            }

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

    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleById(Long userId, Long articleId) {
        val article = baseMapper.selectArticleBy(userId, articleId);
        if (article == null) {
            throw new RequestActionException("您没有这个帖子");
        }
        articleFileService.getBaseMapper().updateFileDeleted(articleId);
        articleFileService.getBaseMapper().deleteByArticleId(articleId);
        baseMapper.deleteById(articleId);
    }

    private void buildArticles(Long userId, List<FrontArticleDTO> articleDTOS) {
        for (FrontArticleDTO a : articleDTOS) {
            if (userId != null) {
                a.setCollected(collectService.collectStatus(a.getId(), userId, Consts.CollectType.ARTICLE) > 0 ? Consts.POSITIVE : Consts.NEGATIVE);
                a.setPraised(praiseService.praiseStatus(a.getId(), userId, Consts.PraiseType.ARTICLE) > 0 ? Consts.POSITIVE : Consts.NEGATIVE);
            }
            a.setBeforeText(commonUtil.getShortTime(a.getUpdateTime()));
            a.setThumbnails(articleFileService.getUrlsByArticleId(a.getId(), Consts.ArticleFileType.IMAGE_THUMBNAIL));
            a.setImages(articleFileService.getUrlsByArticleId(a.getId(), Consts.ArticleFileType.IMAGE));
        }
    }

    public IPage<FrontArticleDTO> articlePage(Long userId, Page<FrontArticleDTO> page, Long categoryId, Integer type) {
        val articlePage = baseMapper.articlePage(page, categoryId, type);
        buildArticles(userId, articlePage.getRecords());
        return articlePage;
    }


    public IPage<FrontArticleDTO> articleOwnPage(Long currentUserId, Page<FrontArticleDTO> page, Long ownUserId, Integer type) {
        val articlePage =  (type == UserArticlePageCondition.Type.OWN)
                ? baseMapper.ownArticlePage(page, ownUserId)
                : baseMapper.collectArticlePage(page, ownUserId);
        buildArticles(currentUserId, articlePage.getRecords());
        return articlePage;
    }

    public IPage<FrontArticleImageDTO> imagePage(Long userId, Page<FrontArticleImageDTO> page) {
        val imagePage = baseMapper.articleImagePage(page, userId);

        for (FrontArticleImageDTO record : imagePage.getRecords()) {
            record.setThumbnails(articleFileService.getUrlsByYearMonth(userId, record.getYearMonth(), Consts.ArticleFileType.IMAGE_THUMBNAIL));
            record.setImages(articleFileService.getUrlsByYearMonth(userId, record.getYearMonth(), Consts.ArticleFileType.IMAGE));
        }
        return imagePage;
    }

    public FrontArticleDTO details(@Nullable Long userId, Long articleId) {
        val article = baseMapper.selectArticleDetails(articleId);
        buildArticles(userId, Lists.newArrayList(article));
        return article;
    }

    public void articleCollect(Long userId, Long articleId, Integer type) {
        if ((type == Consts.DISABLE ? baseMapper.updateCollectSub1(articleId) : baseMapper.updateCollectAdd1(articleId)) == 0) {
            throw new RequestActionException("没有这个动态");
        }
        int status = collectService.collectStatus(articleId, userId, type);
        if (type == Consts.DISABLE) {
            if (status <= 0) {
                throw new RequestActionException("您还没有收藏");
            }
            collectService.getBaseMapper()
                    .delete(
                            new QueryWrapper<MkCommunityCollect>()
                                    .eq("user_id", userId)
                                    .eq("collected_id", articleId)
                                    .eq("type", Consts.CollectType.ARTICLE));
        } else {
            if (status > 0) {
                throw new RequestActionException("您已收藏");
            }
            val collect = new MkCommunityCollect();
            collect.setCollectedId(articleId);
            collect.setUserId(userId);
            collect.setType(Consts.CollectType.ARTICLE);
            collectService.add(collect);
            noticeService.addNotice(userId, baseMapper.selectAuthorIdByArticleId(articleId), articleId, null, Consts.NoticeType.ARTICLE_COLLECT);
        }
    }

    public void articlePraise(Long userId, Long articleId, Integer type) {
        if ((type == Consts.DISABLE ? baseMapper.updatePraiseSub1(articleId) : baseMapper.updatePraiseAdd1(articleId)) == 0) {
            throw new RequestActionException("没有这个动态");
        }
        if (type == Consts.DISABLE) {
            praiseService.getBaseMapper()
                    .delete(
                            new QueryWrapper<MkCommunityPraise>()
                                    .eq("user_id", userId)
                                    .eq("praised_id", articleId)
                                    .eq("type", Consts.PraiseType.ARTICLE));
        } else {
            val praise = new MkCommunityPraise();
            praise.setPraisedId(articleId);
            praise.setUserId(userId);
            praise.setType(Consts.PraiseType.ARTICLE);
            praiseService.add(praise);
            noticeService.addNotice(userId, baseMapper.selectAuthorIdByArticleId(articleId), articleId, null, Consts.NoticeType.ARTICLE_PRAISE);
        }
    }

}
