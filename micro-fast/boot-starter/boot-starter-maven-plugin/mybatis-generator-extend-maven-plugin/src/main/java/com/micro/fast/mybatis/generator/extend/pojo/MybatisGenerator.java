package com.micro.fast.mybatis.generator.extend.pojo;

import java.util.Map;

/**
 * mybatisGenerator的配置参数对象
 */
public class MybatisGenerator {

  private String pojoPackage;

  private String daoPackage;

  private String tablesPrefix;

  private Map<String,String> tablesNameAndDoMainObjectsName;

  private String servicePackage;

  private String controllerPackage;

  private String serviceImplPackage;

  private String primaryKeyType;

  private String isOverWrite;

    public String getPojoPackage() {
        return pojoPackage;
    }

    public void setPojoPackage(String pojoPackage) {
        this.pojoPackage = pojoPackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getTablesPrefix() {
        return tablesPrefix;
    }

    public void setTablesPrefix(String tablesPrefix) {
        this.tablesPrefix = tablesPrefix;
    }

    public Map<String, String> getTablesNameAndDoMainObjectsName() {
        return tablesNameAndDoMainObjectsName;
    }

    public void setTablesNameAndDoMainObjectsName(Map<String, String> tablesNameAndDoMainObjectsName) {
        this.tablesNameAndDoMainObjectsName = tablesNameAndDoMainObjectsName;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        this.primaryKeyType = primaryKeyType;
    }

    public String getIsOverWrite() {
        return isOverWrite;
    }

    public void setIsOverWrite(String isOverWrite) {
        this.isOverWrite = isOverWrite;
    }
}
