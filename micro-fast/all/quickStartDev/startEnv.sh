#!/usr/bin/env bash
docker start portainer
docker start mysql-master
docker start redis-server
docker start rabbitmq