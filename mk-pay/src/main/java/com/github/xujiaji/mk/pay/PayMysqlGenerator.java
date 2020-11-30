package com.github.xujiaji.mk.pay;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class PayMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
        return "";
    }

    @Override
    protected String moduleName() {
        return "mk-pay";
    }

    @Override
    protected String subPackageName() {
        return "pay";
    }

    public static void main(String[] args) {
        new PayMysqlGenerator().runs(
                "mk_pay"
        );
    }
}
