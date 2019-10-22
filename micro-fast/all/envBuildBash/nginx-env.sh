#!/usr/bin/env bash
# 安装nginx
cd /opt/tools
# 下载依赖
apt-get -y install build-essential
apt-get -y install libtool
apt-get -y install libpcre3 libpcre3-dev
apt-get -y install zlib1g-dev
apt-get  -y install openssl libssl-dev
# 下载nginx
wget http://nginx.org/download/nginx-1.11.3.tar.gz
tar -zxvf nginx-1.11.3.tar.gz
# 安装nginx
cd nginx-1.11.3
# 安装https模块
./configure --with-http_ssl_module
make
make install