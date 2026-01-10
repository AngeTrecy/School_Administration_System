package com.example.school_administration_system.model;
import java.time.LocalDate;

    public class Etudiant extends Utilisateur {
        private String matricule;
        private LocalDate dateNaissance;
        private String adresse;

        public Etudiant() {
            super();
        }

        public Etudiant(String nom, String prenom, String email, String motDePasse,
                        String telephone, String matricule, LocalDate dateNaissance, String adresse) {
            super(nom, prenom, email, motDePasse, telephone);
            this.matricule = matricule;
            this.dateNaissance = dateNaissance;
            this.adresse = adresse;
        }

        public void consulterClasse() {
            System.out.println(getNomComplet() + " consulte sa classe.");
        }

        public void consulterEnseignants() {
            System.out.println(getNomComplet() + " consulte la liste de ses enseignants.");
        }

        public void consulterEmploiDuTemps() {
            System.out.println(getNomComplet() + " consulte son emploi du temps.");
        }

        // Getters et Setters
        public String getMatricule() { return matricule; }
        public void setMatricule(String matricule) { this.matricule = matricule; }

        public LocalDate getDateNaissance() { return dateNaissance; }
        public void setDateNaissance(LocalDate dateNaissance) {
            this.dateNaissance = dateNaissance;
        }

        public String getAdresse() { return adresse; }
        public void setAdresse(String adresse) { this.adresse = adresse; }
    }



