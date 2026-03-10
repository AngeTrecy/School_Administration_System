package com.example.school_administration_system.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    public class EmploiDuTemps {
        private int id;
        private LocalDate dateDebut;
        private LocalDate dateFin;
        private String statut;
        private boolean valide;
        private List<Seance> seances;

        public EmploiDuTemps() {
            this.seances = new ArrayList<>();
            this.valide = false;
            this.statut = "En cours";
        }

        public EmploiDuTemps(LocalDate dateDebut, LocalDate dateFin) {
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.seances = new ArrayList<>();
            this.valide = false;
            this.statut = "En cours";
        }

        public void ajouterSeance(Seance seance) {
            seances.add(seance);
        }

        public void modifierSeance(Seance seance) {
            int index = seances.indexOf(seance);
            if (index != -1) {
                seances.set(index, seance);
            }
        }

        public void supprimerSeance(Seance seance) {
            seances.remove(seance);
        }

        // Getters et Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public LocalDate getDateDebut() { return dateDebut; }
        public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

        public LocalDate getDateFin() { return dateFin; }
        public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

        public String getStatut() { return statut; }
        public void setStatut(String statut) { this.statut = statut; }

        public boolean isValide() { return valide; }
        public void setValide(boolean valide) {
            this.valide = valide;
            this.statut = valide ? "Validé" : "En attente";
        }

        public List<Seance> getSeances() { return seances; }
        public void setSeances(List<Seance> seances) { this.seances = seances; }
    }

