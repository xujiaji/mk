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

    MkFile upload(MultipartFile multipartFile, String type);
}
