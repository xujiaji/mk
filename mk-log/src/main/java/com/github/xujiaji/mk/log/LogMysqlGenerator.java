package com.github.xujiaji.mk.log;

import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class LogMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
        return "";
    }

    @Override
    protected String moduleName() {
        return "mk-log";
    }

    @Override
    protected String subPackageName() {
        return "log";
    }

    public static void main(String[] args) {
        new LogMysqlGenerator().runs(
//                "mk_log"
        );
    }
}
