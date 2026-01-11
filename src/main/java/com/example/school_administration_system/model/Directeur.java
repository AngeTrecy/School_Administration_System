package com.example.school_administration_system.model;

    public class Directeur extends Utilisateur {

        public Directeur() {
            super();
        }

        public Directeur(String nom, String prenom, String email, String motDePasse, String telephone) {
            super(nom, prenom, email, motDePasse, telephone);
        }

        public void autoriserAdministrateur(Administration admin) {
            System.out.println("Administrateur " + admin.getNomComplet() + " autorisé.");
        }

        public void revoquerAcces(Administration admin) {
            System.out.println("Accès de " + admin.getNomComplet() + " révoqué.");
        }

        public void validerEmploiDuTemps(EmploiDuTemps emploi) {
            emploi.setValide(true);
            System.out.println("Emploi du temps validé.");
        }

        public void consulterStatistiques() {
            System.out.println("Consultation des statistiques générales.");
        }

        public void genererRapport() {
            System.out.println("Génération du rapport en cours...");
        }
    }

