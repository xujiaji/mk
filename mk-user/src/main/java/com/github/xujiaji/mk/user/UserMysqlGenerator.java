package com.github.xujiaji.mk.user;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class UserMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "mk-user";
    }

    @Override
    protected String subPackageName() {
        return "user";
    }

    public static void main(String[] args) {
        new UserMysqlGenerator().runs(
//                "mk_user",
//                "mk_user_login_log"
//                "mk_user_login_log_admin_view"
//                "mk_user_view"
//                "mk_user_id_number"
//                "mk_sms"
                "mk_app_version"
        );
    }
}
