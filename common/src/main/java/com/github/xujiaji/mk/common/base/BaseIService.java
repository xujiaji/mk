package com.github.xujiaji.mk.common.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务实接口基础类
 * @author jiajixu
 * @date 2020/10/22 16:59
 */
public interface BaseIService<T> extends IService<T> {

    /**
     * 添加实体
     * @param entity 实体
     */
    void add(T entity);

    /**
     * 通过id编辑实体
     * @param entity 实体
     */
    void editById(T entity);

    /**
     * 通过id删除实体
     * @param id 实体id
     */
    void deleteById(Long id);
}