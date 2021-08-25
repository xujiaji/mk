package com.github.xujiaji.mk.common;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class CommonMysqlGenerator extends MysqlGenerator {


    @Override
    protected String authorName() {
        return "xujiaji";
    }

    @Override
    protected String parentPackage() {
        return "com.github.xujiaji.mk";
    }

    @Override
    protected DataSourceConfig getDataSourceConfig() {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/mk?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("mk");
        dsc.setPassword("N3fsa4w72prMiSMD");
        return dsc;
    }

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
