#!/usr/bin/env bash
source lib/isOK.sh
systemctl stop vsftpd
isOk vsftpd stop $?