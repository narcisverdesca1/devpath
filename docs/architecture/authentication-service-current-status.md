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

---

## Future Improvements

* JWT unit tests
* Controller tests
* Refresh tokens
* API Gateway JWT propagation
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

Status:

```text
JWT AUTHENTICATION COMPLETED
```

Ready for:

* API Gateway Integration
* JWT propagation through API Gateway
* Authentication Tests
* Refresh Token Strategy
