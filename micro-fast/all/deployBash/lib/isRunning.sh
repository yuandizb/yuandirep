#!/usr/bin/env bash
# $1 要检测的进程的名字
function isRunning(){
    ps -fe|grep $1|grep -v grep|grep -v $0
    if [ $? -eq 0 ]
    then
        printf "%s is Running\n" "$1"
        return 0
    else
        printf "%s is not Running\n" "$1"
        return 1
    fi
}