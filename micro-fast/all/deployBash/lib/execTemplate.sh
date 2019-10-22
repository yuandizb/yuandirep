#!/usr/bin/env bash

source lib/isOK.sh
source lib/isRunning.sh
source lib/isHaveAndBinPathTrue.sh
source config/devBinPath.sh

# 某个某个程序要执行开始操作的模板
# $1 进程名称　$2 自定义要执行的操作名　$3 bin路径 　$４ 启动参数　$５ 配置文件路径
function startTemplate(){
    isBinPathTrue $3 $1
    if [ $? -eq 0 ]; then
        isRunning $1
        if [ $? -ne 0 ];then
            $3 $4 $5
            isOk $1 $2 $?
        fi
    fi
}

# 某个某个进程要执行关闭操作的模板
# $1 进程名称　$2 自定义要执行的操作名　$3 bin路径 　$４ 启动参数　$５ 配置文件路径
function stopTemplate(){
    isBinPathTrue $3 $1
    if [ $? -eq 0 ]; then
        isRunning $1
        if [ $? -eq 0 ];then
            $3 $4 $5
            isOk $1 $2 $?
        fi
    fi
}
