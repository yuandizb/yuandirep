#!/usr/bin/env bash

# redis安装　3.0.6
cd /opt/tools
wget http://download.redis.io/releases/redis-3.0.6.tar.gz
tar -zxvf redis-3.0.6.tar.gz
cd redis-3.0.6
make
make install
