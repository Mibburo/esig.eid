FROM openjdk:8-jdk-alpine
MAINTAINER Kon Bi
VOLUME /tmp
ADD ./target/esig.eid-0.0.1-SNAPSHOT.jar  app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 7001
