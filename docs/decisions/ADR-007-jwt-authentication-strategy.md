# ADR-007 - JWT Authentication Strategy

## Status

Accepted

## Context

DevPath follows a microservices-oriented architecture composed of:

* API Gateway
* Authentication Service
* Learning Service
* Future domain services

The application exposes REST APIs and uses a separate frontend.

Traditional session-based authentication would require server-side session storage and additional mechanisms to share authentication state across services.

The project requires a stateless authentication mechanism that can be propagated across multiple services using standard HTTP requests.

The Authentication Service is responsible for:

* User registration
* User authentication
* Password verification
* Token generation
* Security integration

Protected endpoints must only be accessible to authenticated users.

---

## Decision

DevPath will use JWT (JSON Web Token) based authentication integrated with Spring Security.

Authentication flow:

```text
User Login
        ↓
AuthenticationManager
        ↓
CustomUserDetailsService
        ↓
Password Verification (BCrypt)
        ↓
JWT Generation
        ↓
Access Token Returned
```

Subsequent requests must provide:

```http
Authorization: Bearer <token>
```

The JWT is validated by a custom Spring Security filter before protected endpoints are executed.

Authentication information is stored inside the JWT.

Current JWT claims:

* sub → user email
* role → user role
* iat → issued at
* exp → expiration date

The Authentication Service uses:

* Spring Security
* BCrypt PasswordEncoder
* JWT (JJWT library)
* CustomUserDetailsService
* UserDetailsImpl
* AuthenticationManager
* JwtAuthenticationFilter

Spring Security remains responsible for authentication and authorization decisions.

JWT is used only as a secure transport mechanism for authenticated user information.

Session management is configured as:

```text
STATELESS
```

No HTTP sessions are created or stored on the server.

---

## Consequences

### Benefits

* Stateless authentication
* No server-side session storage
* Suitable for REST APIs
* Suitable for microservices architecture
* Easy propagation through HTTP requests
* Spring Security integration
* Scalable authentication model
* Standard industry approach

### Trade-Offs

* Access tokens remain valid until expiration
* Token revocation is not immediate
* Secret management must be handled securely
* Additional mechanisms may be required for refresh tokens

---

## Implemented Components

### Security

* SecurityConfig
* AuthenticationManager
* BCrypt PasswordEncoder

### JWT

* JwtService
* JwtServiceImpl
* JwtAuthenticationFilter

### User Authentication

* CustomUserDetailsService
* UserDetailsImpl

### Domain

* User
* Role

### Exception Handling

* EmailAlreadyExistsException
* InvalidCredentialsException
* GlobalExceptionHandler

---

## Authentication Flow

### Registration

```text
POST /auth/register
        ↓
DTO Validation
        ↓
Email Uniqueness Check
        ↓
BCrypt Password Hashing
        ↓
User Persistence
```

### Login

```text
POST /auth/login
        ↓
AuthenticationManager
        ↓
CustomUserDetailsService
        ↓
BCrypt Verification
        ↓
JWT Generation
        ↓
LoginResponseDto
```

### Protected Request

```text
Request
        ↓
Authorization Header
        ↓
JwtAuthenticationFilter
        ↓
JWT Validation
        ↓
SecurityContext Population
        ↓
Protected Endpoint Access
```

---

## Verification

Verified successfully:

* User registration
* Email uniqueness validation
* BCrypt password hashing
* User login
* JWT generation
* JWT validation
* Protected endpoint authentication
* SecurityContext population
* Stateless authentication

---

## Future Improvements

* JWT unit tests
* Authentication controller tests
* Refresh token support
* Role-based authorization
* API Gateway JWT propagation
* Secret externalization through environment variables
* Token revocation strategy

---

## Notes

JWT is used to transport authenticated user information between requests.

Authentication and authorization decisions are performed by Spring Security.

JWT acts as a signed and verifiable container for user identity and role information.
