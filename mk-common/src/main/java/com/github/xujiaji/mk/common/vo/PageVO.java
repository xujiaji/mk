package com.github.xujiaji.mk.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author jiajixu
 * @date 2020/10/22 17:24
 */
@Data
public class PageVO<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前返回的数据条数
     */
    private Long size;

    /**
     * 当前页面
     */
    private Long page;

    /**
     * 总页数
     */
    private Long pages;

    public static <T> PageVO<T> create(List<T> data, Long page, Long size, Long total) {
        final PageVO<T> pv = new PageVO<>();
        pv.setList(data);
        pv.setTotal(total);
        pv.setPage(page);
        pv.setSize((long) (data == null ? 0 : data.size()));
        pv.setPages((long) ((total + 0.5F) / size));
        return pv;
    }
}
