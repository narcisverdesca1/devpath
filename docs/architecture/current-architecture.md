# Current Architecture

## Infrastructure

### PostgreSQL 17 (Docker)

Databases:

* devpath_learning
* devpath_note

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

---

## Current Topology

```text
                     Eureka Server
                     localhost:8761
                            ▲
                            │
            ┌───────────────┴───────────────┐
            │                               │
      API Gateway                  Learning Service
      localhost:8765               localhost:8081
                                           │
                                           ▼
                                   PostgreSQL 17
                                   localhost:5432
                                           │
                      ┌────────────────────┴────────────────────┐
                      │                                         │
              devpath_learning                          devpath_note
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
* Git Flow workflow
* Pull Request workflow
* Architecture documentation

Next Planned Improvements:

* Integration Testing
* Unit Testing
* Pagination and Sorting
* Search Capabilities
* Note Service implementation
