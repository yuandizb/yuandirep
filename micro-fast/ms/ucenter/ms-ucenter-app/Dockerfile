FROM registry.cn-hangzhou.aliyuncs.com/lishouyu/hub:javaV0.0.1
ADD ../target/ms-ucenter.jar /workhome/app.jar
ADD cmd-dev.sh /workhome/cmd-dev.sh
RUN set -xe && chmod u+x /workhome/cmd-dev.sh
ENV JAVA_HOME /opt/lib/jdk1.8.0_144
ENV JAVA_JRE ${JAVA_HOME}/jre
ENV PATH ${JAVA_HOME}/bin:$PATH
EXPOSE 10010
EXPOSE 10011
WORKDIR /workhome
CMD ["java","-jar","-Xms200m", "-Xmx200m","-agentlib:jdwp=transport=dt_socket,address=6001,suspend=n,server=y","app.jar"]