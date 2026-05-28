# MDD Backend API

Backend Spring Boot de l’application MDD développée dans le cadre du projet OpenClassrooms.

---

# Stack Technique

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT Authentication
- Maven
- Swagger OpenAPI
- Lombok
- JUnit / Mockito

---

# Fonctionnalités

## Authentification

- Inscription utilisateur
- Connexion utilisateur
- Génération JWT
- Sécurisation API avec Spring Security

---

## Users

- Récupération utilisateur connecté
- Modification profil utilisateur

---

## Topics

- Liste des topics
- Abonnement
- Désabonnement

---

## Posts

- Création d’article
- Feed utilisateur
- Détail article

---

## Comments

- Ajout de commentaires

---

# Architecture

Le projet respecte :

- Architecture en couches
- Principes SOLID
- DTO Pattern
- Repository Pattern
- Service Interfaces
- Separation of Concerns

---

# Structure du projet

```text id="jlwm1v"
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
Installation
Cloner le projet
git clone <repo-url>
Base de données

Créer une base MySQL :

CREATE DATABASE mdd;
Configuration application.yml
src/main/resources/application.yml
spring:

  datasource:
    url: jdbc:mysql://localhost:3306/mdd
    username: root
    password: root

  jpa:
    hibernate:
      ddl-auto: update

    show-sql: true
Installation dépendances
mvn clean install
Lancement du projet
mvn spring-boot:run

Backend disponible sur :

http://localhost:8080
Swagger

Documentation Swagger :

http://localhost:8080/swagger-ui/index.html
Authentification JWT

Le backend utilise :

JWT Bearer Token
Spring Security Filter Chain
JwtAuthenticationFilter

Les endpoints protégés nécessitent :

Authorization: Bearer <token>
Tests
Lancer les tests
mvn test
Tests implémentés
Tests unitaires services
Tests contrôleurs
Tests d’intégration
MockMvc
Mockito
Endpoints principaux
Auth
/api/auth/register
/api/auth/login
Users
GET /api/users/me
PUT /api/users/me
Topics
GET /api/topics
POST /api/topics/{id}/subscribe
DELETE /api/topics/{id}/subscribe
Posts
GET /api/posts/feed
POST /api/posts
GET /api/posts/{id}
Comments
POST /api/posts/{id}/comments
Sécurité
BCrypt Password Encoder
Stateless Authentication
JWT Validation
Route Protection
Auteur

Projet réalisé dans le cadre de la formation OpenClassrooms.