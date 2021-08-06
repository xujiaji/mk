package com.github.xujiaji.mk.community.mapper;

import com.github.xujiaji.mk.community.entity.MkCommunityArticleFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件使用 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-12
 */
public interface MkCommunityArticleFileMapper extends BaseMapper<MkCommunityArticleFile> {


    List<String> selectPaths(@Param("articleId") Long articleId, @Param("articleFileType") Integer articleFileType);

    List<String> selectPathsByYearMonth(@Param("userId") Long userId, @Param("yearMonth") String yearMonth, @Param("articleFileType") Integer articleFileType);

    int updateFileDeleted(@Param("articleId") Long articleId);

    int deleteByArticleId(@Param("articleId") Long articleId);
}
