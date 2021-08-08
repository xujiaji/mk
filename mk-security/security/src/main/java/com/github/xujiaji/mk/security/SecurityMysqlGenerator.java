package com.github.xujiaji.mk.security;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class SecurityMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "mk-security/security";
    }


    public static void main(String[] args) {
        new SecurityMysqlGenerator().runs(
//                "mk_sec_permission",
//                "mk_sec_role",
//                "mk_sec_role_permission",
                "mk_sec_user"
//                "mk_sec_user_role"
        );
    }

    @Override
    protected String subPackageName() {
        return "security";
    }

    @Override
    protected boolean isFileOverride() {
        return true;
    }
}
