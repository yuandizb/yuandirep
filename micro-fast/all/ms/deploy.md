## ms项目开发文档

### 开发环境的搭建

#### 修改host文件,添加如下记录
```bash
127.0.0.1 www.config.com
127.0.0.1 www.register1.com
127.0.0.1 www.register2.com
127.0.0.1 www.upms.com
127.0.0.1 www.ucenter.com
127.0.0.1 www.gateway.com
127.0.0.1 www.turbine.com
127.0.0.1 www.oauth.com
127.0.0.1 www.zipkin.com
127.0.0.1 fthipw.natappfree.cc
```
#### 安装jdk1.8+
 [官网](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
#### 安装maven
 省略
#### 安装mysql5.7+
默认端口改为3317，数据库账号为root,数据库密码为studyj2e。
如果不想修改本地mysql配置，可以fork [config-server](https://gitee.com/kklt1996/config-server)到自己仓库，对config-server
中的数据库连接配置修改。然后ms项目中需要修改config-server模块的配置仓库的指向为自己的仓库url
#### 安装flume1.8.0
[官网](http://flume.apache.org)
- 进入安装目录，找到conf/flume.conf文件，将其内容替换为如下：
```bash
a1.sources = r1
a1.sinks = k1
a1.channels = c1


a1.sources.r1.type = avro
a1.sources.r1.bind = localhost
a1.sources.r1.port = 44444


a1.sinks.k1.type = logger



a1.channels.c1.type = memory
a1.channels.c1.capacity = 10000
a1.channels.c1.transactionCapacity = 10000
a1.channels.c1.byteCapacityBufferPercentage = 20
a1.channels.c1.byteCapacity = 800000

a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
```
- 进入到flume安装目录执行：
```bash
./bin/flume-ng.cmd agent --conf conf --conf-file ./conf/flume.conf --name a1
```
#### 安装ribbitmq
默认配置,安装后一般自动启动
rabbitmq 3.7.2 [官网](http://www.rabbitmq.com/)
#### 安装redis
默认配置，安装后进入安装目录执行：
```bash
./redis-server.exe ./redis.windows.conf
```
#### 安装node.js
nodejs v8.4.0 [官网](https://nodejs.org/en/)
npm替换阿里镜像源
```bash
npm config set registry https://registry.npm.taobao.org
```
### 项目启动
#### clone项目到本地
```bash
# 克隆项目依赖
git clone https://gitee.com/kklt1996/fastdfs-client-java.git
cd fastdfs-client-java
mvn install -Dmaven.test.skip=true
# 克隆后台项目
git clone https://gitee.com/kklt1996/micro-fast.git
cd micro-fast 
mvn install -Dmaven.test.skip=true -Dmaven.plugin.skip=true
将项目下./all/docker/maven/shardbatis-2.0.0B.jar这个jar拷贝到本地maven仓库对应位置
# 克隆前端项目
git clone https://gitee.com/kklt1996/ms-admin-ui.git
cd ms-admin-ui
npm install
```
#### 使用intellij idea打开项目
分别打开micro-fast,ms-admin-ui
#### 运行ms项目
项目启动顺序：
在最新的版本中为了方便开发部署，弱化了启动顺序依赖。可以不考虑启动顺序直接全部启动即可。并且各个模块的数据表
和数据会在程序第一次启动的时候被自动初始化，不用手动导入sql.所有易用性的改善都是为了开发者更方便的参与进来。
如果启动时报其他错误，请检查redis,flume,rabbitmq是否已经启动。
#### 运行ms-admin-ui
在intellij idea中打开命令行窗口
```bash
npm run dev
```
#### 查看项目
根据各个模块的的README.md文件进行访问。项目目前正在逐步成长，处于起步阶段，功能并不完善，前后台并未连通，请多多包涵。也期待各位
提出宝贵的意见，欢迎新的小伙伴参与进来，一起成长。如果觉得不错记得动动小手点个star.





