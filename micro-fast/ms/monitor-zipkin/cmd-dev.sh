#!/usr/bin/env bash
register1=$1
register2=$2
rabbitHost=$3
rabbitPort=$4
sleep 120 && java -jar \
-Xms200m -Xmx200m \
 -agentlib:jdwp=transport=dt_socket,address=6001,suspend=n,server=y \
 -Deureka.client.serviceUrl.defaultZone=http://micro:fast@:${register1}10002/eureka/,http://micro:fast@${register2}:10004/eureka/ \
 -Deureka.instance.prefer-ip-address=true \
 -Dspring.rabbitmq.host=${rabbitHost} \
 -Dspring.rabbitmq.port=${rabbitPort} \
app.jar
