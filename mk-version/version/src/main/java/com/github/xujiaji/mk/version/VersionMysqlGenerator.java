package com.github.xujiaji.mk.version;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class VersionMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "version";
    }

    @Override
    protected String subPackageName() {
        return "version";
    }

    public static void main(String[] args) {
        new VersionMysqlGenerator().runs(
                "mk_app_version"
        );
    }
}
