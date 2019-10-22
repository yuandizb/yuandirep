# 欢迎查看ms项目说明
## 项目简介
  `ms`使用mybatis,spring系列框架作为基础，致力于打造一套中小企业分布式系统解决方案.借助于springCould微服务框架，对原框架进行整合扩展使用
云架构进行开发
## 模块介绍
### 配置中心 config-server
  配置中心默认启动端口号10000,健康检查端口号10001
### 注册中心 register-center1
  注册中心默认启动端口号10002，健康检查端口号10003．为保证服务的稳定可靠，可以创建多个注册中心，实现服务注
册的备份冗余，实现高可用性
### 注册中心２ register-center2
  注册中心２默认启动端口号10004，健康检查端口号10005．
### 服务网关　gateway
  服务网关默认启动端口号10006,健康检查端口号10007.
### 后台权限管理中心 upms
  用户权限管理中心默认启动端口号10008，健康检查端口号10009．
### 用户中心　ucenter
  用户中心默认启动端口号10010,健康检查端口10011.
### 认证中心　oauth
  认证中心默认启动端口号10012，健康检查端口号10013.
### 请求监控  
  请求监控的默认启动端口是10018
### 服务调用监控
  服务调用监控的默认启动端口是10020
### 后台管理界面 manage-system
  使用vue.js搭建后台管理界面
## 项目相关约束
### 遵循类似于springboot约定大与配置的原则．本项目开发约束如下：
参照boot-starter-ssm项目说明 [README.md](../boot-starter/boot-starter-ssm/README.md)
#### 关于使用docker方式运行项目
  可以使用传统方式运行，可以使用docker容器的方式运行．程序的启动顺序是配置中心，注册中心，其他服务。无论是使何种方式运行，保证除配置中心外，其
他所有服务都能从配置中加载到对应的配置文件，其他所有的服务都能注册到注册中心。所以运行的时候一定要将除配置中心外所有服务的注册空间的配置空间都指
定正确，本项目中本地运行时则是都指定到本机的ip地址．所有应用要显示的指明注册中心的地址．启动顺序是先启动配置中心，然后启动注册中心，其次启动其他服务．
docker容器暴露端口给宿主机主要目的不是为了让宿主机访问，而是为了让其他主机能访问．宿主机器可以直接使用桥接ip访问容器．
## 各模块的详细介绍
- `config-server`项目介绍[README.md](config-server/README.md)
- `gateway`项目介绍[README.md](gateway/README.md)
- `monitor-center`项目介绍[README.md](monitor-center/README.md)
- `oauth`项目介绍[README.md](oauth/README.md)
- `register-center1`项目介绍[README.md](register-center1/README.md)
- `register-center2`项目介绍[README.md](register-center2/README.md)
- `upms`项目介绍[README.md](upms/README.md)
- `ucenter`项目介绍[README.md](ucenter/README.md)