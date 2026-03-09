-- 1. On crée d'abord une classe pour pouvoir y affecter l'élève
INSERT INTO classes (nom, niveau, capacite, annee_scolaire) 
VALUES ('Tle C', 'Terminale', 50, '2025-2026');

-- 2. On insère le compte Enseignant utilisé dans votre LoginController (prof@ecole.cm)
INSERT INTO enseignants (nom, prenom, email, mot_de_passe, telephone, titre) 
VALUES ('MESSI', 'Jean', 'prof@ecole.cm', 'prof123', '699112233', 'Professeur');

-- * Optionnel : on ajoute des spécialités au professeur
INSERT INTO enseignant_specialites (enseignant_id, specialite) 
VALUES ((SELECT id FROM enseignants WHERE email = 'prof@ecole.cm'), 'Mathématiques'),
       ((SELECT id FROM enseignants WHERE email = 'prof@ecole.cm'), 'Physique');

-- 3. On insère le compte Etudiant utilisé dans votre LoginController (eleve@ecole.cm)
INSERT INTO etudiants (nom, prenom, email, mot_de_passe, telephone, matricule, lieu_naissance, classe_id) 
VALUES ('DEMANOU', 'Ange Trecy', 'eleve@ecole.cm', 'eleve123', '691297447', '22SI4831', 'Yaoundé', (SELECT id FROM classes WHERE nom = 'Tle C'));
