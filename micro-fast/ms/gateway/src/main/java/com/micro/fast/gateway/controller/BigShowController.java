package com.micro.fast.gateway.controller;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.filter.RecordInformationFilter;
import com.micro.fast.gateway.pojo.vo.ServiceRank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 展示数据
 * @author lsy
 */
@RestController
@RequestMapping("/bigShow")
@Api(description = "服务调用数据展示")
public class BigShowController {

  @Autowired
  private RedisTemplate<String,String> redisTemplate;

  @GetMapping("/serviceRank")
  @ApiOperation("获取服务调用排行")
  public ServerResponse ServiceRank(){
    List<ServiceRank> serviceRanks = new ArrayList<>();
    Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(RecordInformationFilter.SERVICE_RANK_KEY, 0, 10);
    typedTuples.forEach(item -> {
      ServiceRank serviceRank = new ServiceRank(item.getValue(),item.getScore());
      serviceRanks.add(serviceRank);
    });
    serviceRanks.sort((a,b) -> a.getNumber().equals(b.getNumber()) ? 0 : (a.getNumber() > b.getNumber() ? -1 : 1));
    return ServerResponse.successMsgData("获取服务调用排行成功",serviceRanks);
  }
}
