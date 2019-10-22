package com.micro.fast.elasticsearch.config;

import com.micro.fast.elasticsearch.config.param.ElasticsearchParamConfig;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * elasticsearch客户端bean的配置类
 * @author lishouyu 18332763730@163.com 2017/09/23
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class ElasticsearchConfig  {

  /**
   * 记录es客户端生成日志
   */
  private static final Logger logger = LoggerFactory.getLogger(ElasticsearchParamConfig.class);

  /**
   * elasticsearch的客户端配置参数对象
   * @see com.micro.fast.elasticsearch.config.param.ElasticsearchParamConfig
   */
  @Autowired
  private ElasticsearchParamConfig elasticsearchParamConfig;

  /**
   * 定义es客户端的bean
   * @return client 客户端对象
   * @throws UnknownHostException 未知主机异常
   */
  @Bean("esClient")
  public TransportClient transportClient() throws UnknownHostException {
    //1.根据application.yml中的参数来构建es客户端的配置对象
    Settings.Builder builder = Settings.builder();
    ArrayList<String> settingsParam = elasticsearchParamConfig.getSettings();
    if (settingsParam != null && !settingsParam.isEmpty()) {
        for (String setting:settingsParam) {
          String[] keyValue = setting.split(":");
          String key = keyValue[0];
          String value = keyValue[1];
          builder.put(key, value);
          if (logger.isInfoEnabled()) {
            logger.info("micro-fast elasticsearch 添加elasticsearch的Settings配置: {}:{}", key, value);
          }
        }
    }
    //2.创建配置对象将配置对象放入es客户端对象之中
    Settings settings = builder.build();
    TransportClient client = new PreBuiltTransportClient(settings);
    //3.根据配置文件创建es节点,然后放入到es客户端对象之中
    ArrayList<String> hosts = elasticsearchParamConfig.getHosts();
    if (hosts != null && !hosts.isEmpty()) {
      for (String host :hosts) {
        String[] ipPort = host.split(":");
        String ip = ipPort[0];
        String port = ipPort[1];
        InetSocketTransportAddress node = new InetSocketTransportAddress(
            InetAddress.getByName(ip), Integer.valueOf(port));
        //向客户端中添加节点
        client.addTransportAddress(node);
        if (logger.isInfoEnabled()) {
          logger.info("micro-fast elasticsearch 添加elasticsearch节点: {}:{}", ip, port);
        }
      }
    }
    //返回elasticsearch客户端
    return client;
  }
}
