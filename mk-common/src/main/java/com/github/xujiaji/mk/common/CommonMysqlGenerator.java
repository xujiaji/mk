package com.github.xujiaji.mk.common;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class CommonMysqlGenerator extends MysqlGenerator {
    @Override
    protected String parentPackage() {
        return "com.github.xujiaji.mk";
    }

    @Override
    protected String dbName() {
        return "mk";
    }

    @Override
    protected String dbUsername() {
        return "root";
    }

    @Override
    protected String dbPassword() {
        return "sKk2sxAwtfx";
    }

    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "mk-common";
    }


    public static void main(String[] args) {
        new CommonMysqlGenerator().runs(
                "mk_common"
        );
    }
}
