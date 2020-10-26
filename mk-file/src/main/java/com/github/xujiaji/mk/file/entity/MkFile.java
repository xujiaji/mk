package com.github.xujiaji.mk.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件管理
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_file")
public class MkFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    private Long userId;

    /**
     * 路径
     */
    private String path;

    /**
     * 文件类型：1.图片；2.视频；3.音频；4.文本
     */
    private Integer fileType;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
