# ADR-008 - JWT Resource Server Security

## Status

Accepted

## Context

DevPath uses a microservices architecture where Authentication Service
is responsible for user authentication and JWT generation, while
Learning Service is responsible for protecting business resources.

Authentication Service authenticates users and issues signed JWT tokens.

Learning Service must not authenticate users using credentials. Instead,
it validates JWT tokens received in the `Authorization` header and
authorizes access based on the role contained in the token.

This separation keeps authentication centralized while allowing each
resource service to remain stateless.

------------------------------------------------------------------------

## Decision

Learning Service will act as a JWT Resource Server.

Responsibilities:

-   Validate JWT signature.
-   Validate JWT expiration.
-   Extract authenticated user information.
-   Extract user role.
-   Populate the Spring Security `SecurityContext`.
-   Apply authorization rules using `@PreAuthorize`.

Authentication Service remains the only component responsible for:

-   User registration
-   User login
-   Password verification
-   JWT generation

Learning Service never stores or validates user passwords.

------------------------------------------------------------------------

## Security Flow

``` text
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
Learning Service
    │
    ├── JwtAuthenticationFilter
    ├── JwtService
    ├── SecurityContext
    └── @PreAuthorize
    ▼
Protected Endpoint
```

------------------------------------------------------------------------

## Authorization Rules

``` text
GET    courses/modules    USER or ADMIN
POST   courses/modules    ADMIN only
PUT    courses/modules    ADMIN only
DELETE courses/modules    ADMIN only
```

------------------------------------------------------------------------

## Implemented Components

### Security

-   SecurityConfig
-   JwtAuthenticationFilter
-   JwtService
-   JwtServiceImpl

### Authorization

-   Spring Security Method Security
-   `@EnableMethodSecurity`
-   `@PreAuthorize`

------------------------------------------------------------------------

## Consequences

### Benefits

-   Stateless authorization
-   Centralized authentication
-   Resource services remain independent
-   Simple integration with API Gateway
-   Clear separation of responsibilities
-   Standard Spring Security architecture

### Trade-offs

-   Authentication Service and Learning Service currently share the same
    JWT secret.
-   Secret management should be externalized before production.
-   Token revocation is not currently implemented.

------------------------------------------------------------------------

## Future Improvements

-   API Gateway as the single entry point.
-   Secret externalization.
-   Refresh tokens.
-   Token revocation strategy.
-   Asymmetric JWT signing (public/private keys).

------------------------------------------------------------------------

## Verification

Verified successfully:

-   JWT validation
-   Stateless authentication
-   SecurityContext population
-   Role extraction
-   USER authorization
-   ADMIN authorization
-   `@PreAuthorize` enforcement
-   Unauthorized request rejection
