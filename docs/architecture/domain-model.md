# DevPath - Domain Model

## Scopo

Il domain model descrive le principali entità di DevPath e le relazioni tra di esse.

La prima versione del progetto mantiene il dominio volutamente semplice per ridurre la complessità iniziale e consentire un'evoluzione graduale verso un'architettura a microservizi.

---

## Entità

### Course

Rappresenta un corso, una guida o un percorso di studio.

Campi principali:

* id
* title
* description
* status
* createdAt
* updatedAt

---

### Module

Rappresenta una sezione o argomento appartenente a un corso.

Campi principali:

* id
* courseId
* title
* description
* position
* status
* createdAt
* updatedAt

---

### Note

Rappresenta un appunto personale collegato a un modulo.

Campi principali:

* id
* moduleId
* title
* content
* createdAt
* updatedAt

---

## Relazioni

Un corso può contenere più moduli.

Course (1) → (N) Module

Un modulo può contenere più note.

Module (1) → (N) Note

---

## Diagramma concettuale

Course
└── Module
└── Note

---

## Elementi esclusi dalla versione MVP

Le seguenti entità verranno valutate in una fase successiva:

* Tag
* ResourceLink
* ConceptRelation
* ProblemSolution
* User Progress
* Flashcard
* Quiz
* Recommendation Engine

---

## Evoluzione futura

L'obiettivo è partire da un dominio semplice e stabile.

Una volta consolidato il modello base, sarà possibile separare il sistema in più microservizi mantenendo invariati i concetti fondamentali del dominio.
