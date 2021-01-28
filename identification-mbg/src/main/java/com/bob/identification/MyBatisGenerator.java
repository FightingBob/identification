package com.bob.identification;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis代码生成器
 * Created by LittleBob on 2020/12/17/017.
 */
public class MyBatisGenerator {
    private static void generator() {
        // 代码生成器
        //AutoGenerator mpg = new AutoGenerator();

        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //获取系统当前目录
        String projectPath = System.getProperty("user.dir");
        //代码生成到这个目录下
        gc.setOutputDir(projectPath + "\\identification-mbg" + "/src/main/java");
        gc.setAuthor("LittleBob"); //代码上注释的作者
        gc.setOpen(false); //生成后是否打开资源管理器

        // 覆盖写
        gc.setFileOverride(true); //是否覆盖原来生成的
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/identification?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("wsd123.*");
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                // tinyint转换成int
                if (fieldType.toLowerCase().contains("tinyint")) {
                    return DbColumnType.INTEGER;
                }
                return (DbColumnType) super.processTypeConvert(config, fieldType);
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.bob.identification");
        pc.setModuleName("identification");
        pc.setEntity("po");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "\\identification-mbg" + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        // 只生成mapper po xml
        mpg.setTemplate(
                new TemplateConfig().disable(
                        TemplateType.CONTROLLER,
                        TemplateType.SERVICE,
                        TemplateType.XML
                )
        );

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); //下划线转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); //字段名为下划线
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true); // 持久对象自动添加lombok注解
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        // 默认生成所有表 当注释掉setInclude()方法时
        strategy.setInclude("old_code"); //要生成的表名，想要生成哪个表的代码就填表名，可传多个参数，","隔开
        //strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    public static void main(String[] args) {
        generator();
    }
}
