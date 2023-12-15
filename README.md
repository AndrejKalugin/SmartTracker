# smart tracker

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Requirements

For building and running the application you need:

- [JDK 1.17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.tracker.smartTracker.SmartTrackerApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## DB configuration

DB for the current project PostgreSQL, one config you should change are path to DB in application.properties:

> **spring.datasource.url**=jdbc:postgresql://127.0.0.1:5432/tracker
> 
> **spring.flyway.url**=jdbc:postgresql://127.0.0.1:5432/tracker

## ✨UI for the application

use Swagger UI for convenience:

> http://localhost:8080/swagger-ui/index.html

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
