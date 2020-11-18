package com.github.xujiaji.mk.file.vo;

import com.github.xujiaji.mk.file.entity.MkFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/11/18 17:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MkFileVO extends MkFile  {

    /**
     * 链接
     */
    private String url;
}
