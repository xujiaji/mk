package com.github.xujiaji.mk.file.mapper;

import com.github.xujiaji.mk.file.entity.MkFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
public interface MkFileMapper extends BaseMapper<MkFile> {

    String getPathById(@Param("fileId") Long fileId);

    MkFile selectByPath(@Param("path") String path);
}
