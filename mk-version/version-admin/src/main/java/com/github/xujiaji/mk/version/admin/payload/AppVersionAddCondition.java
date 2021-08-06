package com.github.xujiaji.mk.version.admin.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/19 11:42
 */
@Data
public class AppVersionAddCondition {

    /**
     * 版本名
     */
    @NotBlank(message = "请输入版本名")
    private String versionName;

    /**
     * 版本号
     */
    @NotNull(message = "请输入版本号")
    private Integer versionCode;

    /**
     * 更新内容信息
     */
    @NotBlank(message = "请输入更新内容信息")
    private String updateInfo;

    /**
     * 文件大小说明
     */
    @NotBlank(message = "请输入文件大小说明")
    private String fileSize;

    /**
     * 是否强制更新
     */
    @NotNull(message = "是否强制更新")
    private Integer versionConstraint;

    /**
     * 更新链接
     */
    @NotBlank(message = "请输入更新链接")
    private String url;
}
