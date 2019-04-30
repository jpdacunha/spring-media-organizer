# Spring media batch : organize medias in a comprehensive way

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