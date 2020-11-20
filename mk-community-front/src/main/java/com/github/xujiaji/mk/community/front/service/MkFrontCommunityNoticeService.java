package com.github.xujiaji.mk.community.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.community.dto.CommentNoticeDTO;
import com.github.xujiaji.mk.community.service.impl.MkCommunityNoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * @author jiajixu
 * @date 2020/11/19 16:39
 */
@RequiredArgsConstructor
@Service
public class MkFrontCommunityNoticeService extends MkCommunityNoticeServiceImpl {

    private final CommonUtil commonUtil;

    public IPage<CommentNoticeDTO> commentPage(Long userId, Page<CommentNoticeDTO> page) {
        val result = baseMapper.selectCommentNotice(userId, page);
        for (CommentNoticeDTO record : result.getRecords()) {
            record.setBeforeText(commonUtil.getShortTime(record.getCreateTime()));
        }
        return result;
    }
}
