# ADR-009 - Correlation ID and Distributed Request Tracing

## Status

Accepted

## Context

DevPath is a distributed microservices application composed of multiple independent services communicating through the API Gateway.

A single client request may traverse multiple services before a response is returned.

Without a shared request identifier, troubleshooting, debugging and log analysis become increasingly difficult as the architecture grows.

The platform requires a lightweight mechanism capable of tracing a single request across all participating services without introducing tight coupling between them.

---

## Decision

DevPath adopts a Correlation ID based request tracing strategy.

The API Gateway is the single component responsible for generating a Correlation ID whenever an incoming request does not already provide one.

The Correlation ID is propagated to downstream services using the standard HTTP header:

```http
X-Correlation-Id
```

All participating services preserve the received Correlation ID and include it in request and response logs.

Downstream services never generate new Correlation IDs.

---

## Request Tracing Flow

```text
                 Client
                    │
                    ▼
              API Gateway
      CorrelationIdGlobalFilter
                    │
     Generates Correlation ID
        (if not already present)
                    │
                    ▼
      X-Correlation-Id propagated
                    │
        ┌───────────┴───────────┐
        ▼                       ▼
Authentication Service   Learning Service
CorrelationIdLogging     CorrelationIdLogging
       Filter                  Filter
        │                       │
        ▼                       ▼
      Logs                    Logs
```

---

## Responsibilities

### API Gateway

* Generate a Correlation ID when missing.
* Preserve existing Correlation IDs.
* Propagate the Correlation ID to downstream services.
* Include the Correlation ID in response headers.
* Log incoming requests.
* Log outgoing responses.

### Downstream Services

* Receive the propagated Correlation ID.
* Preserve the received identifier.
* Log incoming requests.
* Log outgoing responses.

---

## Consequences

### Benefits

* End-to-end request tracing.
* Easier debugging across multiple services.
* Improved operational visibility.
* No coupling between business services.
* Standardized request identification.
* Foundation for future observability improvements.

### Trade-offs

* Correlation IDs identify requests, not users or sessions.
* Log aggregation is still decentralized.
* Full distributed tracing (OpenTelemetry / Zipkin) is not yet implemented.

---

## Future Improvements

* Centralized log aggregation.
* Structured JSON logging.
* MDC (Mapped Diagnostic Context) integration.
* OpenTelemetry integration.
* Zipkin distributed tracing.
* Metrics and monitoring dashboards.

---

## Verification

Verified successfully:

* Correlation ID generation by API Gateway.
* Correlation ID propagation through downstream services.
* Response header propagation.
* Gateway request logging.
* Gateway response logging.
* Authentication Service request tracing.
* Learning Service request tracing.
* End-to-end request trace verification using Postman.