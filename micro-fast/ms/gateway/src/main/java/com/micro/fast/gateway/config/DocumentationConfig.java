package com.micro.fast.gateway.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lsy
 * 聚合各个子系统的文档
 */
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
  @Override
  public List<SwaggerResource> get() {
    List resources = new ArrayList<>();
    resources.add(swaggerResource("服务网关", "/api/api-doc", "2.0"));
    resources.add(swaggerResource("后台权限控制系统", "/upms/api/api-doc", "2.0"));
    resources.add(swaggerResource("用户中心", "/ucenter/api/api-doc", "2.0"));
    resources.add(swaggerResource("注册中心", "/eureka-server/api/api-doc", "2.0"));
    return resources;
  }

  /**
   * 添加要聚合的子系统swagger资源对象
   * @param name 子系统文档的名称
   * @param location 聚合子系统swagger的文档api路径
   * @param version swagger的版本
   * @return
   */
  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
