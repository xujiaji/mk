package com.github.xujiaji.mk.user.mapper;

import com.github.xujiaji.mk.user.entity.MkUserIdNumber;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
public interface MkUserIdNumberMapper extends BaseMapper<MkUserIdNumber> {

     /**
      * 获取最新创建的ID
      */
     MkUserIdNumber selectLastIdNumber();

    /**
     * 获取所有的ID
     */
     List<Long> selectAllIdNumber();
}
