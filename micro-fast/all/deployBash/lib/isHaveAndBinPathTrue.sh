#!/usr/bin/env bash
# 检测文件是否存在并且是可执行的文件　$1 要检测的路径　$2 检测的程序名称
function isBinPathTrue(){
    if [ -x $1 ]; then
        printf "%s是可执行文件\n" "$1"
        return 0
    else
        printf "%s没有安装或者没有安装在本项目约定的位置\n如果确定安装了%s请修改config/binPath下的对应启动路径\n" "$2$2"
        return 1
    fi
}
