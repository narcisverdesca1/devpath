# ADR-002 - Microservices First Architecture

## Status

Accepted

## Context

DevPath nasce come progetto portfolio e laboratorio pratico per consolidare Spring Boot Microservices e Spring Cloud.

L'obiettivo non è solo creare una semplice applicazione CRUD, ma costruire progressivamente un sistema distribuito con servizi indipendenti.

## Decision

DevPath partirà direttamente con un'architettura a microservizi.

La struttura iniziale del backend sarà:

- naming-server
- api-gateway
- learning-service
- note-service

Per mantenere la complessità sotto controllo, l'MVP includerà solo:

- Course
- Module
- Note

## Service Responsibilities

### naming-server

Gestisce la service discovery tramite Eureka.

### api-gateway

Rappresenta il punto di ingresso unico per le API.

In questa fase non gestisce ancora autenticazione JWT.

### learning-service

Gestisce il dominio legato ai percorsi di apprendimento:

- Course
- Module

### note-service

Gestisce il dominio legato agli appunti:

- Note

Le note faranno riferimento ai moduli tramite `moduleId`.

## Consequences

### Positive

- Il progetto è coerente con l'obiettivo di studio sui microservizi.
- Permette di consolidare Eureka, API Gateway e comunicazione tra servizi.
- La separazione tra learning-service e note-service è già allineata al dominio.
- Il progetto risulta più interessante come portfolio.

### Negative

- Maggiore complessità iniziale rispetto a un monolite.
- Più configurazione da gestire.
- Debugging più articolato.
- Docker Compose diventerà necessario abbastanza presto.

## Complexity Control

Per evitare overengineering, nella prima fase sono esclusi:

- JWT
- auth-service
- Kubernetes
- tag
- resource links
- concept relations
- problem solutions
- frontend
- CI/CD

Questi elementi potranno essere aggiunti solo dopo aver completato e stabilizzato l'MVP.