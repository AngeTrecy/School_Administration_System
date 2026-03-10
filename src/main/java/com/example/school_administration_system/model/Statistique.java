package com.example.school_administration_system.model;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

    public class Statistique {
        private int id;
        private String type;
        private LocalDate periode;
        private Map<String, Object> donnees;

        public Statistique() {
            this.donnees = new HashMap<>();
        }

        public Statistique(String type, LocalDate periode) {
            this.type = type;
            this.periode = periode;
            this.donnees = new HashMap<>();
        }

        public void calculer() {
            System.out.println("Calcul des statistiques de type: " + type);
        }

        public void generer() {
            System.out.println("Génération des statistiques pour la période: " + periode);
        }

        public void ajouterDonnee(String cle, Object valeur) {
            donnees.put(cle, valeur);
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public LocalDate getPeriode() { return periode; }
        public void setPeriode(LocalDate periode) { this.periode = periode; }

        public Map<String, Object> getDonnees() { return donnees; }
        public void setDonnees(Map<String, Object> donnees) { this.donnees = donnees; }
    }

