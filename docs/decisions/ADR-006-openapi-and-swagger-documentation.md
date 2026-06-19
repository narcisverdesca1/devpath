# ADR-006 - OpenAPI and Swagger Documentation

## Status

Accepted

## Context

Learning Service exposes REST APIs that will be consumed by frontend applications and future DevPath microservices.

Maintaining API documentation manually would create duplication, increase maintenance effort, and introduce the risk of documentation becoming outdated.

A standardized and automated API documentation solution was required.

## Decision

SpringDoc OpenAPI has been adopted as the API documentation solution for Learning Service.

The application automatically generates:

* OpenAPI 3 specification
* Swagger UI documentation
* DTO schemas
* Validation constraint documentation
* Endpoint documentation
* Error response documentation

Documentation is generated directly from source code annotations.

Main annotations currently used:

* @Tag
* @Operation
* @ApiResponse
* @ApiResponses
* @Schema

An OpenApiConfig configuration class provides API metadata such as:

* Title
* Description
* Version
* Contact information

Swagger UI is exposed to provide an interactive interface for exploring and testing endpoints.

## Consequences

Benefits:

* Single source of truth for API documentation
* Interactive API exploration
* Easier frontend integration
* Reduced documentation drift
* Improved developer experience
* Consistent API contracts
* Automatic documentation updates when APIs evolve

Trade-offs:

* Additional dependency
* Documentation annotations must be maintained alongside code changes

## Alternatives Considered

### Manual Documentation

Rejected because it introduces duplication and requires continuous manual maintenance.

### Postman Collections Only

Rejected because collections are useful for testing but do not provide a standardized API contract.

### SpringFox Swagger

Rejected because SpringFox is no longer the preferred solution for Spring Boot 3 applications and has compatibility limitations.

## Rationale

SpringDoc provides:

* Native OpenAPI 3 support
* Full Spring Boot 3 compatibility
* Active maintenance
* Automatic schema generation
* Minimal configuration requirements

These characteristics make it the preferred solution for DevPath services.

## Applicability

This approach should be reused across future DevPath microservices to ensure:

* Consistent API documentation
* Standardized developer experience
* Faster onboarding
* Easier service integration
