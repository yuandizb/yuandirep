package com.micro.fast.ssm.sharding.config;

import com.github.pagehelper.PageHelper;
import com.micro.fast.ssm.sharding.config.param.MybatisParamConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * mybatis的相关配置
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableTransactionManagement
public class SqlSessionFactoryConfig implements TransactionManagementConfigurer{
  private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactoryConfig.class);

  /**
   * 数据源对象
   */
  @Autowired
  private DataSource dataSource;

  /**
   * mybatis参数配置对象
   */
  @Autowired
  private MybatisParamConfig mybatisParamConfig;

  /**
   * mybatis连接工厂bean
   * @return sqlSessionFactory
   */
  @Bean(name = "sqlSessionFactory")
  @Primary
  public SqlSessionFactory sqlSessionFactory() {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    //注入数据源
    sqlSessionFactoryBean.setDataSource(dataSource);
    //设置实体类所在的位置
    sqlSessionFactoryBean.setTypeAliasesPackage(mybatisParamConfig.getTypeAliasesPackage());
    if (logger.isInfoEnabled()) {
      logger.info("micro-fast common mybatisConfig: typeAliasesPackage:{}", mybatisParamConfig.getTypeAliasesPackage());
    }
    //sqlSessionFactoryBean.setTypeAliasesPackage("com.micro.fast.upms.pojo");
    //设置mapper映射文件的位置
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    try {
      sqlSessionFactoryBean.setMapperLocations(
          resolver.getResources(mybatisParamConfig.getMappers()));
          //resolver.getResources("classpath:/mappers/*.xml"));
        if (logger.isInfoEnabled()) {
            logger.info("micro-fast common mybatisConfig: mappers:{}", mybatisParamConfig.getMappers());
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
    //设置分页插件
    PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    ArrayList<String> pageHelperProperties = mybatisParamConfig.getPageHelperProperties();
    if (pageHelperProperties != null && !pageHelperProperties.isEmpty()) {
      for (String param :
          pageHelperProperties) {
        String[] keyValue = param.split(":");
        String key = keyValue[0];
        String value = keyValue[1];
        properties.setProperty(key, value);
        if (logger.isInfoEnabled()) {
          logger.info("micro-fast common 使用mybatisPageHelperPropertyConfig自定义配置: {}:{}", key, value);
        }
      }
    } else {
      properties.setProperty("dialect", "mysql");
      if (logger.isInfoEnabled()) {
        logger.info("micro-fast common 使用mybatisPageHelperPropertyConfig默认配置: {}:{}", "dialect", "mysql");
      }
    }
    pageHelper.setProperties(properties);
    sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
    //设置详细的配置
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    //开启下划线转驼峰命名
    configuration.setMapUnderscoreToCamelCase(true);
    //开启列别名
    configuration.setUseColumnLabel(true);
    //使用自动的主键生成
    configuration.setUseGeneratedKeys(true);
    //将详细配置放入到sqlSessionFactoryBean中
    sqlSessionFactoryBean.setConfiguration(configuration);
    SqlSessionFactory sqlSessionFactory = null;
    try {
      sqlSessionFactory = sqlSessionFactoryBean.getObject();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sqlSessionFactory;
  }


  /**
   * 向事务管理器中注入数据源,由spring来进行事务管理
   */
  @Override
  @Bean("transactionManager")
  @Primary
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }
}
