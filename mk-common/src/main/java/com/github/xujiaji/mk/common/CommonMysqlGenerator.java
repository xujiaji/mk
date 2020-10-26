package com.github.xujiaji.mk.common;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class CommonMysqlGenerator extends MysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "common";
    }


    public static void main(String[] args) {
        new CommonMysqlGenerator().runs(
                "mk_common"
        );
    }
}
