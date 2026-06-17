# DevPath - MVP Use Cases

## Scopo

Questo documento descrive i casi d'uso previsti per la prima versione di DevPath.

L'obiettivo dell'MVP è consentire l'organizzazione dello studio attraverso corsi, moduli e note, mantenendo il sistema semplice e facilmente estendibile.

---

# Course Management

## UC-001 - Create Course

Come utente voglio creare un corso per organizzare un nuovo percorso di studio.

### Dati richiesti

* titolo
* descrizione

### Risultato

Il corso viene salvato nel sistema.

---

## UC-002 - View Courses

Come utente voglio visualizzare l'elenco dei corsi disponibili.

### Risultato

Il sistema mostra la lista dei corsi.

---

## UC-003 - View Course Details

Come utente voglio visualizzare i dettagli di un corso.

### Risultato

Il sistema mostra le informazioni del corso e i moduli associati.

---

## UC-004 - Update Course

Come utente voglio modificare un corso esistente.

### Risultato

Le modifiche vengono salvate.

---

## UC-005 - Delete Course

Come utente voglio eliminare un corso.

### Risultato

Il corso viene rimosso dal sistema.

---

# Module Management

## UC-006 - Create Module

Come utente voglio aggiungere un modulo a un corso.

### Dati richiesti

* titolo
* descrizione
* posizione

### Risultato

Il modulo viene associato al corso selezionato.

---

## UC-007 - View Modules

Come utente voglio visualizzare i moduli di un corso.

### Risultato

Il sistema mostra tutti i moduli appartenenti al corso.

---

## UC-008 - Update Module

Come utente voglio modificare un modulo esistente.

### Risultato

Le modifiche vengono salvate.

---

## UC-009 - Delete Module

Come utente voglio eliminare un modulo.

### Risultato

Il modulo viene rimosso dal sistema.

---

# Note Management

## UC-010 - Create Note

Come utente voglio creare una nota collegata a un modulo.

### Dati richiesti

* titolo
* contenuto

### Risultato

La nota viene salvata nel sistema.

---

## UC-011 - View Notes

Come utente voglio visualizzare tutte le note di un modulo.

### Risultato

Il sistema mostra l'elenco delle note associate al modulo.

---

## UC-012 - View Note Details

Come utente voglio visualizzare una nota specifica.

### Risultato

Il sistema mostra il contenuto completo della nota.

---

## UC-013 - Update Note

Come utente voglio modificare una nota esistente.

### Risultato

Le modifiche vengono salvate.

---

## UC-014 - Delete Note

Come utente voglio eliminare una nota.

### Risultato

La nota viene rimossa dal sistema.

---

# MVP Scope

La prima versione di DevPath include:

* gestione corsi
* gestione moduli
* gestione note

Sono esclusi dall'MVP:

* autenticazione JWT
* ruoli utente
* tag
* resource links
* concept relations
* problem solutions
* ricerca avanzata
* microservizi
* Kubernetes
* notifiche
* dashboard statistiche

---

# Obiettivo della prima release

Consentire a uno sviluppatore di organizzare il proprio percorso di apprendimento tramite corsi, moduli e note in un sistema semplice e facilmente estendibile.
