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

Persistence was verified through PostgreSQL inspection.

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

---

## Implemented Endpoints

### Course Management

```http
POST   /courses
GET    /courses
GET    /courses/{id}
PUT    /courses/{id}
DELETE /courses/{id}
```

### Module Management

```http
POST   /courses/{courseId}/modules
GET    /courses/{courseId}/modules
GET    /modules/{id}
PUT    /modules/{id}
DELETE /modules/{id}
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

---

## Future Improvements

* Integration testing
* Service layer unit testing
* Pagination and sorting
* Search capabilities

---

## Current Status

Learning Service is fully operational and registered in Eureka.

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

Status:

```text
OPENAPI DOCUMENTATION COMPLETED
```

Ready for:

* Integration Testing
* Unit Testing
* Pagination and Sorting
* Search Capabilities
* Note Service
