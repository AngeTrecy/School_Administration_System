package com.example.school_administration_system.service;
import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;
import java.time.LocalTime;
import java.util.List;

    public class EmploiDuTempsService {
        private final EmploiDuTempsDAO emploiDuTempsDAO;
        private final SeanceDAO seanceDAO;
        private final MatiereDAO matiereDAO;

        public EmploiDuTempsService() {
            this.emploiDuTempsDAO = new EmploiDuTempsDAO();
            this.seanceDAO = new SeanceDAO();
            this.matiereDAO = new MatiereDAO();
        }

        // Gestion des emplois du temps
        public boolean creerEmploiDuTemps(EmploiDuTemps emploi) {
            try {
                emploiDuTempsDAO.create(emploi);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur création emploi: " + e.getMessage());
                return false;
            }
        }

        public EmploiDuTemps getEmploiById(int id) {
            return emploiDuTempsDAO.findById(id);
        }

        public List<EmploiDuTemps> getTousLesEmplois() {
            return emploiDuTempsDAO.findAll();
        }

        // Gestion des séances
        public boolean ajouterSeance(int emploiId, Seance seance) {
            try {
                // Vérifier les conflits d'horaires
                if (verifierConflitHoraire(emploiId, seance)) {
                    System.err.println("Conflit d'horaire détecté!");
                    return false;
                }

                seanceDAO.create(seance);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur ajout séance: " + e.getMessage());
                return false;
            }
        }

        public boolean modifierSeance(Seance seance) {
            try {
                seanceDAO.update(seance);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur modification séance: " + e.getMessage());
                return false;
            }
        }

        public boolean supprimerSeance(int seanceId) {
            try {
                seanceDAO.delete(seanceId);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur suppression séance: " + e.getMessage());
                return false;
            }
        }

        public List<Seance> getSeancesParJour(int emploiId, String jour) {
            EmploiDuTemps emploi = emploiDuTempsDAO.findById(emploiId);
            if (emploi != null) {
                return emploi.getSeances().stream()
                        .filter(s -> s.getJour().equalsIgnoreCase(jour))
                        .sorted((s1, s2) -> s1.getHeureDebut().compareTo(s2.getHeureDebut()))
                        .toList();
            }
            return List.of();
        }

        // Gestion des matières
        public List<Matiere> getToutesLesMatieres() {
            return matiereDAO.findAll();
        }

        public boolean creerMatiere(Matiere matiere) {
            try {
                matiereDAO.create(matiere);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur création matière: " + e.getMessage());
                return false;
            }
        }

        // Validation
        private boolean verifierConflitHoraire(int emploiId, Seance nouvelleSeance) {
            List<Seance> seancesJour = getSeancesParJour(emploiId, nouvelleSeance.getJour());

            for (Seance seance : seancesJour) {
                if (horsairesSeChevauche(seance, nouvelleSeance)) {
                    return true;
                }
            }
            return false;
        }

        private boolean horsairesSeChevauche(Seance s1, Seance s2) {
            LocalTime debut1 = s1.getHeureDebut();
            LocalTime fin1 = s1.getHeureFin();
            LocalTime debut2 = s2.getHeureDebut();
            LocalTime fin2 = s2.getHeureFin();

            return !(fin1.isBefore(debut2) || debut1.isAfter(fin2));
        }

        public boolean estEmploiComplet(int emploiId) {
            EmploiDuTemps emploi = emploiDuTempsDAO.findById(emploiId);
            return emploi != null && !emploi.getSeances().isEmpty();
        }
    }


