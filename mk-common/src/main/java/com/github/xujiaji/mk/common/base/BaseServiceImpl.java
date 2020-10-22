package com.github.xujiaji.mk.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 服务实现类基础类
 * @author jiajixu
 * @date 2020/10/22 16:58
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseIService<T> {

}
