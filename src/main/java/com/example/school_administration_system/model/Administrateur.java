package com.example.school_administration_system.model;
import java.time.LocalDate;

    public class Administrateur extends Utilisateur {
        private LocalDate dateAutorisation;
        private String niveauAcces;

        public Administrateur() {
            super();
        }

        public Administrateur(String nom, String prenom, String email, String motDePasse,
                              String telephone, String niveauAcces) {
            super(nom, prenom, email, motDePasse, telephone);
            this.dateAutorisation = LocalDate.now();
            this.niveauAcces = niveauAcces;
        }

        public void inscrireEtudiant(Etudiant etudiant) {
            System.out.println("Étudiant " + etudiant.getNomComplet() + " inscrit avec succès.");
        }

        public void creerClasse(Classe classe) {
            System.out.println("Classe " + classe.getNom() + " créée avec succès.");
        }

        public void affecterEnseignant(Enseignant enseignant, Classe classe) {
            System.out.println("Enseignant " + enseignant.getNomComplet() +
                    " affecté à la classe " + classe.getNom());
        }

        public void etablirEmploiDuTemps(EmploiDuTemps emploi) {
            System.out.println("Emploi du temps établi avec succès.");
        }

        public void voirNombreElevesParClasse() {
            System.out.println("Consultation du nombre d'élèves par classe.");
        }

        // Getters et Setters
        public LocalDate getDateAutorisation() { return dateAutorisation; }
        public void setDateAutorisation(LocalDate dateAutorisation) {
            this.dateAutorisation = dateAutorisation;
        }

        public String getNiveauAcces() { return niveauAcces; }
        public void setNiveauAcces(String niveauAcces) { this.niveauAcces = niveauAcces; }
    }

