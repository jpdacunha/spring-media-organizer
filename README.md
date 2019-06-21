# Spring media batch : organize medias in a comprehensive way

## Running application in development

Running application from command line in dev mode

```shell
$ ./mvnw spring-boot:run -Dspring.profiles.active=local
```

## Running application in docker container locally

Creating new docker container and run it locally

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
## Utilities

Modification date update of an existing file

```shell
touch -m -a -t <date> <file>
```

Exemple

```shell
touch -m -a -t 201512180130.09 ./354PPP.jpg
```