package com.micro.fast.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 对服务访问者的信息进行记录
 * @author lsy
 */
@Component
@Slf4j
public class RecordInformationFilter extends ZuulFilter {

  public static final String SERVICE_RANK_KEY = "gateway:ServiceRank";

  @Autowired
  private RedisTemplate<String,String> redisTemplate;

  /**
   * 过滤器类型
   * @return
   */
  @Override
  public String filterType() {
    return "pre";
  }

  /**
   * 过滤器排序
   * @return
   */
  @Override
  public int filterOrder() {
    return 0;
  }

  /**
   * 是否启用该过滤器
   * @return
   */
  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext currentContext = RequestContext.getCurrentContext();
    HttpServletRequest request = currentContext.getRequest();
    String requestURI = request.getRequestURI();
    String[] split = requestURI.split("/");
    // 统计网管访问服务数量
    redisTemplate.opsForZSet().incrementScore(SERVICE_RANK_KEY,split[1],1);
    return null;
  }
}
