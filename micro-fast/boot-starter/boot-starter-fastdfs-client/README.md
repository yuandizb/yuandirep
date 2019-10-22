# 欢迎查看boot-starter-fastdfs项目说明
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
## 项目介绍
    `boot-starter-fastdfs`项目用于快速开启spring集成fastdfs的项目,将本项目使用maven发布到本地仓库,在其他项
    目中引入该依赖进行相关配置即可使用java操作fastdfs进行文件的上传
## application.yml文件中的相关配置
- `fastdfs.connectTimeoutInSeconds`　5 链接超时时间
- `fastdfs.networkTimeoutInSeconds` 30　网络超时时间
- `fastdfs.charset` UTF-8　编码
- `fastdfs.httpAntiStealToken` false　
- `fastdfs.httpSecretKey` FastDFS1234567890
- `fastdfs.httpTrackerHttpPort` 8888　
- `fastdfs.trackerServers` 192.168.0.107:22122,192.163.0.208:22122　（必要参数）

