package com.github.xujiaji.mk.user;

import com.github.xujiaji.mk.common.MysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class UserMysqlGenerator extends MysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "user";
    }


    public static void main(String[] args) {
        new UserMysqlGenerator().runs(
                "user"
        );
    }
}
