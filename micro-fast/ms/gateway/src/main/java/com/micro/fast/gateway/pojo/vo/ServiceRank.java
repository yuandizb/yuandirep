package com.micro.fast.gateway.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务调用排行
 * @author lsy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRank {
  private String name;
  private Double number;
}
