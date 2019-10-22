package com.micro.fast.mybatis.generator.extend.service;

import com.micro.fast.mybatis.generator.extend.db.JdbcConnection;
import com.micro.fast.mybatis.generator.extend.pojo.Db;
import com.micro.fast.mybatis.generator.extend.pojo.MybatisGenerator;
import com.micro.fast.mybatis.generator.extend.util.FreemarkerUtil;
import com.micro.fast.mybatis.generator.extend.util.JarUtil;
import com.micro.fast.mybatis.generator.extend.util.PropertiesUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * mybatis-generate-extend-maven-plugin主要逻辑
 */
public class MybatisGeneratorExtend {

    private static Db db;

    private static MybatisGenerator mybatisGenerator;

    //初始化参数
    static {
        if (db==null){
            db = new Db();
        }
        if (mybatisGenerator==null){
            mybatisGenerator = new MybatisGenerator();
        }
        MybatisGeneratorExtend.db.setDriverLocation(PropertiesUtil.getProperty("db.driverLocation"));
        MybatisGeneratorExtend.db.setUrl(PropertiesUtil.getProperty("db.url").replaceAll("&","&amp;"));
        MybatisGeneratorExtend.db.setUsername(PropertiesUtil.getProperty("db.username"));
        MybatisGeneratorExtend.db.setPassword(PropertiesUtil.getProperty("db.password"));
        MybatisGeneratorExtend.db.setDriverClassName(PropertiesUtil.getProperty("db.driverClassName"));
        MybatisGeneratorExtend.mybatisGenerator.setPojoPackage(
                PropertiesUtil.getProperty("mybatisGenerator.pojoPackage"));
        MybatisGeneratorExtend.mybatisGenerator.setDaoPackage(
                PropertiesUtil.getProperty("mybatisGenerator.daoPackage"));
        MybatisGeneratorExtend.mybatisGenerator.setTablesPrefix(
                PropertiesUtil.getProperty("mybatisGenerator.tablesPrefix"));
        MybatisGeneratorExtend.mybatisGenerator.setIsOverWrite("true");
    }

    /**
     * 根据模板引擎生成mybatis-generator需要的文件
     */
    public static void generateMybatisGeneratorNeedFilesByTemplates(String projectPath) {
        Connection jdbcConection = JdbcConnection.getJdbcConection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.创建语句执行sql
            statement = jdbcConection.createStatement();
            String sql = "show tables";
            resultSet = statement.executeQuery(sql);
            //2.处理sql返回的结果,
            Map<String, String> tablesNameAndDoMainObjectsName = new HashMap();
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.startsWith(mybatisGenerator.getTablesPrefix())) {
                    String doMainObjectName = "";
                    String[] strings = tableName.split("_");
                    for (String string : strings) {
                        char c = string.charAt(0);
                        String s = String.valueOf(c);
                        String upperCase = s.toUpperCase();
                        String firstUpper = string.replaceFirst(s, upperCase);
                        doMainObjectName += firstUpper;
                    }
                    tablesNameAndDoMainObjectsName.put(tableName,doMainObjectName);
                }
            }
            MybatisGeneratorExtend.mybatisGenerator.setTablesNameAndDoMainObjectsName(tablesNameAndDoMainObjectsName);
            //创建模板mybatis-generator-extend模板文件
            Map<String,Object> mybatisGeneratorModel = new HashMap<>();
            mybatisGeneratorModel.put("db",MybatisGeneratorExtend.db);
            mybatisGeneratorModel.put("mybatisGenerator",MybatisGeneratorExtend.mybatisGenerator);
            boolean mybatisGeneratorFileSuccess = FreemarkerUtil.createFile("mybatisGenerator.ftl", mybatisGeneratorModel, "mybatisGenerator.properties",projectPath,null,"true");
            File file = new File(JarUtil.getThisJarPathParent() + "/templates" + "/mybatisGenerator.ftl");
            if (file.exists()){
                file.delete();
            }
            boolean generatorConfigFileSuccess = FreemarkerUtil.createFile("generatorConfig.ftl", mybatisGeneratorModel, "generatorConfig.xml",projectPath,null,"true");
            File file1 = new File(JarUtil.getThisJarPathParent() + "/templates" + "/generatorConfig.ftl");
            if (file1.exists()){
                file1.delete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //3.关闭流
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
