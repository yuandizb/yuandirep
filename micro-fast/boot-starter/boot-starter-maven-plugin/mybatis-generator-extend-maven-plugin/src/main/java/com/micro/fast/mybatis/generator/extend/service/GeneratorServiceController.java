package com.micro.fast.mybatis.generator.extend.service;

import com.micro.fast.mybatis.generator.extend.db.JdbcConnection;
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
 * 生成service层controller层
 */
public class GeneratorServiceController {

    private static MybatisGenerator mybatisGenerator;

    static {
        // 初始化
        if (mybatisGenerator == null) {
            mybatisGenerator = new MybatisGenerator();
        }
        mybatisGenerator.setPojoPackage(
                PropertiesUtil.getProperty("mybatisGenerator.pojoPackage"));
        mybatisGenerator.setDaoPackage(
                PropertiesUtil.getProperty("mybatisGenerator.daoPackage"));
        mybatisGenerator.setTablesPrefix(
                PropertiesUtil.getProperty("mybatisGenerator.tablesPrefix"));
        mybatisGenerator.setServicePackage(
                PropertiesUtil.getProperty("mybatisGenerator.servicePackage"));
        mybatisGenerator.setServiceImplPackage(
                PropertiesUtil.getProperty("mybatisGenerator.serviceImplPackage"));
        mybatisGenerator.setControllerPackage(
                PropertiesUtil.getProperty("mybatisGenerator.controllerPackage"));
        mybatisGenerator.setPrimaryKeyType(
                PropertiesUtil.getProperty("mybatisGenerator.primaryKeyType"));
        mybatisGenerator.setIsOverWrite(
                PropertiesUtil.getProperty("mybatisGenerator.isOverWrite"));

    }

    /**
     * 生成service层controller层
     *
     * @param projectPath
     */
    public static void generator(String projectPath) {
        Connection jdbcConection = JdbcConnection.getJdbcConection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.创建语句执行sql
            statement = jdbcConection.createStatement();
            String sql = "SHOW TABLES";
            resultSet = statement.executeQuery(sql);
            //2.处理sql返回的结果,
            // 实体类的名字和实体类作为变量的名称
            Map<String, String> pojoNameAndVarPojoName = new HashMap();
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.startsWith(mybatisGenerator.getTablesPrefix())) {
                    String pojoName = "";
                    String varPojoName = "";
                    String[] strings = tableName.split("_");
                    for (int i = 0; i < strings.length; i++) {
                        String string = strings[i];
                        char c = string.charAt(0);
                        String s = String.valueOf(c);
                        String upperCase = s.toUpperCase();
                        String firstUpper = string.replaceFirst(s, upperCase);
                        pojoName += firstUpper;
                        if (i==0){
                            String s1 = s.toLowerCase();
                            String firstLower = string.replaceFirst(s, s1);
                            varPojoName += firstLower;
                        }else {
                            varPojoName += firstUpper;
                        }
                    }
                    pojoNameAndVarPojoName.put(pojoName,varPojoName);
                }
            }
            // 根据模板创建实体类
            Map<String, Object> mybatisGeneratorModel = new HashMap<>();
            mybatisGeneratorModel.put("mybatisGenerator",mybatisGenerator);
            pojoNameAndVarPojoName.forEach((pojoName,varPojoName)->
            {
                mybatisGeneratorModel.put("pojoName",pojoName);
                mybatisGeneratorModel.put("varPojoName",varPojoName);
                // 生成java文件
                FreemarkerUtil.createFile("SsmService.ftl",mybatisGeneratorModel,pojoName+"Service.java",projectPath,mybatisGenerator.getServicePackage(),mybatisGenerator.getIsOverWrite());
                FreemarkerUtil.createFile("SsmServiceImpl.ftl",mybatisGeneratorModel,pojoName+"ServiceImpl.java",projectPath,mybatisGenerator.getServiceImplPackage(),mybatisGenerator.getIsOverWrite());
                FreemarkerUtil.createFile("SsmController.ftl",mybatisGeneratorModel,pojoName+"Controller.java",projectPath,mybatisGenerator.getControllerPackage(),mybatisGenerator.getIsOverWrite());
            });
            // 删除模版文件
            File file1 = new File(JarUtil.getThisJarPathParent() + "/templates" + "/SsmService.ftl");
            if (file1.exists()) {
                file1.delete();
            }
            File file2 = new File(JarUtil.getThisJarPathParent() + "/templates" + "/SsmServiceImpl.ftl");
            if (file2.exists()) {
                file2.delete();
            }
            File file3 = new File(JarUtil.getThisJarPathParent() + "/templates" + "/SsmController.ftl");
            if (file3.exists()) {
                file3.delete();
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
