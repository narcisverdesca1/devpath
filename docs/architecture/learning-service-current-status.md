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

### Module

Fields:

* id
* title
* description
* position

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

Implemented components:

### ResourceNotFoundException

Used when a requested resource cannot be found.

Example:

```text
throw new ResourceNotFoundException(
        "Course not found with id: " + id
);
```

### ApiError

Standard response model for API errors.

Fields:

* timestamp
* status
* error
* message

### GlobalExceptionHandler

Responsible for converting application exceptions into HTTP responses.

Example mapping:

```text
@ExceptionHandler(ResourceNotFoundException.class)
```

Returns:

```http
404 Not Found
```

Example response:

```json
{
  "timestamp": "2026-06-19T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Course not found with id: 999"
}
```

Benefits:

* Consistent API responses
* Centralized error handling
* Proper HTTP status codes
* Improved API consumer experience

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
* 404 responses for missing resources

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

The bidirectional relationship initially caused an infinite JSON serialization loop:

```text
Course
└── modules
    └── course
        └── modules
            └── course
                ...
```

The issue was solved by applying:

```text
@JsonIgnore
```

on the `course` field inside the Module entity:

```text
@JsonIgnore
private Course course;
```

### Centralized Exception Handling

Instead of returning generic server errors, the application now throws domain-specific exceptions that are translated into appropriate HTTP responses by a global exception handler.

Benefits:

* Better separation of concerns
* Cleaner service layer
* Consistent REST API behavior
* Easier future extension for validation and business exceptions

### Future Improvements

* DTO Pattern
* Dedicated API response models
* Validation with Bean Validation
* Swagger / OpenAPI documentation

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

Status:

```text
GLOBAL EXCEPTION HANDLING COMPLETED
```

Ready for:

* Validation Layer
* DTO Introduction
* API Documentation
