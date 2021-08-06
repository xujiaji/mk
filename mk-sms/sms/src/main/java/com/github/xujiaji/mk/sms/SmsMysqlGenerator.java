package com.github.xujiaji.mk.sms;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class SmsMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
        return "";
    }

    @Override
    protected String moduleName() {
        return "sms";
    }

    @Override
    protected String subPackageName() {
        return "sms";
    }

    public static void main(String[] args) {
        new SmsMysqlGenerator().runs(
                "mk_sms"
        );
    }
}
