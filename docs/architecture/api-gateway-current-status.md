# API Gateway - Current Status

## Overview

API Gateway is responsible for becoming the single external entry point for DevPath.

The service is already present in the architecture and registered in Eureka.

Current implementation provides the base routing layer. The next development step is to configure routes toward Authentication Service and Learning Service.

---

## Purpose

API Gateway will centralize client access to backend services.

Target client flow:

```text
Client / Frontend
        │
        ▼
API Gateway
        │
        ├── Authentication Service
        └── Learning Service
```

The client should no longer call internal services directly.

---

## Technology

* Spring Boot
* Spring Cloud Gateway
* Eureka Discovery Client

---

## Port

```text
8765
```

---

## Service Discovery

API Gateway is registered in Eureka.

Eureka Server:

```text
localhost:8761
```

Gateway:

```text
localhost:8765
```

---

## Current Status

Implemented:

* API Gateway service
* Eureka registration
* Basic routing layer availability

Not yet implemented:

* Final route configuration for Authentication Service
* Final route configuration for Learning Service
* Gateway-based client access
* Authorization header forwarding verification

---

## Target Routes

Planned routes:

```text
/auth/**               → authentication-service
/courses/**            → learning-service
/modules/**            → learning-service
/courses/*/modules/**  → learning-service
```

Alternative future route style:

```text
/api/auth/**      → authentication-service
/api/learning/**  → learning-service
```

The final route style will be decided during API Gateway implementation.

---

## JWT Propagation

The API Gateway will not generate JWT tokens.

The API Gateway will receive client requests containing:

```http
Authorization: Bearer <token>
```

The gateway must forward this header to downstream services.

Current target behavior:

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

Learning Service remains responsible for validating JWT tokens and applying role-based authorization.

---

## Responsibilities

Current responsibilities:

* Register itself in Eureka
* Provide the routing layer

Future responsibilities:

* Route authentication requests to Authentication Service
* Route learning requests to Learning Service
* Forward Authorization headers
* Hide internal service ports from external clients
* Prepare for centralized cross-cutting concerns such as logging, rate limiting, CORS and security policies

---

## Out of Scope For Now

The following topics are not part of the immediate next step:

* Centralized JWT validation inside the Gateway
* Token generation
* User registration
* User login logic
* Database access
* Business logic
* Token refresh
* Token revocation

These responsibilities remain inside Authentication Service or downstream services.

---

## Next Implementation Goal

Configure API Gateway as the single entry point.

Target architecture:

```text
Client
        │
        ▼
API Gateway
        │
        ├── /auth/**    → Authentication Service
        └── /courses/** → Learning Service
```

Main objectives:

* Configure Spring Cloud Gateway routes
* Verify routing through Eureka service names
* Test login through Gateway
* Test Learning Service protected endpoints through Gateway
* Verify that Authorization header is propagated correctly
* Keep JWT validation inside Learning Service for now

---

## Current Status Label

```text
API GATEWAY BASE SETUP COMPLETED
```

Ready for:

* Route configuration
* Authentication Service routing
* Learning Service routing
* Authorization header forwarding verification
