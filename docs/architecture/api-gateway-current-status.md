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
* Forward Authorization headers
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
/notes/**              → note-service
```

Routing uses Service Discovery.

Instead of using fixed URLs:

```text
http://localhost:8081
http://localhost:8082
http://localhost:8083
```

the Gateway resolves services dynamically through Eureka:

```text
lb://authentication-service
lb://learning-service
lb://note-service
```

---

## Architecture

```text
                           Client
                              │
                              ▼
                     API Gateway :8765
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
Authentication Service   Learning Service      Note Service
      /auth/**      /courses/** /modules/**      /notes/**
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
    │ "Where is note-service?"
    ▼
Eureka Server
    │
    │ localhost:8083
    ▼
Note Service
```

The Gateway never knows the physical address of the services.

It only knows their logical service names.

---

## JWT Propagation

API Gateway does not generate or validate JWT tokens.

It simply forwards the `Authorization` header to downstream services.

```text
Client
    │
    │ Authorization: Bearer <JWT>
    ▼
API Gateway
    │
    │ Authorization header forwarded
    ▼
Note Service
    │
    │ OpenFeign RequestInterceptor
    ▼
Learning Service
```

JWT validation remains the responsibility of each microservice, allowing every service to remain autonomous and independently secure.

---

## Correlation ID Propagation

API Gateway is responsible for generating and propagating Correlation IDs across the distributed system.

If an incoming request already contains an `X-Correlation-Id` header, the Gateway preserves it.

Otherwise, the Gateway generates a new UUID and propagates it unchanged to every downstream service.

The same identifier is returned to the client through the response headers.

```text
Client
   │
   │ GET /notes
   ▼
API Gateway
   │
   │ X-Correlation-Id generated if absent
   ▼
Note Service
   │
   ▼
Learning Service
```

---

## Security Responsibilities

### Authentication Service

* User authentication
* JWT generation

### API Gateway

* Request routing
* Authorization header forwarding
* Correlation ID generation
* Correlation ID propagation

### Learning Service

* JWT validation
* Authorization
* Method Security

### Note Service

* JWT validation
* Authorization
* Method Security
* Secure communication with Learning Service through OpenFeign

This separation keeps responsibilities isolated and allows every microservice to enforce its own security independently.

---

## Gateway Global Filters

Implemented Global Filter:

**CorrelationIdGlobalFilter**

Responsibilities:

* Generate Correlation IDs
* Propagate Correlation IDs
* Add Correlation ID to responses
* Log incoming requests
* Log outgoing responses

---

## Verified

Verified through Postman:

### Authentication

* User registration through Gateway
* User login through Gateway
* JWT generation

### Learning Service

* Course endpoints
* Module endpoints

### Note Service

* Note CRUD endpoints
* Module validation through OpenFeign
* JWT propagation through RequestInterceptor

### Security

* JWT propagation
* Unauthorized requests
* USER permissions
* ADMIN permissions
* Role-based authorization

### Infrastructure

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

* CORS centralization
* Rate Limiting
* Circuit Breaker
* Centralized Gateway Security

---

## Architectural Decisions

Current architectural decisions:

* API Gateway remains stateless.
* JWT validation is delegated to downstream services.
* Authorization headers are transparently forwarded.
* Correlation IDs are generated only at the Gateway.
* Downstream services never generate new Correlation IDs.
* Service discovery relies exclusively on Eureka (`lb://`).
* Every request is traceable across the distributed architecture.
