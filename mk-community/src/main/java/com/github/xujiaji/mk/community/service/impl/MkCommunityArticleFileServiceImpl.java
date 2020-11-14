package com.github.xujiaji.mk.community.service.impl;

import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.community.entity.MkCommunityArticleFile;
import com.github.xujiaji.mk.community.mapper.MkCommunityArticleFileMapper;
import com.github.xujiaji.mk.community.service.IMkCommunityArticleFileService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件使用 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@Service
public class MkCommunityArticleFileServiceImpl extends BaseServiceImpl<MkCommunityArticleFileMapper, MkCommunityArticleFile> implements IMkCommunityArticleFileService {

    private final MkCommonServiceImpl commonService;

    @Override
    public List<String> getUrlsByArticleId(Long articleId, Integer articleFileType) {
        val baseFileUrl = commonService.baseFileUrl();
        return baseMapper.selectPaths(articleId, articleFileType).stream().map(p -> baseFileUrl + p).collect(Collectors.toList());
    }

    @Override
    public List<String> getUrlsByYearMonth(String yearMonth, Integer articleFileType) {
        val baseFileUrl = commonService.baseFileUrl();
        return baseMapper.selectPathsByYearMonth(yearMonth, articleFileType).stream().map(p -> baseFileUrl + p).collect(Collectors.toList());
    }
}
