package com.example.school_administration_system.model;
import java.time.LocalDate;

public class Presence {
        private int id;
        private LocalDate date;
        private String statut; // "Présent", "Absent", "Retard"
        private String remarque;
        private Etudiant etudiant;
        private Seance seance;

        public Presence() {}

        public Presence(LocalDate date, Etudiant etudiant, Seance seance) {
            this.date = date;
            this.etudiant = etudiant;
            this.seance = seance;
            this.statut = "Absent"; // Par défaut
        }

        public void marquerPresent() {
            this.statut = "Présent";
        }

        public void marquerAbsent() {
            this.statut = "Absent";
        }

        public void marquerRetard() {
            this.statut = "Retard";
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public String getStatut() { return statut; }
        public void setStatut(String statut) { this.statut = statut; }

        public String getRemarque() { return remarque; }
        public void setRemarque(String remarque) { this.remarque = remarque; }

        public Etudiant getEtudiant() { return etudiant; }
        public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

        public Seance getSeance() { return seance; }
        public void setSeance(Seance seance) { this.seance = seance; }
    }

