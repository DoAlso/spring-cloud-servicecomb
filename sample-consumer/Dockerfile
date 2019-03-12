FROM openjdk:8u181-jdk-alpine

WORKDIR /home/apps/

COPY target/*.jar consumer.jar

ENV SAMPLE_PROFILE dev

RUN sh -c 'touch consumer.jar'

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar -Xmx256m consumer.jar --spring.cloud.config.profile=${SAMPLE_PROFILE}" ]