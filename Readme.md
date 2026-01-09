# Système de Gestion Scolaire

## Description

Application de gestion pour établissements d'enseignement secondaire développée en Java avec JavaFX. Le système permet la gestion complète des étudiants, des enseignants, des classes et des emplois du temps.

## Fonctionnalités

### Dashboard Administrateur
- Inscription et gestion des étudiants
- Création et gestion des classes
- Visualisation du nombre d'élèves par classe
- Affectation des enseignants aux classes
- Établissement des emplois du temps (matières, jours, horaires)

### Dashboard Enseignant
- Consultation de l'emploi du temps personnel (lecture seule)
- Rédaction des rapports de séance (cahier de texte)
- Gestion de l'appel et des présences

### Dashboard Étudiant
- Consultation de la classe
- Consultation de la liste des enseignants
- Consultation de l'emploi du temps de la classe

### Dashboard Directeur
- Validation des emplois du temps
- Autorisation d'accès pour les administrateurs
- Consultation des statistiques générales (classes et enseignants)

## Technologies Utilisées

- **Langage** : Java 17+
- **Interface graphique** : JavaFX 17+
- **Base de données** : MySQL 
- **ORM** : Hibernate (optionnel)
- **Build Tool** : Maven 
- **Architecture** : MVC (Model-View-Controller)

## Prérequis

- JDK 17 ou supérieur
- JavaFX SDK 17+
- MySQL 8.0+ 
- Maven 3.8+ 
- IDE recommandé : IntelliJ IDEA / Eclipse / NetBeans

## Installation

### 1. Cloner le projet
```bash
git clone https://github.com/votre-repo/gestion-scolaire.git
cd gestion-scolaire
```

### 2. Configurer la base de données

Créer une base de données MySQL :
```sql
CREATE DATABASE gestion_scolaire;
CREATE USER 'admin_scolaire'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
GRANT ALL PRIVILEGES ON gestion_scolaire.* TO 'admin_scolaire'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurer le fichier de connexion

Modifier le fichier `src/main/resources/application.properties` :
```properties
# Configuration Base de données
db.url=jdbc:mysql://localhost:3306/gestion_scolaire
db.username=admin_scolaire
db.password=votre_mot_de_passe
db.driver=com.mysql.cj.jdbc.Driver

# Configuration JavaFX
javafx.title=Système de Gestion Scolaire
javafx.width=1200
javafx.height=800
```

### 4. Installer les dépendances

**Avec Maven :**
```bash
mvn clean install
```

**Avec Gradle :**
```bash
gradle build
```

## Structure du Projet

```
gestion-scolaire/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── gestion/
│   │   │           ├── model/          # Entités (Utilisateur, Classe, etc.)
│   │   │           ├── dao/            # Data Access Objects
│   │   │           ├── service/        # Logique métier
│   │   │           ├── controller/     # Contrôleurs JavaFX
│   │   │           ├── view/           # Classes utilitaires pour les vues
│   │   │           └── Main.java       # Point d'entrée
│   │   │
│   │   └── resources/
│   │       ├── fxml/                   # Fichiers FXML pour les interfaces
│   │       ├── css/                    # Feuilles de style
│   │       ├── images/                 # Images et icônes
│   │       └── application.properties  # Configuration
│   │
│   └── test/
│       └── java/                       # Tests unitaires
│
├── pom.xml / build.gradle             # Configuration des dépendances
└── README.md
```

## Dépendances Maven (pom.xml)

```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.2</version>
    </dependency>
    
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <!-- Hibernate (optionnel) -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.2.0.Final</version>
    </dependency>
    
    <!-- JUnit pour les tests -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Exécution

### Avec Maven
```bash
mvn javafx:run
```

### Avec Gradle
```bash
gradle run
```

### Depuis l'IDE
Exécuter la classe `Main.java`

## Comptes par Défaut

Après l'initialisation de la base de données :

- **Directeur**
    - Email : directeur@ecole.cm
    - Mot de passe : admin123

- **Administrateur**
    - Email : admin@ecole.cm
    - Mot de passe : admin123

## Architecture

### Modèle MVC
- **Model** : Classes entités représentant les données (Utilisateur, Classe, EmploiDuTemps, etc.)
- **View** : Fichiers FXML définissant les interfaces utilisateur
- **Controller** : Classes gérant la logique d'interaction entre Model et View

### Couche DAO
Pattern DAO (Data Access Object) pour l'accès aux données avec des interfaces génériques :
```java
public interface GenericDAO<T> {
    void create(T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}
```

### Couche Service
Logique métier séparée pour chaque domaine (étudiant, classe, emploi du temps, etc.)

## Fonctionnalités à Développer

- [ ] Module d'authentification
- [ ] Gestion des étudiants (CRUD)
- [ ] Gestion des classes (CRUD)
- [ ] Affectation des enseignants
- [ ] Création d'emplois du temps
- [ ] Validation des emplois du temps par le directeur
- [ ] Rapport de séances (cahier de texte)
- [ ] Gestion des présences
- [ ] Tableau de bord avec statistiques
- [ ] Export des données (PDF, Excel)
- [ ] Système de notifications

## Tests

Exécuter les tests unitaires :
```bash
mvn test
```

## Contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -m 'Ajout nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Ouvrir une Pull Request

## Auteur

Votre Nom - [votre@email.com]

## Licence

Ce projet est sous licence MIT - voir le fichier LICENSE pour plus de détails.

## Support

Pour toute question ou problème, ouvrir une issue sur GitHub.