#!/usr/bin/env bash
register1=$1
register2=$2
redisHost=$3
redisPort=$4
rabbitHost=$5
rabbitPort=$6
mysqlUrl=$7
sleep 120 && java -jar \
-Xms200m -Xmx200m \
 -agentlib:jdwp=transport=dt_socket,address=6001,suspend=n,server=y \
 -Deureka.client.serviceUrl.defaultZone=http://micro:fast@:${register1}10002/eureka/,http://micro:fast@${register2}:10004/eureka/ \
 -Deureka.instance.prefer-ip-address=true \
 -Dspring.redis.host=${redisHost} \
 -Dspring.redis.port=${redisPort} \
 -Dspring.rabbitmq.host=${rabbitHost} \
 -Dspring.rabbitmq.port=${rabbitPort} \
 -Dspring.datasource.druid.url='jdbc:mysql://'+${mysqlUrl}+'/micro?useUncoide=true&chracterEncoding=utf-8&useSSL=false' \
 -Dspring.datasource.druid.master.url='jdbc:mysql://'+${mysqlUrl}+'/micro?useUncoide=true&chracterEncoding=utf-8&useSSL=false' \
 app.jar
