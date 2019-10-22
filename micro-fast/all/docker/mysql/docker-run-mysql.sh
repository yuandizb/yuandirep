#!/usr/bin/env bash
# 修复了数据库中文乱码问题
docker run --name mysql-master -d   -p 3317:3306  -v /opt/mysql-master:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=studyj2e mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci