package com.github.xujiaji.mk.common.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import lombok.val;

/**
 * Controller 基类
 * @author jiajixu
 * @date 2020/10/22 16:57
 */
public class BaseController {

    /**
     * 将请求的page信息转换成mybatis-plus的page模型
     */
    protected <T> Page<T> mapPage(PageCondition pc) {
        return new Page<>(pc.getPage(), pc.getSize());
    }

    /**
     * 将mybatis-plus的page数据转换成response数据模型
     */
    private <T> PageVO<T> responsePage(Page<T> page) {
        val pv = new PageVO<T>();
        pv.setList(page.getRecords());
        pv.setTotal(page.getTotal());
        pv.setSize(page.getSize());
        pv.setPages(page.getPages());
        return pv;
    }

    /**
     * 包装返回成功的处理
     */
    protected <T> ApiResponse<T> success(T t) {
        return ApiResponse.ofSuccess(t);
    }

    /**
     * 成功消息
     */
    protected ApiResponse<?> successMessage(String msg) {
        return ApiResponse.ofMessage(msg);
    }

    /**
     * 添加成功
     */
    protected ApiResponse<?> successAdd() {
        return ApiResponse.ofMessage("添加成功");
    }

    /**
     * 更新成功
     */
    protected ApiResponse<?> successUpdate() {
        return ApiResponse.ofMessage("更新成功");
    }

    /**
     * 删除成功
     */
    protected ApiResponse<?> successDelete() {
        return ApiResponse.ofMessage("删除成功");
    }

    /**
     * 包装分页返回成功的处理
     */
    protected <T> ApiResponse<PageVO<T>> successPage(Page<T> p) {
        return ApiResponse.ofSuccess(responsePage(p));
    }

    /**
     * request 转 实体
     */
    protected <T> T request2Entity(Object request, Class<T> tClass) {
        return BeanUtil.copyProperties(request, tClass);
    }
}
