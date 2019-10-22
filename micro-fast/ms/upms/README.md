# 用户权限管理系统
## 后台用户权限控制
## 生成接口文档
访问`/swagger-ui.html`查看swagger聚合的文档。
## 消息总线刷新配置
修改配置中心的文件配置。访问连接消息总线系统中某一个的`/bus/refresh`接口，将会刷新整个集群的配置。也可以
`/bus/refresh?destination=customers:**`的形式访问，刷新服务名为`customers`，不管ip的配置。
## 服务消费，服务熔断，服务降级，线程隔离
## 服务熔断，降级情况的统计
监控界面访问地址`/hystrix`，监控数据的获取地址`hystrix.stream`
## 服务调用的监控数据生成

`@PostConstruct`注解修饰的方法会在tomcat加载servlet之前运行
`@PreConstruct`注解修饰的方法会在tomcat卸载servlet的时候运行