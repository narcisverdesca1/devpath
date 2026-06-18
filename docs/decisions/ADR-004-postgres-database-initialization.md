# ADR-004 - PostgreSQL Database Initialization Strategy

## Status

Accepted

## Context

DevPath utilizza un'istanza PostgreSQL condivisa tra più microservizi.

Inizialmente:

* devpath_learning veniva creato tramite POSTGRES_DB
* devpath_note veniva creato tramite script SQL

Questa soluzione distribuiva la configurazione dei database in più punti.

## Decision

Tutti i database applicativi devono essere creati tramite script SQL di inizializzazione PostgreSQL.

File:

infrastructure/docker/postgres/init/01-create-databases.sql

Attualmente vengono creati:

* devpath_learning
* devpath_note

Il parametro POSTGRES_DB viene utilizzato esclusivamente per il database tecnico iniziale PostgreSQL.

## Consequences

Benefici:

* Unica fonte di verità per i database applicativi
* Maggiore facilità nell'aggiunta di nuovi microservizi
* Configurazione più chiara e centralizzata

Svantaggi:

* Necessità di mantenere gli script SQL di inizializzazione
* Gli script vengono eseguiti solo alla prima creazione del volume PostgreSQL
