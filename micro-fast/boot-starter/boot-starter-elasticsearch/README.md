# 欢迎查看micro-fast-boot-starter-elasticsearch项目说明
## 项目介绍
    `micro-fast-boot-starter-elasticsearch`项目用于快速开启spring集成elasticsearch的项目,将本项目使用maven发布到本地仓库,在其他项
    目中引入该依赖进行相关配置即可使用java操作elasticsearch
## application.yml文件中的相关配置
- `elasticsearch.hosts`es节点的ip:port数组(必要参数)
- `elasticsearch.settings`构建es客户端的Settings实例的key:value数组 例如设置集群名称`cluster.name:lsy`(必要参数)
  