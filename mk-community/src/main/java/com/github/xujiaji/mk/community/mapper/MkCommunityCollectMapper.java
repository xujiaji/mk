package com.github.xujiaji.mk.community.mapper;

import com.github.xujiaji.mk.community.entity.MkCommunityCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 社区动态—收藏帖子表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface MkCommunityCollectMapper extends BaseMapper<MkCommunityCollect> {

    /**
     * 是否被收藏
     * @param articleId
     * @param userId
     * @return
     */
    int isCollected(@Param("articleId") Long articleId, @Param("userId") Long userId, @Param("type") Integer type);
}
