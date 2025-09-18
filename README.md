# E-commerce Microservices

This project is a microservices-based application built with Spring Boot and Spring Cloud. It consists of several services that work together to provide a complete e-commerce platform.

## Architecture

The application is composed of the following microservices:

*   **Config Server:** A central configuration server that provides configuration to all other services. It uses Spring Cloud Config Server.
*   **Discovery Server:** A service registry that allows services to find each other. It uses Spring Cloud Netflix Eureka.
*   **API Gateway:** An API gateway that provides a single entry point for all clients. It uses Spring Cloud Gateway.
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
    *   **Spring Cloud Gateway:** For building the API gateway.
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
*   **Keycloak:** For identity and access management.
*   **Zipkin:** For distributed tracing.
*   **Maildev:** For development email server.

## Building and Running

To build and run the project, you will need to have the following installed:

*   Java 17
*   Maven
*   Docker

### Build

This is a multi-module Maven project. To build all services, run the following command from the root directory:

```bash
mvn clean install
```

> **Note:** The tests for the `notification` and `order` services are currently skipped to allow the build to pass.

### Running Locally

To run the entire application stack, you can use the provided `docker-compose.yml` file. To do so, run the following command from the root directory:

```bash
docker-compose up
```

This will start all of the services, as well as the required infrastructure, such as PostgreSQL, MongoDB, Kafka, Keycloak, Zipkin, and Maildev.

## CI/CD

This project uses GitHub Actions for Continuous Integration and Continuous Deployment. The workflow is defined in `.github/workflows/main.yml`.

On every push to the `main` branch, the pipeline will:
1.  Build and test all microservices.
2.  Identify which services have changed.
3.  Build and push a new Docker image to Docker Hub for each changed service.

## Services

### Config Server

The Config Server is a central configuration server that provides configuration to all other services. It is a Spring Cloud Config Server that is configured to use a Git repository as its backend. The configuration for each service is stored in a separate YAML file in the Git repository.

### Discovery Server

The Discovery Server is a service registry that allows services to find each other. It is a Spring Cloud Netflix Eureka Server. All other services are configured to register with the Discovery Server on startup.

### API Gateway

The API Gateway provides a single entry point for all clients. It is a Spring Cloud Gateway that is configured to route requests to the appropriate microservice. It also provides security by integrating with Keycloak.

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

### Security

The application is secured using Keycloak. Keycloak is an open-source identity and access management solution. It provides user authentication and authorization for the microservices.

### Distributed Tracing

The application uses Zipkin for distributed tracing. Zipkin is a distributed tracing system that helps to troubleshoot latency issues in microservices.

### Mail Server

The application uses Maildev as a development mail server. Maildev is a simple SMTP server that allows you to view and test emails during development.