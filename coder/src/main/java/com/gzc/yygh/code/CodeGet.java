package com.gzc.yygh.code;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/27  10:19  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 使用mp的代码生成器来生成一系列的service层,controller层,mapper层等
 */
public class CodeGet {

    public static void main(String[] args) {
        /*
        mp代码生成器步骤:
        1,导入依赖
        2,编写代码生成器的类
        3,在该类的方法中修改要进行生成的一系列信息:生成路径,参照表格,数据库配置,生成包名等
        4,执行main方法
         */

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOutputDir("D:\\SoftwareData\\Java\\Project\\yygh_parent\\service\\service-user"+"/src/main/java");

        gc.setServiceName("%sService");	//去掉Service接口的首字母I
        gc.setAuthor("gzc");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/yygh_user?serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("GUAN0310");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("user"); //模块名
        pc.setParent("com.gzc.yygh");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("patient");//表名

        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}
