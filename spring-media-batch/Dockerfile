FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/spring-media-batch.jar ./app.jar
COPY ./config/application.yml ./
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.config.location=/application.yml --spring.profiles.active=live" ]