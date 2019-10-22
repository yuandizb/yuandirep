package com.micro.fast.common.datasource.dynamic.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 用于判断数据库从节点是否存在，进行条件化装配bean
 * @author lishouyu
 * @version 1.0
 * @since 1.0
 */
public class MasterDataSourceCondition implements Condition {
  private static final Logger logger = LoggerFactory.getLogger(MasterDataSourceCondition.class);
  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
    Environment environment = conditionContext.getEnvironment();
    //判断环境中是否有从数据库的配置
    logger.info("是否使用默认主从数据库配置: {}",environment.containsProperty("spring.datasource.druid.master.username"));
    return environment.containsProperty("spring.datasource.druid.master.username");
  }
}
