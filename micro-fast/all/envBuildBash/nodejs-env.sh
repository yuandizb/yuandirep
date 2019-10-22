#!/usr/bin/env bash

# 安装nodejs脚本 v8.4.0
cd /opt/tools
wget https://nodejs.org/dist/v8.4.0/node-v8.4.0-linux-x64.tar.xz
tar xvJf node-v8.4.0-linux-x64.tar.xz
echo "export NODE_HOME=/opt/tools/node-v8.4.0-linux-x64" >> /etc/profile
echo "export PATH=\${NODE_HOME}/bin:\$PATH" >> /etc/profile
source /etc/profile
node -v

# 更换淘宝镜像，依赖下载更迅速
npm config set registry https://registry.npm.taobao.org
# 检验是否设置成功
npm info underscore

# 更换npm的版本 3.10.7
npm install -g npm@3.10.7
# 检查npm的版本
npm -v