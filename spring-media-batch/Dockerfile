FROM adoptopenjdk/openjdk11:jre-11.0.16.1_1-alpine
VOLUME /tmp
RUN mkdir -p /home/spring-media-batch/database
COPY ./target/spring-media-batch.jar ./app.jar
COPY ./config/application.yml ./
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.config.location=/application.yml --spring.profiles.active=live" ]