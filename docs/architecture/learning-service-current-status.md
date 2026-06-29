# Learning Service - Current Status

## Overview

Learning Service is responsible for managing learning content within DevPath.

Current implementation includes:

* Course domain model
* Module domain model
* Course CRUD operations
* Module CRUD operations
* PostgreSQL persistence
* Spring Data JPA integration
* Course → Module relationship
* Global exception handling
* Request validation with Bean Validation
* DTO Pattern
* MapStruct integration
* Request/Response mapping layer
* OpenAPI documentation
* Swagger UI integration
* Service unit tests
* Repository integration tests with Testcontainers
* Controller tests with MockMvc
* Spring Security integration
* JWT validation
* Stateless authentication
* Role-based authorization
* Method security with @PreAuthorize
* Correlation ID request tracing

---

## Security

Learning Service is protected using stateless JWT-based security.

Learning Service does not generate JWT tokens and does not authenticate users through email and password.

JWT tokens are issued by Authentication Service and sent to Learning Service through the HTTP Authorization header.

```http
Authorization: Bearer <token>
```

Learning Service validates the JWT locally and uses the role claim to authorize access to protected endpoints.

### Security Components

* SecurityConfig
* JwtAuthenticationFilter
* JwtService
* JwtServiceImpl
* CorrelationIdLoggingFilter

### Responsibilities

* Read the Authorization header
* Extract the Bearer token
* Validate JWT signature
* Validate JWT expiration
* Extract username/email from the token subject
* Extract user role from the token claims
* Convert role into Spring Security authority
* Populate the Spring SecurityContext
* Support method-level authorization with @PreAuthorize

### Authorization Rules

```text
GET    endpoints    USER or ADMIN
POST   endpoints    ADMIN only
PUT    endpoints    ADMIN only
DELETE endpoints    ADMIN only
```

### Role Mapping

JWT role claim:

```text
USER
ADMIN
```

Spring Security authorities:

```text
ROLE_USER
ROLE_ADMIN
```

---

## Integration With Authentication Service

Learning Service trusts JWT tokens issued by Authentication Service.

Authentication is delegated to Authentication Service.

Authorization is performed locally inside Learning Service using Spring Security.

Learning Service does not:

* store user credentials
* validate passwords
* query the authentication database
* generate JWT tokens

Learning Service does:

* receive JWT tokens through the Authorization header
* validate JWT signature and expiration
* extract email and role
* populate SecurityContext
* enforce role-based rules with @PreAuthorize

---

## Correlation ID Propagation

Learning Service participates in DevPath's distributed request tracing strategy.

The service does not generate Correlation IDs.

Instead, it receives the `X-Correlation-Id` header propagated by the API Gateway and uses it to log incoming requests and outgoing responses.

This allows requests to be traced consistently across the Gateway and downstream services.

```text
Client
    │
    ▼
API Gateway
    │
X-Correlation-Id
    │
    ▼
Learning Service
    │
CorrelationIdLoggingFilter
    │
    ▼
Application Logs
```



## ## API Gateway Integration

Learning Service is accessed through the API Gateway.

The Gateway forwards both the `Authorization` header and the
`X-Correlation-Id` header without modifying them.

Learning Service validates JWT tokens locally while using the propagated
Correlation ID for request tracing.

Current direct access:

```text
Client
        │
        │ Authorization: Bearer <token>
        ▼
Learning Service
```

Target access through API Gateway:

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
```

Learning Service will continue validating JWT tokens locally after receiving requests from the gateway.

---

## Implemented Endpoints

### Course Management

```http
POST   /courses                 ADMIN
GET    /courses                 USER | ADMIN
GET    /courses/{id}            USER | ADMIN
PUT    /courses/{id}            ADMIN
DELETE /courses/{id}            ADMIN
```

### Module Management

```http
POST   /courses/{courseId}/modules   ADMIN
GET    /courses/{courseId}/modules   USER | ADMIN
GET    /modules/{id}                 USER | ADMIN
PUT    /modules/{id}                 ADMIN
DELETE /modules/{id}                 ADMIN
```

---

## Security Verification

Verified successfully:

* Requests without JWT are rejected
* Requests with valid JWT are authenticated
* JWT signature validation
* JWT expiration validation
* JwtAuthenticationFilter execution
* SecurityContext population
* Role extraction from JWT
* USER authorization
* ADMIN authorization
* USER access to GET endpoints
* USER denial on POST, PUT and DELETE endpoints
* ADMIN access to GET, POST, PUT and DELETE endpoints
* Method security with @PreAuthorize
* JWT issued by Authentication Service is accepted by Learning Service
* Authorization header propagation verified manually with Postman
* Correlation ID propagation
* Request logging
* Response logging
* End-to-end request tracing

---

## Future Improvements

* Pagination and sorting
* Search capabilities
* Advanced filtering
* Test coverage reporting
* CI pipeline test execution

---

## Current Status

Learning Service is fully operational, tested, documented, registered in Eureka, and protected with JWT-based security.

Implemented features:

* Course CRUD
* Module CRUD
* Course → Module relationship
* PostgreSQL persistence
* JPA/Hibernate integration
* Global Exception Handling
* Request Validation
* DTO Pattern
* MapStruct Mapping Layer
* Request/Response DTOs
* OpenAPI Documentation
* Swagger UI Integration
* Service Unit Testing
* Repository Integration Testing
* Controller Testing
* Testcontainers Integration
* MockMvc Testing
* Spring Security
* JWT Validation
* Stateless Authentication
* JwtAuthenticationFilter
* Method Security
* Role-based Authorization
* JWT validation of tokens issued by Authentication Service
* Correlation ID Logging
* Request Tracing

Status:

```text
LEARNING SERVICE COMPLETED
```

Ready for:

Pagination and Sorting
Search Capabilities
Note Service


## Architecture Decisions

Current architectural decisions:

* Learning Service acts as a JWT Resource Server.
* User authentication is delegated to Authentication Service.
* JWT validation is performed locally.
* Correlation IDs are propagated by the API Gateway and never generated locally.
* Authorization is enforced using Spring Security and `@PreAuthorize`.