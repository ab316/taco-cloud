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
* Messaging from Taco Cloud to Taco Kitchen using
    * Artemis (JMS)
    * RabbitMQ (AMPQ). Default choice

## To Run
### Taco Cloud
```
mvn spring-boot:run -f app
```

### Taco Kitchen
```
mvn spring-boot:run -f kitchen
```

### Messaging Setup
#### Artemis
Launch the container using the command below
```
docker run -it -d --rm \
-e ARTEMIS_USERNAME=admin -e ARTEMIS_PASSWORD=admin \
-p 8161:8161 -p 61616:61616 \
--name artemis vromero/activemq-artemis
```

#### RabbitMQ
1. Launch the container using the command below
1. Create an exchange `tacocloud.orders`
3. Create a queue `tacocloud.order.queue`
4. Bind the queue to the exchange with the routing key `tacocloud.order`
```
docker run -d --name rabbitmq  \
-p 5672:5672 -p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin \
rabbitmq:3-management
```