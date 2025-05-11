# ✈️ TravelPro

<p align="center">
  <img src="banner.png" alt="TravelPro Screenshot" width="300">
</p>

**TravelPro** est une plateforme digitale dédiée aux **voyages d’affaires**, pensée pour les entreprises et les professionnels. Elle centralise la réservation de services, la gestion des avis, des achats, et le suivi financier, tout en offrant une interface intuitive web et desktop.

---

## 🧭 Table des matières

- [🎯 Fonctionnalités](#-fonctionnalités)  
- [✅ Roadmap](#-roadmap)  
- [💻 Prérequis](#-prérequis)  
- [🚀 Installation](#-installation)  
- [☕ Utilisation](#-utilisation)  
- [🔗 APIs utilisées](#-apis-utilisées)  
- [👥 Membres du Projet](#-membres-du-projet)  
- [😄 Contribution](#-contribution)  
- [📄 Licence](#-licence)

---

## 🎯 Fonctionnalités

- 🔐 Authentification sécurisée (connexion, inscription, gestion de profil)  
- 🛫 Réservations : vols, hôtels, taxis, voitures de location  
- 🗂️ Interface de gestion pour les services (ajout, modification, suppression)  
- 🛒 Boutique d’accessoires de voyage (panier, historique d’achat)  
- ⭐ Système d’avis : note, commentaire, modération admin  
- 📊 Tableau de bord financier pour les administrateurs  
- 💻 Application **web (PHP/MySQL)** et **desktop (JavaFX)**  
- 📄 Export PDF des réservations et factures

---

## ✅ Roadmap

- [x] Authentification et gestion des utilisateurs  
- [x] Réservation multi-service (vol, hôtel, transport)  
- [x] Boutique d’accessoires  
- [x] Système d’avis lié aux services  
- [x] Tableau de bord de gestion admin  
- [ ] Notifications et rappels (à venir)  
- [ ] Statistiques dynamiques et exports avancés (à venir)

---

## 💻 Prérequis

Avant de commencer, assurez-vous d’avoir installé :

- [PHP ≥ 8.0](https://www.php.net/)
- [XAMPP / WAMP / MAMP](https://www.apachefriends.org/index.html)
- [MySQL ≥ 5.7](https://dev.mysql.com/downloads/mysql/)
- [Java ≥ 17](https://jdk.java.net/)
- [JavaFX SDK](https://gluonhq.com/products/javafx/)
- [Figma / Adobe XD](https://www.figma.com)

---

## 🚀 Installation


git clone https://github.com/votre-utilisateur/travelpro.git
cd travelpro
Importez la base de données travelpro.sql depuis /database dans phpMyAdmin

Configurez config.php :

php
Copier
Modifier
$host = "localhost";
$dbname = "travelpro";
$user = "root";
$password = "";
Pour l'application desktop :

Ouvrir le projet JavaFX dans votre IDE (IntelliJ, NetBeans...)

Ajouter le chemin du SDK JavaFX

Lancer la classe principale

☕ Utilisation
Se connecter ou s’inscrire

Réserver un vol, un hôtel ou un transport

Laisser un avis sur un service utilisé

Acheter un produit dans la boutique

Visualiser ses factures ou réservations

Utiliser les fonctions d'administration (gestion services, paiements, rapports)

🔗 APIs utilisées
API / Service	Description	Lien
JavaFX	Application desktop UI	https://openjfx.io/
PHP & MySQL	Backend & base de données	https://www.php.net / https://mysql.com
DomPDF (optionnel)	Génération de factures PDF	https://github.com/dompdf/dompdf

👥 Membres du Projet
Nom	Rôle
Nom 1	Développeur Web – Auth & Réservations
Nom 2	Développeur Desktop – Interface JavaFX
Nom 3	Intégrateur – Base de données MySQL
Nom 4	Designer UI/UX – Maquettes Figma
Nom 5	Test & Documentation

