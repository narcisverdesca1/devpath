# ADR-005 - DTO Pattern and MapStruct Integration

## Status

Accepted

## Context

The Learning Service initially exposed JPA entities directly through REST APIs.

This approach created a tight coupling between the persistence model and the API contract.

As the project evolves, exposing entities directly increases the risk of:

* Breaking API consumers when entities change
* Accidental exposure of internal fields
* Serialization issues caused by JPA relationships
* Reduced flexibility when evolving the domain model

Additionally, manual mapping between entities and API models would introduce repetitive boilerplate code.

## Decision

The Learning Service adopts the DTO Pattern for all REST APIs.

Dedicated request and response DTOs are introduced:

* CourseRequestDto
* CourseResponseDto
* ModuleRequestDto
* ModuleResponseDto

JPA entities are no longer exposed directly through REST endpoints.

Object mapping is delegated to MapStruct.

The mapping layer is separated into:

### Request Mappers

* CourseRequestMapper
* ModuleRequestMapper

Responsibilities:

* Request DTO → Entity conversion
* Entity update through @MappingTarget

### Response Mappers

* CourseResponseMapper
* ModuleResponseMapper

Responsibilities:

* Entity → Response DTO conversion
* Collection mapping support

## Consequences

Benefits:

* Clear separation between API contracts and persistence models
* Safer API evolution
* Reduced coupling between layers
* Centralized mapping logic
* Reduced boilerplate code through MapStruct
* Improved maintainability
* Improved testability

Trade-offs:

* Additional DTO classes must be maintained
* Additional mapping layer increases project structure complexity
* MapStruct annotation processing becomes part of the build process
