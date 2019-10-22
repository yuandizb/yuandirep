# 构建java镜像
FROM ubuntu:latest
MAINTAINER lishouyu
WORKDIR /
RUN mkdir workhome
WORKDIR /opt
RUN mkdir lib
ADD ./jdk1.8.0_144 /opt/lib/jdk1.8.0_144/
RUN echo "export JAVA_HOME=/opt/lib/jdk1.8.0_144" >> /etc/profile
RUN echo "export JAVA_JRE=\${JAVA_HOME}/jre" >> /etc/profile
RUN echo "export PATH=\${JAVA_HOME}/bin:\$PATH" >> /etc/profile
