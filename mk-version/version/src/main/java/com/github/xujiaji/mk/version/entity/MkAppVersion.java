package com.github.xujiaji.mk.version.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * App版本管理
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_app_version")
public class MkAppVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 版本名
     */
    private String versionName;

    /**
     * 版本号
     */
    private Integer versionCode;

    /**
     * 更新内容信息
     */
    private String updateInfo;

    /**
     * 文件大小说明
     */
    private String fileSize;

    /**
     * 是否强制更新
     */
    private Integer versionConstraint;

    /**
     * 更新链接
     */
    private String url;


}
