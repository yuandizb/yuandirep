#!/usr/bin/env bash
cd ./config-server/
mvn  docker:push
cd ../gateway/
mvn  docker:push
cd ../register-center1/
mvn  docker:push
cd ../register-center2/
mvn  docker:push
cd ../ucenter/
mvn  docker:push
cd ../upms/
mvn  docker:push
cd ../oauth/
mvn  docker:push
cd ../monitor-turbine/
mvn  docker:push
cd ../monitor-zipkin/
mvn  docker:push

