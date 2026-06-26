# Current Architecture

## Infrastructure

### PostgreSQL 17 (Docker)

Databases:

* devpath_learning
* devpath_note
* devpath_auth

---

## Services

### Naming Server

Port:

```text
8761
```

Technology:

* Spring Boot
* Eureka Server

Status:

* Running
* Service Discovery enabled

---

### API Gateway

Port:

```text
8765
```

Technology:

* Spring Cloud Gateway
* Eureka Discovery Client

Status:

* Registered in Eureka
* Routing layer ready
* Future single entry point for external clients

Future responsibilities:

* Route `/auth/**` requests to Authentication Service
* Route `/courses/**` requests to Learning Service
* Route `/modules/**` requests to Learning Service
* Forward the `Authorization` header to downstream services
* Hide internal service ports from clients

---

### Authentication Service

Port:

```text
8182
```

Technology:

* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* BCrypt
* MapStruct

Database:

```text
devpath_auth
```

Domain Model:

```text
User
```

Implemented Features:

* User Registration
* User Login
* BCrypt Password Hashing
* JWT Generation
* JWT Validation
* Stateless Authentication
* Spring Security Integration
* Global Exception Handling
* Request Validation
* Custom UserDetails Implementation
* Role-based Authorization
* Method Security with @PreAuthorize
* JWT issuer for DevPath services

Status:

* Registered in Eureka
* Connected to PostgreSQL
* Registration verified
* Login verified
* JWT generation verified
* JWT validation verified
* Protected endpoint authentication verified
* SecurityContext population verified
* Role-based authorization verified
* JWT consumed by Learning Service

---

### Learning Service

Port:

```text
8081
```

Technology:

* Spring Boot
* Spring Web
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* Eureka Discovery Client
* Jakarta Bean Validation
* MapStruct
* SpringDoc OpenAPI
* Swagger UI
* JUnit 5
* Mockito
* AssertJ
* MockMvc
* Testcontainers

Database:

```text
devpath_learning
```

Domain Model:

```text
Course
 └── Module
```

Implemented Features:

* Course CRUD
* Module CRUD
* Course → Module relationship
* Hibernate automatic schema generation
* PostgreSQL persistence
* Global Exception Handling
* Request Validation
* DTO Pattern
* MapStruct Mapping Layer
* OpenAPI Documentation
* Swagger UI Integration
* Service Unit Testing
* Repository Integration Testing
* Controller Testing
* JWT Validation
* Stateless Security
* Role-based Authorization
* Method Security with @PreAuthorize

Status:

* Registered in Eureka
* Connected to PostgreSQL
* Actuator enabled
* CRUD operations verified
* Relationship persistence verified
* Validation verified
* Exception handling verified
* DTO mapping verified
* OpenAPI documentation verified
* Swagger UI verified
* Service layer tested
* Repository layer tested with Testcontainers and PostgreSQL
* Controller layer tested with MockMvc
* JWT validation verified
* Role-based authorization verified
* Protected endpoints verified

---

## Security Architecture

Authentication and authorization are implemented using JWT-based stateless security.

### Authentication Service

Responsibilities:

* Registers users
* Authenticates users through email and password
* Assigns user roles
* Generates signed JWT tokens
* Stores user credentials and roles in devpath_auth
* Acts as JWT issuer for protected DevPath services

### Learning Service

Responsibilities:

* Does not authenticate users through credentials
* Does not store user passwords
* Receives JWT tokens through the `Authorization` header
* Validates JWT signature and expiration
* Extracts user email and role from the token
* Populates the Spring SecurityContext
* Applies method-level authorization through @PreAuthorize

### API Gateway

Current status:

* Registered in Eureka
* Routing layer available

Next responsibility:

* Become the single external entry point
* Route authentication and learning requests
* Forward JWT tokens through the `Authorization` header to downstream services

---

## Current JWT Flow

```text
Client / Postman
        │
        │ POST /auth/login
        ▼
Authentication Service
        │
        │ Issues signed JWT
        ▼
Client / Postman
        │
        │ Authorization: Bearer <token>
        ▼
Learning Service
        │
        │ Validates JWT
        │ Extracts email and role
        ▼
@PreAuthorize
        │
        ▼
Controller Method
```

Authorization rules in Learning Service:

```text
GET    courses/modules    USER or ADMIN
POST   courses/modules    ADMIN only
PUT    courses/modules    ADMIN only
DELETE courses/modules    ADMIN only
```

---

## Target Gateway Flow

The next architecture step is to introduce API Gateway as the only entry point for clients.

```text
Client / Frontend
        │
        ▼
API Gateway
        │
        ├── /auth/**          → Authentication Service
        ├── /courses/**       → Learning Service
        └── /modules/**       → Learning Service
```

JWT propagation through the gateway:

```text
Client
        │
        │ Authorization: Bearer <token>
        ▼
API Gateway
        │
        │ forwards Authorization header
        ▼
Learning Service
        │
        │ validates JWT locally
        ▼
Protected endpoint
```

---

## Current Topology

```text
                      Eureka Server
                     localhost:8761
                            ▲
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
  API Gateway      Learning Service   Authentication Service
 localhost:8765     localhost:8081        localhost:8182
                            │                   │
                            └─────────┬─────────┘
                                      ▼
                               PostgreSQL 17
                               localhost:5432
                                      │
          ┌───────────────┬───────────┴───────────┐
          │               │                       │
  devpath_learning   devpath_note          devpath_auth
```

---

## Current Development Status

Completed:

* Infrastructure setup
* Docker environment
* PostgreSQL integration
* Eureka Service Discovery
* API Gateway base setup
* Learning Service
* Course CRUD
* Module CRUD
* JPA relationships
* Global Exception Handling
* Request Validation
* DTO Pattern
* MapStruct Integration
* OpenAPI Documentation
* Swagger UI Documentation
* Service Unit Testing
* Repository Integration Testing
* Controller Testing
* Authentication Service
* User Registration
* User Login
* JWT Authentication
* JWT Authorization
* Stateless Security
* Role-based Access Control
* Method Security
* Learning Service JWT Validation
* Learning Service protected endpoints
* JWT propagation concept documented

Next Planned Improvements:

* API Gateway as single external entry point
* Gateway routing for authentication-service and learning-service
* Authorization header forwarding through API Gateway
* Pagination and Sorting
* Search Capabilities
* Note Service implementation
