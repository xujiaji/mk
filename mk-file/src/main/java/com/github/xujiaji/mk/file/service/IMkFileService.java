package com.github.xujiaji.mk.file.service;

import com.github.xujiaji.mk.file.entity.MkFile;
import com.github.xujiaji.mk.common.base.BaseIService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
public interface IMkFileService extends BaseIService<MkFile> {

    MkFile getByPath(String path);

    String getPathById(Long id);

    MkFile cloneNewFile(MkFile mkFile);

    MkFile upload(MultipartFile multipartFile, Integer type);

    MkFile uploadBase64(Long userId, String base64Image, Integer type);

    MkFile generateThumbnail(MkFile mkFile);
}
