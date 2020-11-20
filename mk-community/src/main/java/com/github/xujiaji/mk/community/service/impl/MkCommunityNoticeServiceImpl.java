package com.github.xujiaji.mk.community.service.impl;

import com.github.xujiaji.mk.community.entity.MkCommunityNotice;
import com.github.xujiaji.mk.community.mapper.MkCommunityNoticeMapper;
import com.github.xujiaji.mk.community.service.IMkCommunityNoticeService;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 社区动态-消息通知 服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-19
 */
@Service
public class MkCommunityNoticeServiceImpl extends BaseServiceImpl<MkCommunityNoticeMapper, MkCommunityNotice> implements IMkCommunityNoticeService {

    @Override
    public void addNotice(Long fromUserId, Long toUserId, Long articleId, Long commentId, int type) {
        val notice = new MkCommunityNotice();
        notice.setFromUserId(fromUserId);
        notice.setToUserId(toUserId);
        notice.setType(type);
        notice.setArticleId(articleId);
        notice.setCommentId(commentId);
        add(notice);
    }
}
