FROM java:8

WORKDIR /home/apps/

COPY target/*.jar consumer.jar

ENV CONFIG_PROFILE dev

RUN sh -c 'touch consumer.jar'

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar -Xmx256m consumer.jar" ]