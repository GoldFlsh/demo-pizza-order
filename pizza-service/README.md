# Pizza Service

Allows client to create a pizza for use in ordering. 
Administrators can configure what crusts, sauces, 
cheeses, and ingredients are available to use during pizza creation 
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

## Swagger
When the app is running view swagger specification at
```
http://{host}:8080/pizza/v2/api-docs
http://{host}:8080/pizza/swagger-ui.html
```

## Built With

* [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) - Dependency Management
* [Docker](https://www.docker.com/) - Containerization


## Todo List
#### Design
* relies on ext. service for cleanup of created pizzas consider a timeout
* Requires ext. service to keep track of pizza id...maybe that's ok in this case
* Duplicate pizzas across many 'users' and many orders will end up in DB...consider possible solutions
  may be ok if pizzas are always cleaned up after orders are finished. But if pizzas are 'saved' 
  for reuse then it would become a problem.

#### Implementation
* Global Exception Handler to catch and handle elegant error responses to client(s)
* Using File-based H2 DB which is not good for persistence at all. It's fine for proof of concept but
  need to move to a database that can be accessed from outside the application.
* Secure such that only administrators can access the configuration services
* Add Swagger annotations to better define rest API