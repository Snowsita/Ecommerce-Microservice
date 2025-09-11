# E-commerce Microservices

This project is a microservices-based application built with Spring Boot and Spring Cloud. It consists of several services that work together to provide a complete e-commerce platform.

## Architecture

The application is composed of the following microservices:

*   **Config Server:** A central configuration server that provides configuration to all other services. It uses Spring Cloud Config Server.
*   **Discovery Server:** A service registry that allows services to find each other. It uses Spring Cloud Netflix Eureka.
*   **Customer Service:** Manages customer data. It uses MongoDB as its data store.
*   **Product Service:** Manages product data. It uses PostgreSQL as its data store and Flyway for database migrations.
*   **Order Service:** Manages customer orders. It uses PostgreSQL as its data store and communicates with the customer and product services via REST APIs (using OpenFeign) to retrieve customer and product information. It also sends order confirmation events to a Kafka topic.
*   **Payment Service:** Manages payments. It uses PostgreSQL as its data store and sends payment confirmation events to a Kafka topic.
*   **Notification Service:** Consumes order and payment confirmation events from Kafka topics and sends email notifications to customers.

All services are configured to register with the Eureka discovery server and retrieve their configuration from the Config Server.

## Technologies Used

*   **Spring Boot:** For building the microservices.
*   **Spring Cloud:** For building the microservices-based application, including:
    *   **Spring Cloud Config:** For centralized configuration.
    *   **Spring Cloud Netflix Eureka:** For service discovery.
    *   **Spring Cloud OpenFeign:** For declarative REST API communication.
*   **Spring Data JPA:** For accessing data in relational databases.
*   **Spring Data MongoDB:** For accessing data in MongoDB.
*   **Spring Kafka:** For asynchronous messaging.
*   **PostgreSQL:** As a relational database.
*   **MongoDB:** As a NoSQL database.
*   **Kafka:** As a message broker.
*   **Docker:** For containerizing the application.
*   **Maven:** For building the project.
*   **Lombok:** For reducing boilerplate code.

## Building and Running

To build and run the project, you will need to have the following installed:

*   Java 17
*   Maven
*   Docker

To build the project, run the following command from the root directory:

```bash
mvn clean install
```

To run the project, you can use the provided `docker-compose.yml` file. To do so, run the following command from the root directory:

```bash
docker-compose up
```

This will start all of the services, as well as the required infrastructure, such as PostgreSQL, MongoDB, and Kafka.

## Services

### Config Server

The Config Server is a central configuration server that provides configuration to all other services. It is a Spring Cloud Config Server that is configured to use a Git repository as its backend. The configuration for each service is stored in a separate YAML file in the Git repository.

### Discovery Server

The Discovery Server is a service registry that allows services to find each other. It is a Spring Cloud Netflix Eureka Server. All other services are configured to register with the Discovery Server on startup.

### Customer Service

The Customer Service manages customer data. It provides a REST API for creating, retrieving, updating, and deleting customers. It uses MongoDB as its data store.

### Product Service

The Product Service manages product data. It provides a REST API for creating, retrieving, updating, and deleting products. It uses PostgreSQL as its data store and Flyway for database migrations.

### Order Service

The Order Service manages customer orders. It provides a REST API for creating and retrieving orders. It communicates with the Customer Service and Product Service to retrieve customer and product information. It also sends order confirmation events to a Kafka topic.

### Payment Service

The Payment Service manages payments. It provides a REST API for creating payments. It also sends payment confirmation events to a Kafka topic.

### Notification Service

The Notification Service consumes order and payment confirmation events from Kafka topics and sends email notifications to customers. It uses Thymeleaf to generate the email content.
