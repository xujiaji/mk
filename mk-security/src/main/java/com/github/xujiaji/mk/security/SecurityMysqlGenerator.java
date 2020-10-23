package com.github.xujiaji.mk.security;

import com.github.xujiaji.mk.common.MysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class SecurityMysqlGenerator extends MysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "security";
    }


    public static void main(String[] args) {
        new SecurityMysqlGenerator().runs(
                "sec_permission",
                "sec_role",
                "sec_role_permission",
                "sec_user",
                "sec_user_role"
        );
    }
}
