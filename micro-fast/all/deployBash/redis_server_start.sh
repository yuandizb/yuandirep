#!/usr/bin/env bash
source lib/isOK.sh
redis-server
isOk vsftpd start $?