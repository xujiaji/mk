package com.github.xujiaji.mk.auth;

import com.github.xujiaji.mk.common.MysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class AuthMysqlGenerator extends MysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "auth";
    }


    public static void main(String[] args) {
        new AuthMysqlGenerator().runs(
                "mk_auth_user",
                "mk_auth_user_log"
        );
    }
}
