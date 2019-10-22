package com.micro.fast.fastdfs.config;

import com.micro.fast.fastdfs.config.param.FastDFSClientParamConfig;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * fastdfs java client bean configuration class
 * @author lishouyu
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class FastDFSClientConfig {
  /**
   * 记录日志
   */
  private static final Logger logger = LoggerFactory.getLogger(FastDFSClientConfig.class);

  @Autowired
  private FastDFSClientParamConfig fastDFSClientParamConfig;
  /**
   * init trackerServer bean
   * @return trackerServer trackerServer　object
   */
  @Bean
  public TrackerServer trackerServer() {
    TrackerServer trackerServer = null;
    //填充properties参数
    Properties properties = new Properties();
    properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, fastDFSClientParamConfig.getTrackerServers());
    properties.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, fastDFSClientParamConfig.getHttpSecretKey());
    properties.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, fastDFSClientParamConfig.getHttpAntiStealToken());
    properties.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, fastDFSClientParamConfig.getHttpTrackerHttpPort());
    properties.put(ClientGlobal.PROP_KEY_CHARSET, fastDFSClientParamConfig.getCharset());
    properties.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, fastDFSClientParamConfig.getConnectTimeoutInSeconds());
    properties.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, fastDFSClientParamConfig.getNetworkTimeoutInSeconds());
    //对fatsdfs的配置信息进行日志输出
    if (!properties.isEmpty()) {
      Set<Map.Entry<Object, Object>> entries = properties.entrySet();
      Iterator<Map.Entry<Object, Object>> iterator = entries.iterator();
      while (iterator.hasNext()) {
        Map.Entry<Object, Object> keyVlaue = iterator.next();
        String key = (String) keyVlaue.getKey();
        String value = (String) keyVlaue.getValue();
        if (logger.isInfoEnabled()) {
          logger.info("fastdfs ClientGlobal properties {} : {} ", key, value);
        }
      }
    }
    try {
      //使用properties初始化全局配置
      ClientGlobal.initByProperties(properties);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (MyException e) {
      if (logger.isErrorEnabled()) {
        logger.error("fastdfs java client init error", e);
      }
    }
    //获取tracker客户端对象
    TrackerClient trackerClient = new TrackerClient();
    try {
      //获取tracker服务端对象
      trackerServer = trackerClient.getConnection();
    } catch (IOException e) {
      if (logger.isErrorEnabled()) {
        logger.error("fastdfs java client TrackerServer init error", e);
      }
    }
    return trackerServer;
  }

  /**
   * init storageClient bean
   * @return storageClient storageClient object
   */
  @Bean
  public StorageClient storageClient(){
    StorageServer storageServer = null;
    StorageClient storageClient = new StorageClient(trackerServer(), storageServer);
    return  storageClient;
  }
}
