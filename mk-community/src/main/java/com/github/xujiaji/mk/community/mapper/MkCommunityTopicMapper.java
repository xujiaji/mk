package com.github.xujiaji.mk.community.mapper;

import com.github.xujiaji.mk.community.dto.FrontTopicDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityTopic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 社区动态—话题表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface MkCommunityTopicMapper extends BaseMapper<MkCommunityTopic> {

    List<FrontTopicDTO> selectFrontAll();
}
