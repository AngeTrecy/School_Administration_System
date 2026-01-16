package com.example.school_administration_system.service;
import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;
import java.time.LocalDate;
import java.util.List;
    public class EnseignantService {
        private final SeanceDAO seanceDAO;
        private final RapportSeanceDAO rapportSeanceDAO;
        private final PresenceDAO presenceDAO;
        private final EmploiDuTempsDAO emploiDuTempsDAO;

        public EnseignantService() {
            this.seanceDAO = new SeanceDAO();
            this.rapportSeanceDAO = new RapportSeanceDAO();
            this.presenceDAO = new PresenceDAO();
            this.emploiDuTempsDAO = new EmploiDuTempsDAO();
        }

        // Consultation emploi du temps
        public List<Seance> getEmploiDuTempsEnseignant(int enseignantId) {
            return seanceDAO.findByEnseignant(enseignantId);
        }

        public List<Seance> getSeancesDuJour(int enseignantId, String jour) {
            List<Seance> toutesSeances = seanceDAO.findByEnseignant(enseignantId);
            return toutesSeances.stream()
                    .filter(s -> s.getJour().equalsIgnoreCase(jour))
                    .toList();
        }

        // Gestion des rapports de séance
        public boolean creerRapportSeance(RapportSeance rapport) {
            try {
                rapportSeanceDAO.create(rapport);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur création rapport: " + e.getMessage());
                return false;
            }
        }

        public boolean modifierRapportSeance(RapportSeance rapport) {
            try {
                rapportSeanceDAO.update(rapport);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur modification rapport: " + e.getMessage());
                return false;
            }
        }

        public List<RapportSeance> getRapportsEnseignant(int enseignantId) {
            return rapportSeanceDAO.findByEnseignant(enseignantId);
        }

        public RapportSeance getRapportById(int id) {
            return rapportSeanceDAO.findById(id);
        }

        // Gestion des présences
        public boolean enregistrerPresence(Presence presence) {
            try {
                presenceDAO.create(presence);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur enregistrement présence: " + e.getMessage());
                return false;
            }
        }

        public boolean modifierPresence(Presence presence) {
            try {
                presenceDAO.update(presence);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur modification présence: " + e.getMessage());
                return false;
            }
        }

        public List<Presence> getPresencesSeance(int seanceId) {
            return presenceDAO.findBySeance(seanceId);
        }

        public boolean faireAppel(int seanceId, List<Etudiant> etudiants) {
            try {
                LocalDate aujourdhui = LocalDate.now();
                Seance seance = seanceDAO.findById(seanceId);

                for (Etudiant etudiant : etudiants) {
                    Presence presence = new Presence(aujourdhui, etudiant, seance);
                    presenceDAO.create(presence);
                }
                return true;
            } catch (Exception e) {
                System.err.println("Erreur appel: " + e.getMessage());
                return false;
            }
        }

        public double getTauxPresenceClasse(int classeId) {
            // Calculer le taux de présence moyen d'une classe
            try {
                // Logique de calcul à implémenter
                return 0.0;
            } catch (Exception e) {
                System.err.println("Erreur calcul taux: " + e.getMessage());
                return 0.0;
            }
        }
    }

