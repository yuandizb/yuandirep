package com.micro.fast.mybatis.generator.extend.db;


import com.micro.fast.mybatis.generator.extend.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcConnection {
  private static Connection connection = null;
  //加载数据库驱动获取连接
   static {
     try {
       Class.forName(PropertiesUtil.getProperty("db.driverClassName"));
       connection = DriverManager.getConnection(
           PropertiesUtil.getProperty("db.url")
           , PropertiesUtil.getProperty("db.username"),
           PropertiesUtil.getProperty("db.password"));
     } catch (ClassNotFoundException e) {
       e.printStackTrace();
     } catch (SQLException e) {
       e.printStackTrace();
     }
   }

  /**
   * 获取数据路链接
   * @return
   */
  public static Connection getJdbcConection(){
      return JdbcConnection.connection;
  }

}
