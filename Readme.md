# Systeme de Gestion Scolaire

## Description

Application de gestion pour etablissements scolaires developpee en Java avec JavaFX.
Le systeme couvre la gestion des etudiants, des enseignants, des classes, des matieres,
des emplois du temps, des rapports de seance, du cahier de texte et des presences.

L'application demarre avec un ecran de lancement (splash screen) affichant le logo
de l'etablissement pendant 3 secondes avant de rediriger vers la page de connexion.

---

## Fonctionnalites

### Authentification

- Connexion dynamique par email et mot de passe stockes en base de donnees
- Chaque enseignant et chaque etudiant dispose de son propre compte
- Comptes administrateur et directeur fixes
- Redirection automatique vers le dashboard correspondant au role

### Dashboard Administrateur

- Tableau de bord avec statistiques globales (etudiants, classes, enseignants, matieres, paiements)
- Inscription et gestion des etudiants (CRUD complet avec formulaire detaille)
- Creation et gestion des classes (nom, niveau, capacite, annee academique)
- Ajout et gestion des enseignants avec attribution de mot de passe individuel
- Gestion des matieres (code, nom, coefficient, volume horaire)
- Creation de l'emploi du temps (classe, matiere, enseignant, jour, horaires, salle)
- Visualisation de l'emploi du temps par classe sous forme de grille
- Gestion des paiements
- Impression et export de l'emploi du temps
- Consultation des rapports de seance soumis par les enseignants
- Consultation du cahier de texte des enseignants
- Consultation de l'historique des presences

### Dashboard Enseignant

- Tableau de bord avec cours du jour, rapports en attente, taux de presence, classes
- Emploi du temps personnel organise par jour avec code couleur
- Redaction et gestion des rapports de seance (creation, modification, suppression)
- Cahier de texte (titre de lecon, contenu, devoirs a la maison)
- Gestion des presences : appel par classe/matiere/date avec statut (Present, Absent, Retard)
- Historique des appels avec pourcentage de presence
- Consultation de la liste des eleves par classe

### Dashboard Etudiant

- Tableau de bord avec cartes statistiques (classe, camarades, cours du jour, enseignants)
- Consultation de la liste des camarades de classe avec matricules
- Emploi du temps complet de la classe organise par jour
- Liste des enseignants avec les matieres enseignees et le nombre de seances par semaine

### Dashboard Directeur

- Acces au meme dashboard que l'administrateur
- Validation des emplois du temps
- Consultation des statistiques generales

---

## Technologies utilisees

| Composant         | Technologie                  |
|-------------------|------------------------------|
| Langage           | Java 17+                     |
| Interface         | JavaFX 17.0.14               |
| Base de donnees   | PostgreSQL                   |
| Driver JDBC       | PostgreSQL Driver 42.7.2     |
| Build Tool        | Maven                        |
| Architecture      | MVC (Model-View-Controller)  |
| Acces aux donnees | Pattern DAO + Facade         |

---

## Prerequis

- JDK 17 ou superieur
- JavaFX SDK 17+
- PostgreSQL installe et en cours d'execution
- Maven 3.8+
- IDE recommande : IntelliJ IDEA

---

## Installation

### 1. Cloner le projet

```bash
git clone https://github.com/AngeTreworworworworworworTreworworworworworworTrecy/School_Administration_System.git
cd School_Administration_System
```

### 2. Configurer la base de donnees

Creer une base de donnees PostgreSQL nommee `school_db` :

```sql
CREATE DATABASE school_db;
```

La connexion est configuree dans le fichier
`src/main/java/.../service/DatabaseConnection.java` :

```
URL      : jdbc:postgresql://localhost:5432/school_db
USER     : postgres
PASSWORD : root
```

Modifier ces valeurs selon votre configuration locale.

### 3. Creer les tables

Les tables principales a creer dans PostgreSQL :

- `enseignants` (id, nom, prenom, email, mot_de_passe, telephone, titre)
- `etudiants` (id, nom, prenom, email, mot_de_passe, telephone, matricule, date_naissance, lieu_naissance, nom_pere, nom_mere, telephone_parent, classe_id)
- `classes` (id, nom, niveau, capacite_max, annee_academique)
- `matieres` (id, code, nom, coefficient, nb_heures)
- `enseignant_specialites` (enseignant_id, specialite)
- `seances` (id, classe_nom, matiere_nom, enseignant_nom, jour, heure_debut, heure_fin, salle)
- `rapports_seance` (id, enseignant_nom, matiere_nom, classe_nom, date, contenu, objectifs, observations, statut)
- `cahier_texte` (id, enseignant_nom, matiere_nom, classe_nom, date, titre, contenu, devoirs)
- `presences` (id, enseignant_nom, matiere_nom, classe_nom, date, etudiant_nom, statut)
- `paiements` (id, etudiant_id, montant, date_paiement, type, description)

### 4. Installer les dependances et lancer

```bash
mvn clean install
mvn javafx:run
```

Ou depuis l'IDE, executer la classe `HelloApplication.java`.

---

## Structure du projet

```
School_Administration_System/
|
|-- src/
|   |-- main/
|   |   |-- java/com/example/school_administration_system/
|   |   |   |-- model/           Entites (Utilisateur, Etudiant, Enseignant, Classe, Matiere, etc.)
|   |   |   |-- DAO/             Data Access Objects (EtudiantDAO, EnseignantDAO, ClasseDAO, etc.)
|   |   |   |-- service/         Facade DataStore + DatabaseConnection
|   |   |   |-- controller/      Controleurs JavaFX (Login, Admin, Teacher, Student)
|   |   |   |-- HelloApplication.java   Point d'entree avec splash screen
|   |   |
|   |   |-- resources/
|   |       |-- fxml/            Fichiers FXML (login, admin-dashboard, teacher-dashboard, student-dashboard)
|   |       |-- assets/images/   Logo et images
|
|-- pom.xml                      Configuration Maven et dependances
|-- Readme.md
```

---

## Comptes par defaut

### Comptes fixes (administrateur / directeur)

| Role          | Email                  | Mot de passe |
|---------------|------------------------|--------------|
| Administrateur| admin@ecole.cm         | admin123     |
| Directeur     | directeur@ecole.cm     | admin123     |

### Comptes dynamiques (enseignants / etudiants)

Les enseignants et etudiants se connectent avec l'email et le mot de passe
definis lors de leur inscription par l'administrateur.

Lors de l'ajout d'un enseignant ou d'un etudiant, l'administrateur saisit :
- L'email (qui servira d'identifiant de connexion)
- Le mot de passe

Apres l'inscription, un message de confirmation affiche les identifiants de connexion.

---

## Architecture

### Modele MVC

- **Model** : Classes entites dans le package `model` (Utilisateur, Etudiant, Enseignant, Classe, Matiere, Paiement, etc.)
- **View** : Fichiers FXML dans `resources/fxml` definissant les interfaces
- **Controller** : Classes dans le package `controller` gerant la logique d'interaction

### Couche DAO

Chaque entite dispose de son propre DAO qui encapsule les requetes SQL vers PostgreSQL.
Les DAO sont appeles via la facade `DataStore` qui centralise tous les acces aux donnees.

### Facade DataStore

Le `DataStore` agit comme un point d'acces unique vers l'ensemble des DAO.
Il fournit des methodes simples que les controleurs appellent directement.

---

## Auteur

Demanou Ange Trecy - angedemanou0@gmail.com

---

## Support

Pour toute question ou probleme, ouvrir une issue sur le depot GitHub.