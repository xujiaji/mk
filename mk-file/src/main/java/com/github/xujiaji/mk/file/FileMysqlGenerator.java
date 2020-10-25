package com.github.xujiaji.mk.file;

import com.github.xujiaji.mk.common.MysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class FileMysqlGenerator extends MysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "file";
    }


    public static void main(String[] args) {
        new FileMysqlGenerator().runs(
                "mk_file"
        );
    }
}
