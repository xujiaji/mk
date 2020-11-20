package com.github.xujiaji.mk.community.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.community.dto.CommentNoticeDTO;
import com.github.xujiaji.mk.community.entity.MkCommunityNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 社区动态-消息通知 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-19
 */
public interface MkCommunityNoticeMapper extends BaseMapper<MkCommunityNotice> {

    IPage<CommentNoticeDTO> selectCommentNotice(@Param("userId") Long userId, Page<CommentNoticeDTO> page);
}
