package com.github.xujiaji.mk.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.github.xujiaji.mk.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * mysql 代码生成器
 */
public abstract class MysqlGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public void runs(String ... tableNames) {
        for (String tableName : tableNames) {
            run(tableName);
        }
    }

    public void run(String tableName) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/" + moduleName() + "/src/main/java");
        gc.setAuthor(authorName());
        gc.setOpen(false);
        gc.setFileOverride(isFileOverride());
        mpg.setGlobalConfig(gc);

        mpg.setDataSource(getDataSourceConfig());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackage() + "." + (subPackageName() == null ? moduleName() : subPackageName()));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/" + moduleName() + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setSuperControllerClass("com.github.xujiaji.mk.common.base.BaseController");
        strategy.setSuperServiceClass("com.github.xujiaji.mk.common.base.BaseIService");
        strategy.setSuperServiceImplClass("com.github.xujiaji.mk.common.base.BaseServiceImpl");
        strategy.setRestControllerStyle(true);
        if (tableName == null) {
            strategy.setInclude(scanner("表名"));
        } else {
            strategy.setInclude(tableName);
        }
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(tablePrefix());
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 作者名
     * @return 作者名
     */
    protected abstract String authorName();

    /**
     * 主包名
     * @return 父包名
     */
    protected abstract String parentPackage();

    /**
     * 链接数据库的配置
     * @return 返回配置
     */
    protected abstract DataSourceConfig getDataSourceConfig();

    /**
     * 表前缀
     * @return 前缀
     */
    protected abstract String tablePrefix();

    /**
     * 模块名
     * @return 模块
     */
    protected abstract String moduleName();

    /**
     * 子包名
     */
    protected String subPackageName() {
        return null;
    }

    protected boolean isFileOverride() {
        return false;
    }
}
