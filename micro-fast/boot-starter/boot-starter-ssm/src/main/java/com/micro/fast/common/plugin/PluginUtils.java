package com.micro.fast.common.plugin;

/**
 * mybatis插件工具类
 * @author lsy
 */
public class PluginUtils {

    /**
     * 不能被实例化
     */
    private PluginUtils(){

    }

    /**
     * 根据语句id获取方法名
     * @param statementId
     * @return
     */
    public static String getMethodName(String statementId){
        int i = statementId.lastIndexOf(".");
        return statementId.substring(i + 1);
    }

    /**
     * 根据语句id获取全类名
     * @param statementId
     * @return
     */
    public static String getClassName(String statementId){
        int i = statementId.lastIndexOf(".");
        return statementId.substring(0,i);
    }
}
