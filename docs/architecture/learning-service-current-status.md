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

Benefits:

* Consistent API responses
* Centralized error handling
* Proper HTTP status codes
* Improved API consumer experience

---

## Request Validation

Validation is implemented using Jakarta Bean Validation.

Annotations currently used:

* @Valid
* @NotBlank
* @Size
* @NotNull
* @PositiveOrZero

Validation is enforced on:

* Course creation
* Course update
* Module creation
* Module update

Invalid requests return:

```http
400 Bad Request
```

---

## DTO Pattern

The application no longer exposes JPA entities directly through REST APIs.

REST endpoints now communicate through dedicated request and response DTOs.

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
* 404 responses for missing resources
* 400 responses for invalid requests

---

## Lessons Learned

### Bidirectional JPA Relationships

The Course ↔ Module relationship was implemented using:

```text
@OneToMany
@ManyToOne
```

This allows a Course to contain multiple Modules while each Module belongs to one Course.

### JSON Serialization Issue

The bidirectional relationship initially caused an infinite JSON serialization loop.

The issue was solved by applying:

```text
@JsonIgnore
```

on the `course` field inside the Module entity.

### Centralized Exception Handling

Instead of returning generic server errors, the application now throws domain-specific exceptions that are translated into appropriate HTTP responses by a global exception handler.

### Bean Validation

Validation rules are declared directly on DTO fields using Jakarta Validation annotations.

Benefits:

* Cleaner controllers
* Consistent validation rules
* Automatic request validation
* Standardized 400 responses

### DTO Pattern

Dedicated DTOs provide a stable API contract independent from the persistence model.

### MapStruct

Compile-time generated mappers reduce boilerplate and improve maintainability.

### Future Improvements

* Swagger / OpenAPI documentation
* Integration testing
* Service layer unit testing

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

Status:

```text
DTO PATTERN COMPLETED
```

Ready for:

* Swagger / OpenAPI Documentation
* Integration Testing
* Unit Testing
