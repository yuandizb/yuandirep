package com.micro.fast.mybatis.generator.extend.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * mybatisGenerator所需要的数据库连接配置
 */
public class Db {

  private String driverLocation;

  private String url;

  private String username;

  private String password;

  private String driverClassName;

  public String getDriverLocation() {
    return driverLocation;
  }

  public void setDriverLocation(String driverLocation) {
    this.driverLocation = driverLocation;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }
}
