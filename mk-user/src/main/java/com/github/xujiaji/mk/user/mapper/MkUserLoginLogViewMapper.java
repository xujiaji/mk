package com.github.xujiaji.mk.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.user.entity.MkUserLoginLogView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-03
 */
public interface MkUserLoginLogViewMapper extends BaseMapper<MkUserLoginLogView> {

    IPage<MkUserLoginLogView> pageSearch(Page<MkUserLoginLogView> page, @Param("userType") Integer userType, @Param("device") String device, @Param("location") String location, @Param("loginIp") String loginIp, @Param("username") String username);
}
