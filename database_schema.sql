-- Création de la base de données (A exécuter d'abord si elle n'existe pas)
-- CREATE DATABASE school_db;
-- \c school_db;

-- 1. Table Classes
CREATE TABLE classes (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE,
    niveau VARCHAR(50),
    capacite INT,
    annee_scolaire VARCHAR(20)
);

-- 2. Table Etudiants
CREATE TABLE etudiants (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    mot_de_passe VARCHAR(100),
    telephone VARCHAR(20),
    matricule VARCHAR(50) UNIQUE,
    date_naissance DATE,
    lieu_naissance VARCHAR(100),
    nom_pere VARCHAR(100),
    nom_mere VARCHAR(100),
    telephone_parent VARCHAR(20),
    classe_id INT REFERENCES classes(id) ON DELETE SET NULL
);

-- 3. Table Enseignants
CREATE TABLE enseignants (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    mot_de_passe VARCHAR(100),
    telephone VARCHAR(20),
    titre VARCHAR(50)
);

-- 3bis. Table des spécialités (car un enseignant peut en avoir plusieurs)
CREATE TABLE enseignant_specialites (
    enseignant_id INT REFERENCES enseignants(id) ON DELETE CASCADE,
    specialite VARCHAR(100),
    PRIMARY KEY (enseignant_id, specialite)
);

-- 4. Table Matieres
CREATE TABLE matieres (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    coefficient INT,
    credits INT
);

-- 5. Table Paiements
CREATE TABLE paiements (
    id SERIAL PRIMARY KEY,
    motif VARCHAR(100),
    detail VARCHAR(255),
    montant NUMERIC(10, 2),
    mode_paiement VARCHAR(50),
    annee_scolaire VARCHAR(20),
    etudiant_id INT REFERENCES etudiants(id) ON DELETE CASCADE
);

-- 6. Table Seances (Emploi du temps)
CREATE TABLE seances (
    id SERIAL PRIMARY KEY,
    classe_nom VARCHAR(50),
    matiere_nom VARCHAR(100),
    enseignant_nom VARCHAR(100),
    jour VARCHAR(20),
    heure_debut VARCHAR(10),
    heure_fin VARCHAR(10),
    salle VARCHAR(50)
);

-- 7. Table Rapports de Seance
CREATE TABLE rapports_seance (
    id SERIAL PRIMARY KEY,
    enseignant_nom VARCHAR(100),
    matiere_nom VARCHAR(100),
    classe_nom VARCHAR(50),
    date_rapport DATE,
    contenu TEXT,
    objectifs TEXT,
    observations TEXT,
    statut VARCHAR(50)
);

-- 8. Table Presences
CREATE TABLE presences (
    id SERIAL PRIMARY KEY,
    enseignant_nom VARCHAR(100),
    matiere_nom VARCHAR(100),
    classe_nom VARCHAR(50),
    date_presence DATE,
    etudiant_nom VARCHAR(200),
    statut VARCHAR(50)
);

-- 9. Table Cahier de Texte
CREATE TABLE cahier_texte (
    id SERIAL PRIMARY KEY,
    enseignant_nom VARCHAR(100),
    matiere_nom VARCHAR(100),
    classe_nom VARCHAR(50),
    date_cahier DATE,
    titre VARCHAR(255),
    contenu TEXT,
    devoirs TEXT
);
