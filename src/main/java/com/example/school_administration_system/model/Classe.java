package com.example.school_administration_system.model;
import java.util.ArrayList;
import java.util.List;

    public class Classe {
        private int id;
        private String nom;
        private String niveau;
        private int capaciteMax;
        private String anneeAcademique;
        private List<Etudiant> etudiants;

        public Classe() {
            this.etudiants = new ArrayList<>();
        }

        public Classe(String nom, String niveau, int capaciteMax, String anneeAcademique) {
            this.nom = nom;
            this.niveau = niveau;
            this.capaciteMax = capaciteMax;
            this.anneeAcademique = anneeAcademique;
            this.etudiants = new ArrayList<>();
        }

        public boolean ajouterEtudiant(Etudiant etudiant) {
            if (etudiants.size() < capaciteMax) {
                etudiants.add(etudiant);
                return true;
            }
            System.out.println("Capacité maximale atteinte pour la classe " + nom);
            return false;
        }

        public boolean retirerEtudiant(Etudiant etudiant) {
            return etudiants.remove(etudiant);
        }

        public int getNombreEtudiants() {
            return etudiants.size();
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getNiveau() { return niveau; }
        public void setNiveau(String niveau) { this.niveau = niveau; }

        public int getCapaciteMax() { return capaciteMax; }
        public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }

        public String getAnneeAcademique() { return anneeAcademique; }
        public void setAnneeAcademique(String anneeAcademique) {
            this.anneeAcademique = anneeAcademique;
        }

        public List<Etudiant> getEtudiants() { return etudiants; }
        public void setEtudiants(List<Etudiant> etudiants) { this.etudiants = etudiants; }
    }

