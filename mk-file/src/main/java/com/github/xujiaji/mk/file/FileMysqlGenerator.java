package com.github.xujiaji.mk.file;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class FileMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "mk-file";
    }


    public static void main(String[] args) {
        new FileMysqlGenerator().runs(
                "mk_file"
        );
    }
}
