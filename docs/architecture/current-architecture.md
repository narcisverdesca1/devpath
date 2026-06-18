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

Database:

```text
devpath_learning
```

Current Domain:

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

Status:

* Registered in Eureka
* Connected to PostgreSQL
* Actuator enabled
* CRUD operations verified
* Relationship persistence verified

---

## Current Topology

```text
                     Eureka Server
                     localhost:8761
                            ▲
                            │
            ┌───────────────┴───────────────┐
            │                               │
            │                               │
      API Gateway                  Learning Service
      localhost:8765               localhost:8081
                                           │
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

Relationship:

```text
Course (1) ──────────────► (N) Module
```

Persistence:

```text
module.course_id → course.id
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
* Git Flow workflow
* Pull Request workflow
* Architecture documentation

Next Planned Improvements:

* Validation Layer
* Global Exception Handling
* DTO Pattern
* Swagger / OpenAPI Documentation
* Note Service implementation
