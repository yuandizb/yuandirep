#!/usr/bin/env bash

# 开启shipyarnf服务
echo "准备开始"
curl -sSL https://shipyard-project.com/deploy | PORT=9000 bash -s
echo "开启成功"