#!/usr/bin/env bash
# 根据输入的对程序的操作以及操作的返回结果来进行提示
# $1 操作的程序名称　$2 对程序进行的操作　$3 操作的返回结果值
function isOk(){
    if [ $3 -eq 0 ];then
     printf "%s %s successful\n" "$1　$2"
     return 0
    else
     printf "%s %s fail\n" "$1　$2"
     return 1
    fi
}