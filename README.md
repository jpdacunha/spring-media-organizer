# Spring media batch : organize medias in a comprehensive way

## Running application in development

Running application from command line in dev mode

```shell
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local -Dspring.config.location="/home/dev/git/spring-media-organizer/spring-media-batch/config/application.yml"
```

## Building and publishing docker image

For refreshing image with new source in eclipse execute 

```shell
$ cd ./spring-media-organizer/spring-media-batch/
$ mvn clean install
```

To build the image skipping tests execution
```shell
$ cd ./spring-media-organizer/spring-media-batch/
mvn clean install -Dmaven.test.skip=true
```

To push new image to docker Hub execute

```shell
$ docker push jpdacunha/spring-media-batch:0.0.1-SNAPSHOT
```

### In case of permission denied errors do the following

Authorize docker execution without sudo
```shell
$ sudo groupadd docker
$ sudo usermod -aG docker $USER
$ newgrp docker
```

Create a settings.xml under ~/.m2 path and add the Docker Hub credentials
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>registry.hub.docker.com</id>
            <username>USERNAME</username>
            <password>PASSWORD</password>
            <configuration>
                <email>EMAIL_ADDRESS</email>
            </configuration>
        </server>
    </servers>
</settings>
```

## Running application in docker container localy

Creating new docker container and run it localy

```shell
$ sudo docker container run -d --name <container_name> <repository>/<image>:<version> 
```

    Example : sudo docker container run -d --name spring-media-batch jpdacunha/spring-media-batch:0.0.1-SNAPSHOT 

Starting an existing container

```shell
sudo docker container start spring-media-batch
```

Stopping an existing container

```shell
sudo docker container stop spring-media-batch
```

Opening a shell in running container

```shell
$ sudo docker container exec -i -t <container_name> ash
```
    Example : sudo docker container exec -i -t spring-media-batch ash

## Running Junit tests

Run your tests using custom configuration. In eclipse Open your Junit running test configuration and add following values to JVM arguments

```shell
-Dspring.config.location="/home/dev/git/spring-media-organizer/spring-media-batch/config/application.yml"
-Dspring.profiles.active=test
```

File classification is based on files's update date. In order to correctly setup files modification dates please execute this script BEFORE tuning Junit tests :

```shell
$ cd <git_project_home>/spring-media-organizer/spring-media-batch/setup-tests-env.sh
```