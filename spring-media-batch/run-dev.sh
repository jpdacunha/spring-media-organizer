
#!/bin/bash

./mvnw spring-boot:run -e -Dspring-boot.run.profiles=local -Dspring.profiles.active=local -Dspring.config.location="/home/dev/git/spring-media-organizer/spring-media-batch/config/application.yml"
