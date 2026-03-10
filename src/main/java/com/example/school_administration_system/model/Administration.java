package com.example.school_administration_system.model;
import java.time.LocalDate;

    public class Administration extends Utilisateur {
        private LocalDate dateAutorisation;
        private String niveauAcces;

        public Administration() {
            super();
        }

        public Administration(String nom, String prenom, String email, String motDePasse,
                              String telephone, String niveauAcces) {
            super(nom, prenom, email, motDePasse, telephone);
            this.dateAutorisation = LocalDate.now();
            this.niveauAcces = niveauAcces;
        }

        public void inscrireEtudiant(Etudiant etudiant) {
            System.out.println("etudiant " + etudiant.getNomComplet() + " inscrit avec succes.");
        }

        public void creerClasse(Classe classe) {
            System.out.println("Classe " + classe.getNom() + " creee avec succes.");
        }

        public void affecterEnseignant(Enseignant enseignant, Classe classe) {
            System.out.println("Enseignant " + enseignant.getNomComplet() +
                    " affecte à la classe " + classe.getNom());
        }

        public void etablirEmploiDuTemps(EmploiDuTemps emploi) {
            System.out.println("Emploi du temps etabli avec succes.");
        }

        public void voirNombreElevesParClasse() {
            System.out.println("Consultation du nombre d'eleves par classe.");
        }

        // Getters et Setters
        public LocalDate getDateAutorisation() { return dateAutorisation; }
        public void setDateAutorisation(LocalDate dateAutorisation) {
            this.dateAutorisation = dateAutorisation;
        }

        public String getNiveauAcces() { return niveauAcces; }
        public void setNiveauAcces(String niveauAcces) { this.niveauAcces = niveauAcces; }
    }

