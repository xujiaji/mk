package com.github.xujiaji.mk.community.front.service;

import com.github.xujiaji.mk.community.dto.FrontArticleCategoryDTO;
import com.github.xujiaji.mk.community.service.impl.MkCommunityArticleCategoryServiceImpl;
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
public class MkFrontCommunityArticleCategoryService extends MkCommunityArticleCategoryServiceImpl {

    public List<FrontArticleCategoryDTO> all() {
        return baseMapper.selectFrontAll();
    }
}
