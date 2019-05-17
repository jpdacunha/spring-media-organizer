# Spring media batch : organize medias in a comprehensive way

## Runing application

Runing application from command line in dev mode

```shell
$ ./mvnw spring-boot:run
```

## Building and publishing docker image

For refreshing image with new source in eclispe execute 

```shell
$ cd ./spring-media-organizer/spring-media-batch/
$ mvn clean install
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