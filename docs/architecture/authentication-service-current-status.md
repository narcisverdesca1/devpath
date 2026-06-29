# Authentication Service - Current Status

## Overview

Authentication Service is responsible for user authentication and JWT issuing within DevPath.

Current implementation includes:

* User domain model
* Role management
* User registration
* User login
* BCrypt password hashing
* JWT authentication
* JWT generation
* Spring Security integration
* Global exception handling
* Request validation
* Custom UserDetails implementation
* Stateless authentication
* Role-based authorization
* Correlation ID request tracing

Authentication Service is the only service responsible for validating user credentials.

Other services, such as Learning Service, trust JWT tokens issued by Authentication Service and validate them locally.

---

## Domain Model

### User

Fields:

* id (UUID)
* firstName
* lastName
* email
* password
* role
* enabled
* createdAt
* updatedAt

Validation:

* firstName required
* lastName required
* email required
* email format validation
* password required

### Role

Implemented roles:

* USER
* ADMIN

---

## Persistence

Database:

```text
devpath_auth
```

Generated tables:

```text
users
```

---

## Security Architecture

Authentication is implemented using Spring Security and JWT.

Components:

* SecurityConfig
* JwtService
* JwtAuthenticationFilter
* CustomUserDetailsService
* UserDetailsImpl
* AuthenticationManager
* PasswordEncoder (BCrypt)
* CorrelationIdLoggingFilter

Responsibilities:

* Authenticate users with email and password
* Validate credentials through Spring Security
* Hash passwords with BCrypt
* Generate signed JWT tokens
* Include user identity and role inside JWT claims
* Protect internal authentication-service endpoints when required
* Populate SecurityContext for protected authentication-service endpoints

---

## JWT Issuer Responsibility

Authentication Service acts as the JWT issuer for DevPath.

It generates JWT tokens consumed by:

* Learning Service

Future consumers:

* API Gateway
* Note Service
* Other DevPath domain services

JWT tokens are transported through the standard HTTP header:

```http
Authorization: Bearer <token>
```

JWT claims:

* sub → user email
* role → user role
* iat → issued at
* exp → expiration date

Authentication Service signs the JWT using the configured secret key.

The secret key is never sent to clients.

---

## Registration Flow

```text
POST /auth/register
        ↓
DTO Validation
        ↓
Email uniqueness check
        ↓
BCrypt password hashing
        ↓
User persistence
        ↓
Response DTO
```

---

## Login Flow

```text
POST /auth/login
        ↓
AuthenticationManager
        ↓
CustomUserDetailsService
        ↓
BCrypt password verification
        ↓
JWT generation
        ↓
Response DTO with access token
```

---

## JWT Flow

```text
Client
        │
        │ POST /auth/login
        ▼
Authentication Service
        │
        │ Generates signed JWT
        ▼
Client
        │
        │ Authorization: Bearer <token>
        ▼
Protected DevPath Service
        │
        │ Validates JWT locally
        ▼
Protected endpoint access
```

---

## Currelation ID Propagation
Authentication Service participates in the distributed request tracing strategy implemented by DevPath.

The service does not generate Correlation IDs.

Instead, it receives the `X-Correlation-Id` header from the API Gateway and uses it to log incoming requests and outgoing responses.

This allows a single client request to be traced across multiple microservices.

```text
Client
    │
    ▼
API Gateway
    │
X-Correlation-Id
    │
    ▼
Authentication Service
    │
CorrelationIdLoggingFilter
    │
    ▼
Application Logs
```

---

## Integration With Learning Service

Learning Service does not authenticate users with email and password.

Instead:

* Authentication Service verifies credentials.
* Authentication Service issues a signed JWT.
* Client sends the JWT to Learning Service.
* Learning Service validates the JWT signature and expiration.
* Learning Service extracts email and role.
* Learning Service applies authorization using Spring Security.

This keeps authentication centralized and allows Learning Service to remain stateless.

---

## Implemented Endpoints

```http
POST /auth/register
POST /auth/login
GET  /auth/me
```

---

## Exception Handling

Implemented exceptions:

* EmailAlreadyExistsException
* InvalidCredentialsException

Handled by:

* GlobalExceptionHandler

HTTP responses:

* 400 Bad Request
* 401 Unauthorized
* 409 Conflict

---

## Verification Completed

Verified successfully:

* User registration
* Email uniqueness validation
* BCrypt password hashing
* User persistence
* User login
* JWT generation
* JWT validation
* Protected endpoint authentication
* SecurityContext population
* Stateless authentication
* Role claim generation
* JWT usage by Learning Service
* Correlation ID propagation
* Request logging
* Response logging
* End-to-end request tracing through API Gateway


---

## Future Improvements

* JWT unit tests
* Controller tests
* Refresh tokens
* Secret externalization through environment variables
* Token revocation strategy
* Asymmetric JWT signing with private/public key pair

---

## Current Status

Authentication Service is operational and integrated with Spring Security.

Implemented features:

* User Registration
* User Login
* BCrypt Password Hashing
* JWT Authentication
* JWT Generation
* Spring Security Integration
* Stateless Security
* Role-based Authorization
* Global Exception Handling
* Validation Layer
* JWT Issuer for Learning Service
* Correlation ID Logging
* Request Tracing

Status:

```text
AUTHENTICATION SERVICE COMPLETED
```

Ready for:

* Authentication Tests
* Refresh Token Strategy

## Architecture Decisions

Current architectural decisions:

* Authentication Service is the only JWT issuer in the platform.
* User credentials are validated only by Authentication Service.
* Correlation IDs are received from the API Gateway and never generated locally.
* Authentication remains centralized while resource services remain stateless.
