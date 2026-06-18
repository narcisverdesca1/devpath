# ADR-003 - Learning Service

## Status

Accepted

## Context

DevPath necessita di un primo microservizio di dominio per gestire i contenuti formativi.

Dopo aver configurato:

* Eureka Naming Server
* API Gateway
* PostgreSQL

è necessario introdurre il primo servizio business.

## Decision

Viene creato il microservizio:

learning-service

Responsabilità:

* Gestione corsi
* Gestione moduli
* Persistenza dei dati formativi

Tecnologie:

* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Eureka Discovery Client
* Actuator
* DevTools

Database:

devpath_learning

Porta:

8081

## Consequences

Benefici:

* Primo servizio business completo
* Validazione integrazione Eureka + PostgreSQL
* Base per future funzionalità della piattaforma

Svantaggi:

* Necessità di gestire relazioni Course/Module
* Introduzione di migrazioni database future
