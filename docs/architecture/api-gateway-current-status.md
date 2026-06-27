# API Gateway - Current Status

## Overview

API Gateway is the single external entry point for DevPath.

It is responsible for routing incoming client requests to the appropriate downstream microservice while hiding the internal architecture from external consumers.

API Gateway is registered in Eureka and uses Spring Cloud Gateway with Service Discovery for dynamic routing.

---

## Responsibilities

Current responsibilities:

* Register itself in Eureka
* Route client requests to downstream services
* Resolve service instances through Eureka
* Forward HTTP requests transparently
* Preserve request headers, including `Authorization`

Future responsibilities:

* Logging
* CORS centralization
* Rate Limiting
* Circuit Breaker
* Request filtering
* Centralized security policies

---

## Technology

* Spring Boot
* Spring Cloud Gateway
* Spring Cloud LoadBalancer
* Eureka Discovery Client

---

## Port

```text
8765
```

---

## Route Configuration

Implemented routes:

```text
/auth/**               → authentication-service
/courses/**            → learning-service
/modules/**            → learning-service
```

Routing uses Service Discovery.

Instead of using fixed URLs:

```text
http://localhost:8182
```

the Gateway resolves services dynamically through Eureka:

```text
lb://authentication-service
lb://learning-service
```

---

## Architecture

```text
                    Client
                       │
                       ▼
                API Gateway :8765
                       │
        ┌──────────────┴──────────────┐
        │                             │
        ▼                             ▼
Authentication Service          Learning Service
      /auth/**             /courses/**  /modules/**
```

---

## Service Discovery Flow

```text
Client
    │
    ▼
API Gateway
    │
    │ asks Eureka:
    │ "Where is learning-service?"
    ▼
Eureka Server
    │
    │ localhost:8081
    ▼
Learning Service
```

The Gateway never knows the physical address of the services.

It only knows their logical service names.

---

## JWT Propagation

API Gateway does not generate JWT tokens.

It simply forwards the Authorization header to downstream services.

```text
Client
    │
    │ Authorization: Bearer <JWT>
    ▼
API Gateway
    │
    │ Header propagated unchanged
    ▼
Learning Service
    │
JwtAuthenticationFilter
    │
SecurityContext
    │
@PreAuthorize
    │
Controller
```

JWT validation remains inside the Learning Service.

---

## Security Responsibilities

Authentication Service

* User authentication
* JWT generation

API Gateway

* Request routing
* Header forwarding

Learning Service

* JWT validation
* Authorization
* Method Security

This separation keeps responsibilities isolated and makes the architecture easier to evolve.

---

## Verified

Verified through Postman:

Authentication

* User registration through Gateway
* User login through Gateway
* JWT generation

Learning Service

* Course endpoints
* Module endpoints

Security

* JWT propagation
* Unauthorized requests
* USER permissions
* ADMIN permissions
* Role-based authorization

Infrastructure

* Eureka Service Discovery
* Dynamic routing through `lb://`
* End-to-end communication

---

## Current Status

```text
API GATEWAY ROUTING COMPLETED
```

Ready for:

* Global Filters
* Logging
* Rate Limiting
* Circuit Breaker
* Centralized Gateway Security
