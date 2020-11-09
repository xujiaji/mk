package com.github.xujiaji.mk.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jiajixu
 * @date 2020/10/23 15:35
 */
@Data
public class BaseWithIdEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

}
