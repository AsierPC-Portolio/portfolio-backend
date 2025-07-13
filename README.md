# portfolio-backend

Personal portfolio backend following Hexagonal Architecture (Ports & Adapters).

## Technologies and Tools

- **Java 21**
- **Spring Boot** (latest stable version)
- **Hexagonal Architecture** (Ports & Adapters)
- **Maven** (dependency management)
- **Testing:** JUnit, Mockito, Testcontainers
- **Database:** PostgreSQL (running with Docker)
- **Liquibase** (database migrations)
- **Lombok** (to reduce boilerplate code)
- **MapStruct** (for mapping between models, DTOs, and entities)
- **Spring Security** (authentication and authorization)
- **Spring Boot Actuator** (metrics and health endpoints)
- **Jacoco** (test coverage)
- **Checkstyle/Spotless** (code formatting and quality)
- **SLF4J + Logback** (structured logging)
- **Swagger/OpenAPI** (API First)
- **OpenAPI Generator** (code generation from specification)
- **Spring DevTools** (automatic reload in development)
- **Docker** (for database and deployment)
- **Git** (version control)

## Description

This project is the backend for the personal portfolio, designed to be clean, maintainable, and easily extensible. It follows Hexagonal Architecture to separate business logic from external dependencies.

## Initial Structure

- `/src/main/java` — Source code
- `/src/test/java` — Tests
- `/docs` — OpenAPI/Swagger documentation

## Getting Started

1. Clone the repository.
2. Start the PostgreSQL database with Docker (see instructions later in this README).
3. Build and run the project with Maven.

More details and useful commands will be added soon.
