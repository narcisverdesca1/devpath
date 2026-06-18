# Current Architecture

## Infrastructure

PostgreSQL 17 (Docker)

Databases:

* devpath_learning
* devpath_note

## Services

Naming Server

Port:
8761

Technology:

* Spring Boot
* Eureka Server

API Gateway

Port:
8765

Technology:

* Spring Cloud Gateway
* Eureka Discovery Client

## Current Topology

```
            Eureka Server
            localhost:8761
                   ▲
                   │
            API Gateway
            localhost:8765

            PostgreSQL
            localhost:5432
             ├─ devpath_learning
             └─ devpath_note
```

## Planned Services

* learning-service
* note-service
* auth-service
