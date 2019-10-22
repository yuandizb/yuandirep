# 高可用注册中心
相同的hostname的注册中心eureka会认为是这些euraka在同一台物理机上，这些eureka会一共存储
一份注册者的信息，而不是每个注册中心存储一份。所以在同一台机器上模拟高可用注册中心的时候
必须修改hosts文件，注册中心使用不同的hostname

#### 增加了注册实例查询的restful接口
默认的base路径是`/restful/eureka`