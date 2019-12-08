# Taco Cloud


## Introduction
Taco Cloud is a sample application from the book Spring in Action, 5th Edition by Craig Walls.
It is a website that allows users to design their custom tacos and order them online.
The application is only a prototype that follows what is taught in the book.

Taco Cloud uses the following technologies:
* Java 11
* Maven
* Spring Boot 2
* H2 in-memory database
* Thymeleaf

The application demonstrates the following:
* Spring MVC using Thymeleaf
* Spring Security with JPA based user credentials
* Spring Profiles (different application properties for dev/qa/prod)


## Application
The following features are currently present in the application
* User registration / login
* Design Taco MVC
* Order Tacos MVC

## To Run

### The Application
```
mvn clean install
mvn spring-boot:run -f app
```

### Active MQ
```
docker run -it -d --rm \
-e ARTEMIS_USERNAME=admin -e ARTEMIS_PASSWORD=admin \
-p 8161:8161 -p 61616:61616 \
--name artemis vromero/activemq-artemis
```
