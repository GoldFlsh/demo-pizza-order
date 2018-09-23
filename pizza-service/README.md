# Pizza Service

Allows client to create a pizza and administrators to 
add and delete configuration of what crusts, sauces, 
cheeses, and ingredients are available 
## Getting Started

At the project root you can build this project using
```
./gradlew :pizza-service:build
```

Run locally using 
```
./gradlew :pizza-service:bootRun
```

### Prerequisites

Java JDK/JRE 10

## Running the tests

At the project root run 
```
./gradlew test 
```

## Docker
Commands to build and run when in the submodule's root directory:
```
docker build -t spring-boot-pizza-service .
docker run -p 8080:8080 spring-boot-pizza-service
```

## Built With

* [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) - Dependency Management
* [Docker](https://www.docker.com/) - Containerization