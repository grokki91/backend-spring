# API Marvel Application

## Description
This is a backend application built with Spring Boot. It provides the following functionalities:

- **Authorization and Authentication**: Uses JWT for secure user authentication.
- **User Management**: Allows managing users with two roles: `ROLE_ADMIN` and `ROLE_USER`.
- **Marvel Characters Management**: Provides full CRUD operations to manage Marvel characters.

## Prerequisites
- **Java**: Version 17 or higher
- **Maven**
- **PostgreSQL**: Version 16 (configured without Docker)

## Database Setup
1. Create a database named `heroes_db` in PostgreSQL.
2. Set the following environment variables on application.properties:
    - `DB_USERNAME`: Your PostgreSQL username
    - `DB_PASSWORD`: Your PostgreSQL password


```properties
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
```

## Build and Run
1. Clone the repository:
   ```bash
   git clone https://github.com/grokki91/backend-spring.git
   cd backend-spring
   ```
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Documentation (Swagger)

```
http://localhost:8080/swagger-ui/index.html
```

## Application Roles
- **ROLE_ADMIN**: Has full access to all endpoints.
- **ROLE_USER**: Can access and manage their own user profile and perform limited actions on Marvel characters.
