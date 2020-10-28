package com.github.xujiaji.mk.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xujiaji.mk.common.exception.db.DBDeleteException;
import com.github.xujiaji.mk.common.exception.db.DBInsertException;
import com.github.xujiaji.mk.common.exception.db.DBUpdateException;

/**
 * 服务实现类基础类
 * @author jiajixu
 * @date 2020/10/22 16:58
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseIService<T> {

    /**
     * 检查是否插入数据成功
     */
    public void checkInsertSuccess(int insertResult) {
        if (insertResult == Consts.NEGATIVE) {
            throw new DBInsertException();
        }
    }

    /**
     * 检查是否更新数据成功
     */
    public void checkUpdateSuccess(int updateResult) {
        if (updateResult == Consts.NEGATIVE) {
            throw new DBUpdateException();
        }
    }

    /**
     * 检查是否删除数据成功
     */
    public void checkDeleteSuccess(int deleteResult) {
        if (deleteResult == Consts.NEGATIVE) {
            throw new DBDeleteException();
        }
    }

    @Override
    public void add(T entity) {
        checkInsertSuccess(baseMapper.insert(entity));
    }

    @Override
    public void editById(T entity) {
        checkUpdateSuccess(baseMapper.updateById(entity));
    }

    @Override
    public void deleteById(Long id) {
        checkDeleteSuccess(baseMapper.deleteById(id));
    }
}
