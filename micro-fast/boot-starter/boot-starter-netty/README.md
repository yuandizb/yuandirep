# boot-starter-netty项目说明
## 几种io的对比
io类型 | 通讯模型 | 描述 
---|------------|----
bio | 阻塞同步通讯 | 使用一个线程处理一个请求
伪nio |阻塞同步通讯 | 使用线程池，使用n个线程处理m个请求
nio | 非阻塞同步通讯 | 使用一个线程处理多个请求，难度大，不方便调试
aio | 非阻塞异步通讯 | 难度大，不方便调试
## jdk自带的nio和netty的对比
- jdk自带的nio的api繁杂、工作量和工作难度大、入门门槛高需要精通多线程、jdk的nio存在bug会出现selector空轮训导致cpu跑满
- netty商用成功。可扩展性强、入门门槛低、成熟稳定、性能强
## websocket简单介绍
### websocket建立连接
客户端发起握手请求，服务端响应请求，建立连接。
### websocket的生命周期
#### 打开事件
#### 消息事件
#### 错误事件  
- 能感知websocket所有方法的错误
#### 关闭事件 
- 服务端端关闭底层tcp连接
- 客户端发起TCP close
## 基于netty实现websocket协议
