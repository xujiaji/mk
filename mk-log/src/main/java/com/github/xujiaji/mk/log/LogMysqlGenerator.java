package com.github.xujiaji.mk.log;

import com.github.xujiaji.mk.common.MysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class LogMysqlGenerator extends MysqlGenerator {
    @Override
    protected String tablePrefix() {
        return "";
    }

    @Override
    protected String moduleName() {
        return "log";
    }


    public static void main(String[] args) {
        new LogMysqlGenerator().runs(
                "mk_log"
        );
    }
}
