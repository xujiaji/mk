package com.github.xujiaji.mk.common.payload;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 分页请求参数
 * @author jiajixu
 * @date 2020/10/22 17:14
 */
@Data
public class PageCondition {
    /**
     * 当前页码
     */
    @NotNull(message = "请传入页码")
    private Long page;

    /**
     * 每页条数
     */
    @NotNull(message = "请传入一页多少条数据")
    @Max(value = 100, message = "超出每页最大条数")
    private Long size;
}
