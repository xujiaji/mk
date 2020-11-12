package com.github.xujiaji.mk.file.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.service.IFileUrlService;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.common.util.UserUtil;
import com.github.xujiaji.mk.file.entity.MkFile;
import com.github.xujiaji.mk.file.mapper.MkFileMapper;
import com.github.xujiaji.mk.file.service.IMkFileService;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@RequiredArgsConstructor
@Service
public class MkFileServiceImpl extends BaseServiceImpl<MkFileMapper, MkFile> implements IMkFileService, IFileUrlService {

    private final UserUtil userUtil;
    private final Snowflake snowflake;
    private final MkCommonServiceImpl mkCommonService;

    private String getFileName(MultipartFile multipartFile, Integer type, Long userId) {
        // 获取文件名
        String fileName = multipartFile != null ? multipartFile.getOriginalFilename() : null;
        // 获取文件后缀
        String suffix;
        // 如果没有找到尾缀
        if (StrUtil.isBlank(fileName) || !fileName.contains(".")) {
            suffix =
                    type == Consts.FileType.AUDIO ? ".mp3" :
                            type == Consts.FileType.IMAGE ? ".png" :
                                    type == Consts.FileType.TEXT ? ".txt" :
                                            type == Consts.FileType.VIDEO ? ".mp4" : null;
            if (suffix == null) {
                suffix = ".unknown";
            }
        } else { // 有尾缀
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        return String.format("%s/%s%s", userId, snowflake.nextId(), suffix);
    }

    private MkFile upload(Long userId, MultipartFile multipartFile, String base64File, Integer type) {
        String parentRelativePathKey;
        switch (type) {
            case Consts.FileType.AUDIO:
                parentRelativePathKey = Consts.ConfigKey.baseAudioPath;
                break;
            case Consts.FileType.IMAGE:
                parentRelativePathKey = Consts.ConfigKey.baseImagePath;
                break;
            case Consts.FileType.VIDEO:
                parentRelativePathKey = Consts.ConfigKey.baseVideoPath;
                break;
            case Consts.FileType.TEXT:
                parentRelativePathKey = Consts.ConfigKey.baseTextPath;
                break;
            default:
                throw new RequestActionException("没有这个类型");
        }
        String parentRelativePath = mkCommonService.valueByKey(parentRelativePathKey);
        String filename = getFileName(multipartFile, type, userId);

        File file = new File(mkCommonService.valueByKey(Consts.ConfigKey.basePath) + parentRelativePath, filename);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new RequestActionException("文件父级目录创建失败");
            }
        }
        try {
            if (multipartFile != null) {
                multipartFile.transferTo(file);
            } else {
                Base64.decodeToFile(base64File, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RequestActionException("文件创建失败 - " + e.getMessage());
        }
        val mkFile = new MkFile();
        mkFile.setPath(parentRelativePath + "/" + filename);
        mkFile.setFileType(type);
        mkFile.setState(Consts.FileState.ENABLE);
        mkFile.setUserId(userId);
        add(mkFile);
        return mkFile;
    }

    @Override
    public MkFile upload(MultipartFile multipartFile, Integer type) {
        val userId = userUtil.currentUserIdNotNull();
        return upload(userId, multipartFile, null, type);
    }

    @Override
    public MkFile uploadBase64(Long userId, String base64Image, Integer type) {
        return upload(userId, null, base64Image, type);
    }

    /**
     * 包含图片id的字段转图片全路径
     */
    private final Set<String> imgKeys = Sets.newHashSet("image", "avatar", "thumb", "img");

    @Override
    public boolean isEnableUrlAutoFull(String key) {
        return imgKeys.contains(key);
    }

    @Override
    public String getUrlBy(Object obj) {
        if (!(obj instanceof Long)) {
            return null;
        }
        Long fileId = (Long) obj;
        val path = baseMapper.getPathById(fileId);
        if (path == null) {
            return null;
        }
        return mkCommonService.valueByKey(Consts.ConfigKey.baseFileUrl) + path;
    }
}
