package com.github.xujiaji.mk.common.exception.db;

import com.github.xujiaji.mk.common.base.BaseException;
import com.github.xujiaji.mk.common.base.Status;

/**
 * 数据库插入异常
 * @author jiajixu
 * @date 2020/10/26 14:33
 */
public class DBUpdateException extends BaseException {
    public DBUpdateException() {
        super(Status.DB_UPDATE_ERROR);
    }
}
