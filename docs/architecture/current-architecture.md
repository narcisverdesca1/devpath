# Current Architecture

## Overview

DevPath is a microservices-based learning platform built with Spring Boot and Spring Cloud.

The current architecture consists of:

* PostgreSQL
* Eureka Service Discovery
* API Gateway
* Authentication Service
* Learning Service

API Gateway acts as the single external entry point while Authentication Service and Learning Service remain independent microservices.

---

## Infrastructure

### PostgreSQL 17 (Docker)

Databases:

* devpath_learning
* devpath_note
* devpath_auth

---

## Service Topology

```text
                          Eureka Server
                         localhost:8761
                                ▲
                                │
        ┌───────────────────────┼────────────────────────┐
        │                       │                        │
        ▼                       ▼                        ▼
   API Gateway        Authentication Service     Learning Service
   localhost:8765        localhost:8182           localhost:8081
        │                       │                        │
        │                       └──────────────┐         │
        │                                      │         │
        └──────────────────────┬───────────────┘         │
                               ▼                         ▼
                      PostgreSQL 17 (Docker)
                         localhost:5432
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
   devpath_auth       devpath_learning       devpath_note
```

---

# Services

## Naming Server

Port

```text
8761
```

Technology

* Spring Boot
* Netflix Eureka Server

Responsibilities

* Service Discovery
* Service Registration
* Dynamic service lookup

Status

* Running
* Verified

---

## API Gateway

Port

```text
8765
```

Technology

* Spring Boot
* Spring Cloud Gateway
* Spring Cloud LoadBalancer
* Eureka Discovery Client

Responsibilities

* Single external entry point
* Dynamic routing
* Service Discovery
* HTTP request forwarding
* Authorization header propagation
* Hide internal service addresses

Implemented

* Eureka Registration
* Dynamic Route Configuration
* Authentication Service routing
* Learning Service routing
* JWT header propagation

Configured Routes

```text
/auth/**               → authentication-service
/courses/**            → learning-service
/modules/**            → learning-service
```

Status

* Registered in Eureka
* Routing verified
* JWT propagation verified
* End-to-end communication verified

---

## Authentication Service

Port

```text
8182
```

Technology

* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT
* BCrypt
* MapStruct

Database

```text
devpath_auth
```

Domain Model

```text
User
```

Responsibilities

* User Registration
* User Login
* Password Hashing
* JWT Generation
* JWT Validation
* User Authentication

Implemented

* Register
* Login
* BCrypt
* AuthenticationManager
* JwtService
* CustomUserDetailsService
* Stateless Security
* Method Security
* Role-Based Authorization

Status

* Registered in Eureka
* Connected to PostgreSQL
* JWT issuer for DevPath
* Fully verified

---

## Learning Service

Port

```text
8081
```

Technology

* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Eureka Discovery Client
* MapStruct
* SpringDoc OpenAPI
* Swagger UI
* JUnit 5
* Mockito
* MockMvc
* Testcontainers

Database

```text
devpath_learning
```

Domain Model

```text
Course
 └── Module
```

Responsibilities

* Course Management
* Module Management
* JWT Validation
* Authorization

Implemented

* Course CRUD
* Module CRUD
* DTO Pattern
* Bean Validation
* Global Exception Handling
* MapStruct
* OpenAPI
* Swagger
* Unit Tests
* Repository Tests
* Controller Tests
* JWT Validation
* Stateless Authentication
* Role-Based Authorization
* Method Security

Status

* Registered in Eureka
* Connected to PostgreSQL
* Fully verified

---

# Security Architecture

DevPath currently follows a distributed security architecture.

Authentication Service is responsible for authenticating users and issuing JWT tokens.

Learning Service is responsible for validating JWT tokens and authorizing requests.

API Gateway only routes requests and forwards the Authorization header.

---

## Authentication Flow

```text
                 Client
                    │
                    │ POST /auth/login
                    ▼
              API Gateway
                    │
                    ▼
       Authentication Service
                    │
            Validate credentials
                    │
                    ▼
              Generate JWT
                    │
                    ▼
                 Client
```

---

## Protected Request Flow

```text
                 Client
                    │
Authorization: Bearer <JWT>
                    │
                    ▼
              API Gateway
                    │
        Authorization Header
          forwarded unchanged
                    │
                    ▼
            Learning Service
                    │
        JwtAuthenticationFilter
                    │
          SecurityContext
                    │
            @PreAuthorize
                    │
                    ▼
               Controller
```

---

## Authorization Rules

Learning Service applies role-based authorization.

```text
GET      USER | ADMIN
POST     ADMIN
PUT      ADMIN
DELETE   ADMIN
```

---

## Current Development Status

Completed

### Infrastructure

* Docker
* PostgreSQL
* Eureka Server
* API Gateway

### Authentication

* User Registration
* User Login
* JWT Authentication
* JWT Generation
* JWT Validation
* Spring Security
* Role-Based Authorization

### Learning

* Course CRUD
* Module CRUD
* DTO Pattern
* MapStruct
* Bean Validation
* Global Exception Handling
* OpenAPI
* Swagger
* Unit Tests
* Repository Tests
* Controller Tests
* JWT Validation
* Stateless Authentication

### Gateway

* Dynamic Routing
* Service Discovery
* JWT Header Propagation
* End-to-End Routing Verification

---

## Next Planned Improvements

* Gateway Global Filters
* Logging
* Rate Limiting
* Circuit Breaker
* Pagination
* Search
* Note Service