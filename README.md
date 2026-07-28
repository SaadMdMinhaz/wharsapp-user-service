# Connectly User Service

[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk)](https://jdk.java.net/21)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

User management microservice for the Connectly messaging platform. Manages user profiles, contacts, blocking, privacy settings, and user search.

---

## Features

- User profile CRUD (create, read, update, deactivate/reactivate)
- Contact management (add, rename, remove)
- Block/unblock users
- Privacy settings (profile photo, last seen, about visibility)
- User search by name, email, or phone
- JWT-protected endpoints
- Swagger/OpenAPI documentation
- Flyway database migrations

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| **Framework** | Spring Boot 3 |
| **Language** | Java 21 |
| **Database** | SQL Server 2022 |
| **ORM** | Spring Data JPA / Hibernate |
| **Migrations** | Flyway |
| **Security** | Spring Security + JWT |
| **DTO Mapping** | MapStruct 1.5.5 |
| **API Docs** | Springdoc OpenAPI 2.5.0 |
| **Build** | Maven |

---

## API Endpoints

### User Profile

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/users` | Create user profile |
| GET | `/api/v1/users/{id}` | Get user profile |
| PUT | `/api/v1/users/{id}` | Update profile |
| DELETE | `/api/v1/users/{id}` | Deactivate user |
| PATCH | `/api/v1/users/{id}/activate` | Reactivate user |
| GET | `/api/v1/users/search?q=` | Search users |

### Contacts

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/users/contacts` | List contacts |
| POST | `/api/v1/users/contacts` | Add contact |
| PUT | `/api/v1/users/contacts/{contactId}` | Rename contact |
| DELETE | `/api/v1/users/contacts/{contactId}` | Delete contact |

### Privacy

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/users/privacy` | Get privacy settings |
| PUT | `/api/v1/users/privacy` | Update privacy settings |

### Blocking

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/users/block` | Block a user |
| DELETE | `/api/v1/users/block/{blockedUserId}` | Unblock a user |
| GET | `/api/v1/users/blocked` | List blocked users |

---

## Prerequisites

- **Java 21+**
- **Maven 3.8+**
- **SQL Server 2022** with database `whatsapp_users`

---

## Configuration

Edit `src/main/resources/application.yml`:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=whatsapp_users;encrypt=false
    username: sa
    password: YourPassword123
  flyway:
    enabled: true
    locations: classpath:db/migration
```

---

## Getting Started

### 1. Create Database

```sql
CREATE DATABASE whatsapp_users;
```

### 2. Run Migrations & Service

```bash
mvn clean package -DskipTests
java -jar target/user-service-1.0.0.jar
```

The service starts on port **8081**. Flyway runs migrations automatically on startup.

### Swagger UI

Open `http://localhost:8081/swagger-ui.html` after starting.

---

## Docker

```bash
docker build -t connectly-user .
docker run -p 8081:8081 connectly-user
```

---

## Project Structure

```
src/main/java/com/whatsapp/userservice/
├── config/          # OpenApiConfig, SecurityConfig
├── controller/      # UserController
├── dto/             # request/ + response/ DTOs
├── entity/          # User, Contact, UserPrivacy, BlockedUser
├── enums/           # Visibility
├── exception/       # GlobalExceptionHandler + custom exceptions
├── mapper/          # MapStruct mappers
├── repository/      # JPA repositories
├── security/        # JwtAuthenticationFilter, JwtService, etc.
├── service/         # Interfaces + impl/ implementations
└── util/            # PhoneValidator
```

---

## Related Repositories

| Service | Repository |
|---------|-----------|
| Frontend | [whatsapp-web](https://github.com/SaadMdMinhaz/whatsapp-web) |
| Auth Service | [whatsapp-auth](https://github.com/SaadMdMinhaz/whatsapp-auth) |
| Chat Service | [whatsapp-chat-service](https://github.com/SaadMdMinhaz/whatsapp-chat-service) |
| Media Service | [whatsapp-media-service](https://github.com/SaadMdMinhaz/whatsapp-media-service) |
| Gateway Service | [whatsapp-gateway-service](https://github.com/SaadMdMinhaz/whatsapp-gateway-service) |

---

## License

[MIT](LICENSE)
