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
* AMQP (RabbitMQ)

The project demonstrates the following:
* Spring MVC using Thymeleaf
* Spring Security with JPA based user credentials
* Spring Profiles (different application properties for dev/qa/prod)
* Spring JMS messaging with Artemis and RabbitMQ


## Application
The following features are present in the project
* User registration / login
* Design Taco MVC
* Order Tacos MVC
* Messaging from Taco Cloud to Taco Kitchen using
    * Artemis (JMS)
    * RabbitMQ (AMQP). Default choice
    
The project consists of two separate applications
1. Taco Cloud
2. Taco Kitchen

Taco Cloud is the main application containing all the features. Taco Kitchen is a
small application that only listens from messages from Taco Cloud

## To Run
### Taco Cloud
```
mvn spring-boot:run -f tacocloud-app
```

### Taco Kitchen
```
mvn spring-boot:run -f kitchen
```

To change the messaging service from rabbitMQ to artemis add the below
argument to the maven commands for both apps:
`-Dmessaging.service=artemis`

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