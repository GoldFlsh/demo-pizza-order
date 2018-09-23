FROM openjdk:10-jdk
LABEL maintainer="Ryan.Z.Khalil@gmail.com"
EXPOSE 8080
ARG JAR_FILE=build/libs/pizza-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} pizza-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pizza-service.jar"]