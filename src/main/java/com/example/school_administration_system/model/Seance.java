package com.example.school_administration_system.model;
import java.time.LocalTime;

    public class Seance {
        private int id;
        private String jour;
        private LocalTime heureDebut;
        private LocalTime heureFin;
        private String salle;
        private Matiere matiere;

        public Seance() {}

        public Seance(String jour, LocalTime heureDebut, LocalTime heureFin,
                      String salle, Matiere matiere) {
            this.jour = jour;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
            this.salle = salle;
            this.matiere = matiere;
        }

        public String getDetails() {
            return jour + " de " + heureDebut + " à " + heureFin +
                    " - " + matiere.getNom() + " (Salle " + salle + ")";
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getJour() { return jour; }
        public void setJour(String jour) { this.jour = jour; }

        public LocalTime getHeureDebut() { return heureDebut; }
        public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }

        public LocalTime getHeureFin() { return heureFin; }
        public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }

        public String getSalle() { return salle; }
        public void setSalle(String salle) { this.salle = salle; }

        public Matiere getMatiere() { return matiere; }
        public void setMatiere(Matiere matiere) { this.matiere = matiere; }
    }

