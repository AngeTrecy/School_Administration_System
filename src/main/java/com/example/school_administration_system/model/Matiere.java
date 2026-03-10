package com.example.school_administration_system.model;

    public class Matiere {
        private int id;
        private String code;
        private String nom;
        private float coefficient;
        private int nbHeures;

        public Matiere() {}

        public Matiere(String code, String nom, float coefficient, int nbHeures) {
            this.code = code;
            this.nom = nom;
            this.coefficient = coefficient;
            this.nbHeures = nbHeures;
        }

        public String getDescription() {
            return nom + " (" + code + ") - Coef: " + coefficient + " - " + nbHeures + "h";
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public float getCoefficient() { return coefficient; }
        public void setCoefficient(float coefficient) { this.coefficient = coefficient; }

        public int getNbHeures() { return nbHeures; }
        public void setNbHeures(int nbHeures) { this.nbHeures = nbHeures; }
    }
