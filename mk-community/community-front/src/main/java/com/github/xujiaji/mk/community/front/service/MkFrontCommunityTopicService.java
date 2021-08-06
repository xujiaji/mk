package com.github.xujiaji.mk.community.front.service;

import com.github.xujiaji.mk.community.dto.FrontTopicDTO;
import com.github.xujiaji.mk.community.service.impl.MkCommunityTopicServiceImpl;
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
public class MkFrontCommunityTopicService extends MkCommunityTopicServiceImpl {

    public List<FrontTopicDTO> all() {
        return baseMapper.selectFrontAll();
    }
}
