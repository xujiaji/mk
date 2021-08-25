package com.github.xujiaji.mk.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.security.dto.MkSecUserDTO;
import com.github.xujiaji.mk.security.entity.MkSecUser;

/**
 * <p>
 * 安全用户表 Mapper 接口
 * </p>
 *
 * @author xujiaji
 * @since 2021-08-08
 */
public interface MkSecUserMapper extends BaseMapper<MkSecUser> {

    IPage<MkSecUserDTO> selectUserPage(Page<MkSecUserDTO> page);
}
