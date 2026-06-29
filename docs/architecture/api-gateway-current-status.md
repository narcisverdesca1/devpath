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
* Preserve request headers
* Generate Correlation IDs
* Propagate Correlation IDs
* Log incoming requests
* Log outgoing responses

Future responsibilities:

* CORS centralization
* Rate Limiting
* Circuit Breaker
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

## Correlation ID Propagation
API Gateway is responsible for generating and propagating
Correlation IDs across all downstream services.

If an incoming request already contains an
`X-Correlation-Id` header, the Gateway preserves it.

Otherwise, the Gateway generates a new UUID and propagates
it unchanged to every downstream service.

The same identifier is also returned to the client through
the response headers.

```text

Client
   │
   │ GET /courses
   ▼
API Gateway
   │
   │ X-Correlation-Id generated if absent
   ▼
Learning Service
```


## Security Responsibilities

Authentication Service

* User authentication
* JWT generation

API Gateway

* Request routing
* Authorization header forwarding
* Correlation ID generation
* Correlation ID propagation

Learning Service

* JWT validation
* Authorization
* Method Security

This separation keeps responsibilities isolated and makes the architecture easier to evolve.

---

## Gateway Global Filters
Implemented Global Filter

CorrelationIdGlobalFilter

Responsibilities

* Generate Correlation IDs
* Propagate Correlation IDs
* Add Correlation ID to responses
* Log incoming requests
* Log outgoing responses

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
* Correlation ID generation
* Correlation ID propagation
* Gateway request logging
* Gateway response logging
* End-to-end request tracing

---

## Current Status

```text
API GATEWAY FOUNDATION COMPLETED
```

Ready for:

* CORS
* Rate Limiting
* Circuit Breaker
* Centralized Gateway Security

---

## Architectural Decisions
Current architectural decisions

* API Gateway remains stateless.
* JWT validation is delegated to downstream services.
* Correlation IDs are generated only at the Gateway.
* Downstream services never generate new Correlation IDs.
* Every request is traceable across the distributed architecture.

