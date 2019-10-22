package com.micro.fast.mybatis.generator.extend.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
  private static final String PROPERTIES_PATH = "mybatisStart.properties";
  private static Properties properties = null;

  /**
   * 根据key获取property的value
   * @param key 关键字
   * @return
   */
  public static String getProperty(String key){
    String property = properties.getProperty(key.trim());
    return property.trim();
  }

  /**
   * 根据key获取property的value,如果没有value的话就是默认值
   * @param key　关键字
   * @param defaultValue　默认值
   * @return
   */
  public static String getProperty(String key,String defaultValue){
    String property = properties.getProperty(key.trim(), defaultValue);
    return property.trim();
  }

  public static Properties getProperties() {
    return properties;
  }

  public static void setProperties(Properties properties) {
    PropertiesUtil.properties = properties;
  }
  public void loadProperties(String projectPath){
    //2.将mybatisStart.properties配置文件加载到输入流中
    File file = new File( projectPath+ "/src/main/resources/"+PROPERTIES_PATH);
    if (file.exists()){
      InputStream inputStream = null;
      try {
        inputStream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      //3.将输入流写入到properties对象中
      if (properties==null){
        properties = new Properties();
      }
      try {
        properties.load(inputStream);
      } catch (IOException e) {
        e.printStackTrace();
      }
      }
  }
}
