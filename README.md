# MDD — Application Full Stack

MDD est une application full-stack développée dans le cadre du projet OpenClassrooms.

L’objectif du projet est de permettre à des utilisateurs :

* de consulter des articles techniques,
* de suivre des thèmes,
* de publier des articles,
* d’interagir via des commentaires,
* de gérer leur profil utilisateur.

---

# Stack Technique

## Frontend

* Angular 21
* TypeScript
* Angular Material
* RxJS
* SCSS

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate
* JWT Authentication
* Maven

## Base de données

* MySQL

---

# Architecture du projet

```text
project-root
│
├── front/
│   └── mdd-front
│
├── back/
│   └── mdd-api
│
└── README.md
```

---

# Fonctionnalités

# Authentification

* Inscription utilisateur
* Connexion utilisateur
* JWT Authentication
* Protection des routes
* Gestion session utilisateur

---

# Topics

* Consultation des thèmes
* Abonnement
* Désabonnement

---

# Articles

* Création d’articles
* Consultation du feed
* Détail article

---

# Commentaires

* Ajouter un commentaire
* Affichage commentaires article

---

# Profil utilisateur

* Modification profil
* Gestion abonnements

---

# Installation du projet

# 1. Cloner le repository

```bash
git clone <repo-url>
```

---

# 2. Backend

## Aller dans le dossier backend

```bash
cd back/mdd-api
```

---

# 3. Configurer la base de données

Créer une base MySQL :

```sql
CREATE DATABASE mdd;
```

---

# 4. Configurer application.yml

## `src/main/resources/application.yml`

```yaml
spring:

  datasource:
    url: jdbc:mysql://localhost:3306/mdd
    username: root
    password: root

  jpa:
    hibernate:
      ddl-auto: update

    show-sql: true
```

---

# 5. Installer dépendances backend

```bash
mvn clean install
```

---

# 6. Lancer backend

```bash
mvn spring-boot:run
```

Backend disponible sur :

```text
http://localhost:8080
```

---

# Swagger

Documentation API :

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Frontend

# 1. Aller dans le dossier frontend

```bash
cd front/mdd-front
```

---

# 2. Installer dépendances frontend

```bash
npm install
```

---

# 3. Installer Angular Animations

```bash
npm install @angular/animations@21.2.12
```

---

# 4. Configurer environment

## `src/environments/environment.ts`

```ts
export const environment = {

  apiUrl: 'http://localhost:8080/api'
};
```

---

# 5. Lancer Angular

```bash
ng serve
```

Frontend disponible sur :

```text
http://localhost:4200
```

---

# JWT Authentication

Le backend utilise :

* Spring Security
* JWT Bearer Token
* Stateless Authentication

Le frontend :

* sauvegarde le token dans localStorage,
* ajoute automatiquement le JWT via un interceptor.

---

# Architecture Backend

```text
src/main/java/com/openclassrooms/mdd
│
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
│   └── jwt
├── service
│   └── impl
└── MddApplication.java
```

---

# Architecture Frontend

```text
src/app
│
├── core
│   ├── guards
│   ├── interceptors
│   ├── models
│   └── services
│
├── features
│   ├── auth
│   ├── feed
│   ├── posts
│   ├── profile
│   └── topics
│
├── layout
│   └── navbar
│
└── app.routes.ts
```

---

# Tests Backend

Tests implémentés :

* Tests unitaires
* Tests intégration
* MockMvc
* Mockito

## Lancer les tests

```bash
mvn test
```

---

# Rapport de couverture Backend

JaCoCo est utilisé pour la couverture de tests.

## Générer rapport

```bash
mvn clean test
```

Rapport disponible :

```text
target/site/jacoco/index.html
```

---

# Tests Frontend

Tests Angular :

* Services
* Components
* Routing
* Guards

## Lancer tests

```bash
ng test
```

---

# Responsive Design

Application responsive :

* Desktop
* Tablet
* Mobile

---

# Sécurité

* BCrypt Password Encoder
* JWT Authentication
* Auth Guard
* HTTP Interceptor
* Protection routes backend

---

# Endpoints principaux

# Auth

```http
POST /api/auth/register
POST /api/auth/login
```

---

# Topics

```http
GET /api/topics
POST /api/topics/{id}/subscribe
DELETE /api/topics/{id}/subscribe
```

---

# Posts

```http
GET /api/posts/feed
POST /api/posts
GET /api/posts/{id}
```

---

# Comments

```http
POST /api/posts/{id}/comments
```

---

# Users

```http
GET /api/users/me
PUT /api/users/me
```

---

# Auteur

Projet réalisé dans le cadre de la formation OpenClassrooms.
