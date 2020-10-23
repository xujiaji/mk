package com.github.xujiaji.mk.common.handler;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * mybatis-plus id 生成
 * @author jiajixu
 * @date 2020/10/23 17:33
 */
@Component
public class MybatisPlusIdGenerator implements IdentifierGenerator {

    @Resource
    private Snowflake snowflake;

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }
}
