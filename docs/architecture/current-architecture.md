# Current Architecture

## Infrastructure

PostgreSQL 17 (Docker)

Databases:

* devpath_learning
* devpath_note

## Services

### Naming Server

Port: 8761

Technology:

* Spring Boot
* Eureka Server

### API Gateway

Port: 8765

Technology:

* Spring Cloud Gateway
* Eureka Discovery Client

### Learning Service

Port: 8081

Technology:

* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Eureka Discovery Client

Database:

* devpath_learning

Status:

* Registered in Eureka
* Connected to PostgreSQL
* Actuator enabled

## Current Topology

```
            Eureka Server
            localhost:8761
                   ▲
                   │
    ┌──────────────┴──────────────┐
    │                             │
```

API Gateway                 Learning Service
localhost:8765              localhost:8081

```
            PostgreSQL
            localhost:5432
             ├─ devpath_learning
             └─ devpath_note
```
