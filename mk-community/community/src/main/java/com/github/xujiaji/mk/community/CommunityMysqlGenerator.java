package com.github.xujiaji.mk.community;


import com.github.xujiaji.mk.common.CommonMysqlGenerator;

/**
 * @author jiajixu
 * @date 2020/10/23 15:47
 */
public class CommunityMysqlGenerator extends CommonMysqlGenerator {
    @Override
    protected String tablePrefix() {
//        return "sec_";
        return "";
    }

    @Override
    protected String moduleName() {
        return "community";
    }

    @Override
    protected String subPackageName() {
        return "community";
    }

    public static void main(String[] args) {
        new CommunityMysqlGenerator().runs(
//                "mk_community_article",
//                "mk_community_article_category",
//                "mk_community_article_collect",
//                "mk_community_article_file",
//                "mk_community_article_topic",
//                "mk_community_comment",
//                "mk_community_follow",
//                "mk_community_praise",
                "mk_community_notice"
        );
    }
}
