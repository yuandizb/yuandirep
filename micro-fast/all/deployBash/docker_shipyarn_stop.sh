#!/usr/bin/env bash
# 关闭shipyarnf服务
echo "准备关闭"
curl -sSL https://shipyard-project.com/deploy | ACTION=remove bash -s
echo "关闭成功"

