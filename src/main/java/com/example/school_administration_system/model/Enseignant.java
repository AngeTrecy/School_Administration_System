package com.example.school_administration_system.model;
import java.util.ArrayList;
import java.util.List;

    public class Enseignant extends Utilisateur {
        private List<String> specialites;
        private String grade;

        public Enseignant() {
            super();
            this.specialites = new ArrayList<>();
        }

        public Enseignant(String nom, String prenom, String email, String motDePasse,
                          String telephone, String grade) {
            super(nom, prenom, email, motDePasse, telephone);
            this.specialites = new ArrayList<>();
            this.grade = grade;
        }

        public void consulterEmploiDuTemps() {
            System.out.println(getNomComplet() + " consulte son emploi du temps.");
        }

        public void faireRapportSeance(RapportSeance rapport) {
            rapport.enregistrer();
            System.out.println("Rapport de séance enregistré par " + getNomComplet());
        }

        public void faireAppel(Presence presence) {
            System.out.println("Appel effectué pour la séance.");
        }

        public void ajouterSpecialite(String specialite) {
            this.specialites.add(specialite);
        }

        // Getters et Setters
        public List<String> getSpecialites() { return specialites; }
        public void setSpecialites(List<String> specialites) { this.specialites = specialites; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }
    }
