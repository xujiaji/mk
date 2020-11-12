package com.github.xujiaji.mk.file.controller;


import com.github.xujiaji.mk.common.base.ApiResponse;
import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.file.entity.MkFile;
import com.github.xujiaji.mk.file.service.impl.MkFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @menu 文件
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class MkFileController extends BaseController {

    private final MkFileServiceImpl fileService;

    /**
     * @param file 上传文件
     * @param type 文件类型：1.图片；2.视频；3.音频；4.文本
     */
    @PostMapping("/upload")
    public ApiResponse<MkFile> upload(@NotNull(message = "请上传文件") @RequestParam("file") MultipartFile file,
                                      @NotNull(message = "请传入类型") @RequestParam("type") @Pattern(regexp = "[1234]", message = "没有这个类型") String type) {
        return success(fileService.upload(file, Integer.parseInt(type)));
    }

}
