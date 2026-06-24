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

---

### Learning Service

Port:

```text
8081
```

Technology:

* Spring Boot
* Spring Web
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

---

### Authentication Service

Port:

```text
8082
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

Status:

* Registered in Eureka
* Connected to PostgreSQL
* Registration verified
* Login verified
* JWT generation verified
* JWT validation verified
* Protected endpoint authentication verified
* SecurityContext population verified

```
```


## Current Topology

```text
                      Eureka Server
                     localhost:8761
                            ▲
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
  API Gateway      Learning Service   Authentication Service
 localhost:8765     localhost:8081        localhost:8082
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

## API Documentation Layer

Technology:

* SpringDoc OpenAPI
* Swagger UI

Responsibilities:

* Automatic OpenAPI specification generation
* Interactive API documentation
* DTO schema generation
* Validation documentation
* Error response documentation

Documented Components:

* CourseController
* ModuleController
* Request DTOs
* Response DTOs
* ApiError

---

## Testing Strategy

Learning Service is tested across controller, service, and repository layers.

### Service Layer Testing

Technology:

* JUnit 5
* Mockito
* AssertJ

Scope:

* Business logic
* Repository interaction
* Mapper interaction
* Success scenarios
* Resource not found scenarios

Test classes:

* CourseServiceTest
* ModuleServiceTest

### Repository Integration Testing

Technology:

* Spring Boot Test
* Testcontainers
* PostgreSQL

Scope:

* Entity persistence
* Entity retrieval
* Custom repository queries
* Course → Module relationship
* PostgreSQL integration

Test classes:

* CourseRepositoryIT
* ModuleRepositoryIT

### Controller Testing

Technology:

* WebMvcTest
* MockMvc
* MockitoBean
* ObjectMapper

Scope:

* REST endpoints
* HTTP status codes
* JSON responses
* Request validation errors
* Service interaction

Test classes:

* CourseControllerTest
* ModuleControllerTest

---

## Learning Service Domain Model

```text
Course
│
├── id
├── title
├── description
├── difficulty
├── createdAt
└── updatedAt
│
└── 1..N
      │
      ▼
Module
├── id
├── title
├── description
└── position
```

---

## Learning Service Internal Architecture

```text
Controller
    │
    ▼
Request DTO
    │
    ▼
Service Layer
    │
    ▼
Request Mapper (MapStruct)
    │
    ▼
Entity
    │
    ▼
Repository
    │
    ▼
PostgreSQL
```

Response flow:

```text
PostgreSQL
    │
    ▼
Entity
    │
    ▼
Response Mapper (MapStruct)
    │
    ▼
Response DTO
    │
    ▼
Controller
```

Testing flow:

```text
Controller Tests
    │
    ▼
MockMvc + Mocked Service

Service Tests
    │
    ▼
Mockito Repositories + Mockito Mappers

Repository Integration Tests
    │
    ▼
Testcontainers PostgreSQL
```

---

## Current Development Status

Completed:

* Infrastructure setup
* Docker environment
* PostgreSQL integration
* Eureka Service Discovery
* API Gateway setup
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
* Testcontainers PostgreSQL Integration
* MockMvc REST API Testing
* Git Flow workflow
* Pull Request workflow
* Architecture documentation
* Authentication Service
* User Registration
* User Login
* JWT Authentication
* Spring Security Integration

Next Planned Improvements:

* Pagination and Sorting
* Search Capabilities
* Note Service implementation
