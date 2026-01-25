package com.example.school_administration_system.service;
import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
    public class PresenceService {
        private final PresenceDAO presenceDAO;
        private final EtudiantDAO etudiantDAO;
        private final SeanceDAO seanceDAO;

        public PresenceService() {
            this.presenceDAO = new PresenceDAO();
            this.etudiantDAO = new EtudiantDAO();
            this.seanceDAO = new SeanceDAO();
        }

        // Enregistrement des présences
        public boolean enregistrerPresence(int etudiantId, int seanceId, String statut) {
            try {
                Etudiant etudiant = etudiantDAO.findById(etudiantId);
                Seance seance = seanceDAO.findById(seanceId);

                if (etudiant != null && seance != null) {
                    Presence presence = new Presence(LocalDate.now(), etudiant, seance);
                    presence.setStatut(statut);
                    presenceDAO.create(presence);
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.err.println("Erreur enregistrement présence: " + e.getMessage());
                return false;
            }
        }

        public boolean modifierPresence(int presenceId, String nouveauStatut) {
            try {
                Presence presence = presenceDAO.findById(presenceId);
                if (presence != null) {
                    presence.setStatut(nouveauStatut);
                    presenceDAO.update(presence);
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.err.println("Erreur modification présence: " + e.getMessage());
                return false;
            }
        }

        // Consultation
        public List<Presence> getPresencesSeance(int seanceId) {
            return presenceDAO.findBySeance(seanceId);
        }

        public List<Presence> getPresencesEtudiant(int etudiantId) {
            return presenceDAO.findByEtudiant(etudiantId);
        }

        public List<Presence> getPresencesDate(LocalDate date) {
            return presenceDAO.findByDate(date);
        }

        // Statistiques
        public double getTauxPresenceEtudiant(int etudiantId) {
            return presenceDAO.getTauxPresenceEtudiant(etudiantId);
        }

        public Map<String, Integer> getStatistiquesPresenceEtudiant(int etudiantId) {
            List<Presence> presences = presenceDAO.findByEtudiant(etudiantId);

            Map<String, Integer> stats = new HashMap<>();
            stats.put("presents", 0);
            stats.put("absents", 0);
            stats.put("retards", 0);

            for (Presence p : presences) {
                switch (p.getStatut()) {
                    case "Présent" -> stats.put("presents", stats.get("presents") + 1);
                    case "Absent" -> stats.put("absents", stats.get("absents") + 1);
                    case "Retard" -> stats.put("retards", stats.get("retards") + 1);
                }
            }

            return stats;
        }

        public Map<String, Integer> getStatistiquesPresenceSeance(int seanceId) {
            List<Presence> presences = presenceDAO.findBySeance(seanceId);

            Map<String, Integer> stats = new HashMap<>();
            stats.put("presents", 0);
            stats.put("absents", 0);
            stats.put("retards", 0);

            for (Presence p : presences) {
                switch (p.getStatut()) {
                    case "Présent" -> stats.put("presents", stats.get("presents") + 1);
                    case "Absent" -> stats.put("absents", stats.get("absents") + 1);
                    case "Retard" -> stats.put("retards", stats.get("retards") + 1);
                }
            }

            return stats;
        }
    }

