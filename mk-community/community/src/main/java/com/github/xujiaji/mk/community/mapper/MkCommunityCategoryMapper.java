package com.github.xujiaji.mk.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xujiaji.mk.community.dto.FrontCategoryDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityCategory;

import java.util.List;

/**
 * <p>
 * 社区动态—分类表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface MkCommunityCategoryMapper extends BaseMapper<MkCommunityCategory> {

    List<FrontCategoryDTO> selectFrontAll();
}
