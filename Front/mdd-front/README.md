# MDD Frontend

Frontend Angular de l’application MDD développée dans le cadre du projet OpenClassrooms.

---

# Stack Technique

- Angular 20
- TypeScript
- Angular Material
- RxJS
- SCSS
- JWT Authentication
- Angular Standalone Components

---

# Fonctionnalités

## Authentification

- Inscription utilisateur
- Connexion utilisateur
- Gestion JWT
- Route Guard
- Interceptor HTTP JWT

---

## Feed

- Affichage des articles
- Navigation vers détail article
- Responsive design

---

## Topics

- Liste des topics
- Abonnement / désabonnement

---

## Posts

- Création d’article
- Détail article
- Commentaires
- Ajout de commentaire

---

## Profil

- Modification profil
- Liste abonnements
- Désabonnement

---

# Installation

## Cloner le projet

```bash
git clone <repo-url>
Installer les dépendances
npm install
Lancement du projet
Démarrer Angular
ng serve

Application disponible sur :

http://localhost:4200
Backend

Le backend Spring Boot doit être lancé sur :

http://localhost:8080
Structure du projet
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
Authentification JWT

Le token JWT est :

stocké dans le localStorage
automatiquement ajouté aux requêtes HTTP via un interceptor
Variables d’environnement
src/environments/environment.ts
export const environment = {

  apiUrl: 'http://localhost:8080/api'
};
Tests
Lancer les tests
ng test
Responsive Design

L’application est responsive :

Desktop
Tablet
Mobile
Auteur

Projet réalisé dans le cadre de la formation OpenClassrooms.