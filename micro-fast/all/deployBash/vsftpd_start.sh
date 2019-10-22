#!/usr/bin/env bash

source lib/isOK.sh
systemctl start vsftpd
isOk vsftpd start $?

