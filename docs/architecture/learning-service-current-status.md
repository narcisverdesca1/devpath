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

---

## Security

Learning Service is protected using stateless JWT-based security.

Learning Service does not generate JWT tokens and does not authenticate users through email and password.

JWT tokens are issued by Authentication Service and sent to Learning Service through the HTTP Authorization header.

```http
Authorization: Bearer <token>
```

### Security Components

* SecurityConfig
* JwtAuthenticationFilter
* JwtService
* JwtServiceImpl

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

## Testing

Testing has been implemented across the main application layers.

### Service Tests

Implemented using:

* JUnit 5
* Mockito
* AssertJ

Covered classes:

* CourseServiceTest
* ModuleServiceTest

Covered scenarios:

* create operations
* find all operations
* find by id operations
* update operations
* delete operations
* resource not found cases
* mapper interaction
* repository interaction

### Repository Integration Tests

Implemented using:

* Spring Boot Test
* Testcontainers
* PostgreSQL
* JPA repositories

Covered classes:

* CourseRepositoryIT
* ModuleRepositoryIT

Covered scenarios:

* entity persistence
* entity retrieval
* custom query methods
* Course → Module relationship
* PostgreSQL integration

### Controller Tests

Implemented using:

* WebMvcTest
* MockMvc
* MockitoBean
* ObjectMapper

Covered classes:

* CourseControllerTest
* ModuleControllerTest

Covered scenarios:

* HTTP status codes
* JSON response structure
* request validation errors
* create endpoints
* read endpoints
* update endpoints
* delete endpoints
* service interaction

---

## Domain Model

### Course

Fields:

* id
* title
* description
* difficulty
* createdAt
* updatedAt

Validation:

* title required
* title max 100 characters
* description max 1000 characters
* difficulty required
* difficulty max 50 characters

### Module

Fields:

* id
* title
* description
* position

Validation:

* title required
* title max 100 characters
* description max 1000 characters
* position required
* position >= 0

---

## Relationships

### Course (1) → (N) Module

The Learning Service currently models a one-to-many relationship between Course and Module.

### Course Mapping

```text
@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Module> modules;
```

### Module Mapping

```text
@ManyToOne
@JoinColumn(name = "course_id")
@JsonIgnore
private Course course;
```

Generated foreign key:

```text
module.course_id -> course.id
```

---

## Persistence

Database:

```text
devpath_learning
```

Hibernate configuration:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Generated tables:

```text
course
module
```

Persistence was verified through PostgreSQL inspection and repository integration tests.

---

## Implemented Layers

### Repository

* CourseRepository
* ModuleRepository

### Service

* CourseService
* ModuleService

### Controller

* CourseController
* ModuleController

### DTO

#### Request DTOs

* CourseRequestDto
* ModuleRequestDto

#### Response DTOs

* CourseResponseDto
* ModuleResponseDto

### Mapper

Implemented using MapStruct.

#### Request Mappers

* CourseRequestMapper
* ModuleRequestMapper

Responsibilities:

* Request DTO → Entity conversion
* Entity update through @MappingTarget

#### Response Mappers

* CourseResponseMapper
* ModuleResponseMapper

Responsibilities:

* Entity → Response DTO conversion
* Collection mapping support

### Exception Handling

* ResourceNotFoundException
* ApiError
* GlobalExceptionHandler

### Security

* SecurityConfig
* JwtAuthenticationFilter
* JwtService
* JwtServiceImpl

Responsibilities:

* JWT validation
* Stateless authentication
* SecurityContext population
* Role extraction
* Method authorization support

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

## OpenAPI Documentation

API documentation is implemented using SpringDoc OpenAPI.

Configuration:

* OpenApiConfig
* Swagger UI
* OpenAPI 3 specification generation

Documentation annotations currently used:

* @Tag
* @Operation
* @ApiResponse
* @ApiResponses
* @Schema

Documented Components:

### Controllers

* CourseController
* ModuleController

### DTOs

* CourseRequestDto
* CourseResponseDto
* ModuleRequestDto
* ModuleResponseDto

### Error Responses

* 400 Bad Request
* 404 Not Found

Benefits:

* Self-documenting APIs
* Interactive API exploration
* Faster frontend integration
* Reduced documentation drift
* Consistent API contracts

---

## Global Exception Handling

A centralized exception handling mechanism has been implemented using:

```text
@RestControllerAdvice
```

Implemented handlers:

* ResourceNotFoundException → 404 Not Found
* MethodArgumentNotValidException → 400 Bad Request

Standard error response model:

```json
{
  "timestamp": "2026-06-19T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Title is required"
}
```

---

## Request Validation

Validation is implemented using Jakarta Bean Validation.

Annotations currently used:

* @Valid
* @NotBlank
* @Size
* @NotNull
* @PositiveOrZero

---

## DTO Pattern

The application no longer exposes JPA entities directly through REST APIs.

REST endpoints communicate through dedicated request and response DTOs.

Benefits:

* Clear separation between persistence and API contracts
* Safer API evolution
* Reduced coupling
* Improved maintainability

---

## MapStruct Integration

Object mapping is handled through MapStruct.

Benefits:

* Reduced boilerplate code
* Centralized mapping logic
* Compile-time mapper generation
* Cleaner service layer

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

---

## Verification Completed

Verified successfully:

* Spring Boot startup
* Eureka registration
* PostgreSQL connectivity
* Hibernate schema generation
* Repository layer
* Service layer
* REST controller layer
* Postman testing
* Course CRUD operations
* Module CRUD operations
* Course → Module relationship
* Foreign key generation
* Relationship persistence in PostgreSQL
* Global exception handling
* Request validation
* DTO request validation flow
* DTO response mapping
* MapStruct integration
* Entity isolation from REST APIs
* Swagger UI accessibility
* OpenAPI specification generation
* Controller documentation rendering
* DTO schema rendering
* Error response documentation
* 404 responses for missing resources
* 400 responses for invalid requests
* Service unit tests
* Repository integration tests
* Controller tests
* Testcontainers PostgreSQL testing
* MockMvc REST API testing
* JWT validation
* JwtAuthenticationFilter
* SecurityContext population
* Role-based authorization
* Method security
* USER authorization verified
* ADMIN authorization verified
* Unauthorized access verified

---

## Future Improvements

* Pagination and sorting
* Search capabilities
* Advanced filtering
* Test coverage reporting
* CI pipeline test execution
* API Gateway integration as single entry point

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

Status:

```text
LEARNING SERVICE SECURITY COMPLETED
```

Ready for:

* API Gateway integration
* Pagination and Sorting
* Search Capabilities
* Note Service
