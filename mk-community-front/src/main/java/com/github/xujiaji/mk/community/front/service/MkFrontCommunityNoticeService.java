package com.github.xujiaji.mk.community.front.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.util.CommonUtil;
import com.github.xujiaji.mk.community.dto.FrontCollectAndPraiseDTO;
import com.github.xujiaji.mk.community.dto.FrontCommentNoticeDTO;
import com.github.xujiaji.mk.community.mapper.MkCommunityArticleMapper;
import com.github.xujiaji.mk.community.service.impl.MkCommunityNoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author jiajixu
 * @date 2020/11/19 16:39
 */
@RequiredArgsConstructor
@Service
public class MkFrontCommunityNoticeService extends MkCommunityNoticeServiceImpl {

    private final CommonUtil commonUtil;
    private final MkCommunityArticleMapper articleMapper;

    public IPage<FrontCommentNoticeDTO> commentPage(Long userId, Page<FrontCommentNoticeDTO> page) {
        val result = baseMapper.selectCommentNotice(userId, page);
        for (FrontCommentNoticeDTO record : result.getRecords()) {
            if (record.getType() == Consts.NoticeType.ARTICLE_COMMENT) {
                val imageNum = articleMapper.countArticleImage(record.getArticleId());
                if (imageNum > 0) {
                    val arr = new String[imageNum];
                    Arrays.fill(arr, "[图片]");
                    val imgStr = Stream.of(arr).reduce((s1, s2) -> s1 + s2).get();
                    if (record.getTargetContent() == null) {
                        record.setTargetContent(imgStr);
                    } else {
                        val split = record.getTargetContent().split("\n");
                        if (split.length <= 1) {
                            record.setTargetContent(split[0] + "\n" + imgStr);
                        } else {
                            record.setTargetContent(split[0] + "\n" + imgStr + split[1]);
                        }
                    }

                }
            }
            record.setBeforeText(commonUtil.getShortTime(record.getCreateTime()));
        }
        return result;
    }

    public IPage<FrontCollectAndPraiseDTO> collectAndPraise(Long userId, Page<FrontCollectAndPraiseDTO> page) {
        val result = baseMapper.selectCollectAndPraiseNotice(userId, page);
        for (FrontCollectAndPraiseDTO record : result.getRecords()) {
            record.setBeforeText(commonUtil.getShortTime(record.getCreateTime()));
        }
        return result;
    }

    public Long unreadNum(Long userId) {
        return baseMapper.selectUnreadNum(userId);
    }

    public void readAll(Long userId) {
        baseMapper.readAll(userId);
    }
}
