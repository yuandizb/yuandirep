package com.micro.fast.common.datasource.dynamic;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.micro.fast.common.config.SqlSessionFactoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * 动态数据源配置类
 * @author lishouyu
 * @version 1.0
 * @since 1.0
 */
@Configuration
@AutoConfigureBefore({SqlSessionFactoryConfig.class})
@AutoConfigureAfter({DruidDataSourceAutoConfigure.class})
public class DynamicDataSourceConfig {
  public static final String SLAVE = "slave";
  public static final String MASTER = "master";
  public static final String MASTER_DATA_SOURCE = "masterDataSource";
  public static final String DATA_SOURCE = "dataSource";
  public static final String DYNAMIC_DATA_SOURCE = "dynamicDataSource";

  /**
   * 当masterDataSource存在时，dataSource是从数据源
   */
  @Resource(name = DATA_SOURCE)
  private DataSource dataSource;

  /**
   * 主数据源
   */
  @Autowired(required = false)
  @Qualifier(MASTER_DATA_SOURCE)
  private DataSource masterDataSource;

  /**
   * 收集所有的datasource
   */
  @Autowired
  private Map<String,DataSource> dataSourceMap;

  @Bean(name = DYNAMIC_DATA_SOURCE)
  @Primary
  public DataSource dynamicDataSource(){
    //动态数据源对象
    DynamicDataSource dynamicDataSource = new DynamicDataSource();
    //配置多数据源
    Map<Object,Object> dataSourceMap = new HashMap<>();
    if (masterDataSource==null){
      //设置默认数据源
      dynamicDataSource.setDefaultTargetDataSource(dataSource);
      dataSourceMap.put(MASTER,dataSource);
      this.dataSourceMap.forEach((key,value) -> {
        if ( !DYNAMIC_DATA_SOURCE.equals(key)
             &&!DATA_SOURCE.equals(key)
             && !MASTER.equals(key)){
          dataSourceMap.put(key,value);
        }
      });
    }else{
      //设置默认数据源
      dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
      //根据数据源存在情况调整动态数据源配置
      dataSourceMap.put(MASTER,masterDataSource);
      dataSourceMap.put(SLAVE,dataSource);
      this.dataSourceMap.forEach((key,value) -> {
        if (!DYNAMIC_DATA_SOURCE.equals(key)
            &&!DATA_SOURCE.equals(key)
            &&!MASTER_DATA_SOURCE.equals(key)
            && !MASTER.equals(key)
            && !SLAVE.equals(key)){
          dataSourceMap.put(key,value);
        }
      });
    }
    dynamicDataSource.setTargetDataSources(dataSourceMap);
    return dynamicDataSource;
  }
}
