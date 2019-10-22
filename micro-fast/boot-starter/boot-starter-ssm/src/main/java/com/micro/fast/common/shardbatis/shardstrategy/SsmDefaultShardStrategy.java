package com.micro.fast.common.shardbatis.shardstrategy;

import com.google.code.shardbatis.strategy.ShardStrategy;
import com.micro.fast.common.config.SqlSessionFactoryConfig;
import com.micro.fast.common.shardbatis.IdShardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 提供默认的分表策略
 * @author lsy
 */
@Slf4j
@Configuration
@AutoConfigureBefore(SqlSessionFactoryConfig.class)
@ConditionalOnProperty(prefix = "shardbatis",name = "configPath",matchIfMissing = false)
public class SsmDefaultShardStrategy implements ShardStrategy{


  private static Integer tableCount;

  /**
   * 分表策略的实现
   * @param baseTableName　逻辑表名
   * @param params mybatis语句的参数
   * @param statementId　语句的id，一般是类名.方法名
   * @return　
   */
  @Override
  public String getTargetTableName(String baseTableName, Object params, String statementId) {
    StringBuilder shardTableName = new StringBuilder(baseTableName);
    shardTableName.append("_");
    try {
      Object shardIdValue = IdShardUtils.getShardIdValue(params, statementId);
      if (shardIdValue == null){
        shardTableName.append("000");
      }else {
        String s = shardIdValue.toString();
        Integer integer = Integer.valueOf(s);
        Integer index = integer % tableCount;
        if (index<10){
          shardTableName.append("00"+index);
        } else if (index<100){
          shardTableName.append("0"+index);
        } else {
          shardTableName.append(index);
        }
      }
    } catch (Exception e) {
      log.info("分表失败:{}",e);
      throw new RuntimeException("分表失败");
    }

    return shardTableName.toString();
  }

  /**
   * springBoot不允许直接给静态变量赋值,但是可以通过非静态方法给静态变量赋值
   * @param tableCount
   */
  @Value("${shardbatis.tableCount}")
  public void setTableCount(Integer tableCount){
    SsmDefaultShardStrategy.tableCount = tableCount;
  }
}
