package com.example.school_administration_system.model;
import java.time.LocalDate;

    public class RapportSeance {
        private int id;
        private LocalDate date;
        private String contenu;
        private String objectifs;
        private String observations;
        private Seance seance;

        public RapportSeance() {}

        public RapportSeance(LocalDate date, String contenu, String objectifs,
                             String observations, Seance seance) {
            this.date = date;
            this.contenu = contenu;
            this.objectifs = objectifs;
            this.observations = observations;
            this.seance = seance;
        }

        public void enregistrer() {
            System.out.println("Rapport de séance enregistré pour le " + date);
        }

        public void modifier() {
            System.out.println("Rapport de séance modifié.");
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public String getContenu() { return contenu; }
        public void setContenu(String contenu) { this.contenu = contenu; }

        public String getObjectifs() { return objectifs; }
        public void setObjectifs(String objectifs) { this.objectifs = objectifs; }

        public String getObservations() { return observations; }
        public void setObservations(String observations) { this.observations = observations; }

        public Seance getSeance() { return seance; }
        public void setSeance(Seance seance) { this.seance = seance; }
    }

