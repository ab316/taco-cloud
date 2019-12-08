# Taco Cloud


## Introduction
Taco Cloud is a sample application from the book Spring in Action, 5th Edition by Craig Walls.
It is a website that allows users to design their custom tacos and order them online.
The application is only a prototype that follows what is taught in the book.

Taco Kitchen is a supplementary application that listens for Order messages from the Taco Cloud application.

The project uses the following technologies:
* Java 11
* Maven
* Spring Boot 2
* H2 in-memory database
* Thymeleaf
* JMS (Artemis)

The project demonstrates the following:
* Spring MVC using Thymeleaf
* Spring Security with JPA based user credentials
* Spring Profiles (different application properties for dev/qa/prod)
* Spring JMS messaging with Artemis


## Application
The following features are currently present in the project
* User registration / login
* Design Taco MVC
* Order Tacos MVC
* Messaging from Taco Cloud to Taco Kitchen

## To Run
### Taco Cloud
```
mvn spring-boot:run -f app
```

### Taco Kitchen
```
mvn spring-boot:run -f kitchen
```

### Artemis Docker Container
```
docker run -it -d --rm \
-e ARTEMIS_USERNAME=admin -e ARTEMIS_PASSWORD=admin \
-p 8161:8161 -p 61616:61616 \
--name artemis vromero/activemq-artemis
```
