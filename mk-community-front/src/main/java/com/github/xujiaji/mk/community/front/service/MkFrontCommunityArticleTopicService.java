package com.github.xujiaji.mk.community.front.service;

import com.github.xujiaji.mk.community.dto.FrontArticleCategoryDTO;
import com.github.xujiaji.mk.community.dto.FrontArticleTopicDTO;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleTopicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 社区动态—分类表 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
@RequiredArgsConstructor
@Service
public class MkFrontCommunityArticleTopicService extends MkCommunityArticleTopicServiceImpl {

    public List<FrontArticleTopicDTO> all() {
        return baseMapper.selectFrontAll();
    }
}
