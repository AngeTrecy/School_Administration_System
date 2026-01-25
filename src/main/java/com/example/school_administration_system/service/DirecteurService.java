package com.example.school_administration_system.service;
import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;
import java.util.List;
import java.util.Map;
public class DirecteurService {
        private final AdministrationDAO administrationDAO;
        private final EmploiDuTempsDAO emploiDuTempsDAO;
        private final StatistiqueDAO statistiqueDAO;
        private final ClasseDAO classeDAO;
        private final EnseignantDAO enseignantDAO;
        private final EtudiantDAO etudiantDAO;

        public DirecteurService() {
            this.administrationDAO = new AdministrationDAO();
            this.emploiDuTempsDAO = new EmploiDuTempsDAO();
            this.statistiqueDAO = new StatistiqueDAO();
            this.classeDAO = new ClasseDAO();
            this.enseignantDAO = new EnseignantDAO();
            this.etudiantDAO = new EtudiantDAO();
        }



        // Gestion des AdministrationServices
        public boolean autoriserAdministrationService(Administration admin) {
            try {
                administrationDAO.create(admin);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur autorisation admin: " + e.getMessage());
                return false;
            }
        }

        public boolean revoquerAcces(int adminId) {
            try {
                administrationDAO.delete(adminId);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur révocation: " + e.getMessage());
                return false;
            }
        }

    public List<Administration> getTousLesAdministrations() {
        return administrationDAO.findAll(); // ✅
    }


    // Validation des emplois du temps
        public List<EmploiDuTemps> getEmploisEnAttente() {
            return emploiDuTempsDAO.findEnAttente();
        }

        public boolean validerEmploiDuTemps(int emploiId) {
            try {
                EmploiDuTemps emploi = emploiDuTempsDAO.findById(emploiId);
                if (emploi != null) {
                    emploi.setValide(true);
                    emploiDuTempsDAO.update(emploi);
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.err.println("Erreur validation emploi: " + e.getMessage());
                return false;
            }
        }

        public boolean rejeterEmploiDuTemps(int emploiId) {
            try {
                EmploiDuTemps emploi = emploiDuTempsDAO.findById(emploiId);
                if (emploi != null) {
                    emploi.setStatut("Rejeté");
                    emploiDuTempsDAO.update(emploi);
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.err.println("Erreur rejet emploi: " + e.getMessage());
                return false;
            }
        }

        // Statistiques générales
        public Statistique getStatistiquesGenerales() {
            return statistiqueDAO.getStatistiquesGenerales();
        }

        public Map<String, Integer> getStatistiquesParClasse() {
            return statistiqueDAO.getStatistiquesParClasse();
        }

        public int getNombreTotalEtudiants() {
            return etudiantDAO.findAll().size();
        }

        public int getNombreTotalClasses() {
            return classeDAO.findAll().size();
        }

        public int getNombreTotalEnseignants() {
            return enseignantDAO.findAll().size();
        }

        public int getNombreEmploisEnAttente() {
            return emploiDuTempsDAO.findEnAttente().size();
        }

        // Rapports
        public String genererRapportGlobal() {
            StringBuilder rapport = new StringBuilder();
            rapport.append("=== RAPPORT GLOBAL ===\n\n");

            rapport.append("Étudiants: ").append(getNombreTotalEtudiants()).append("\n");
            rapport.append("Classes: ").append(getNombreTotalClasses()).append("\n");
            rapport.append("Enseignants: ").append(getNombreTotalEnseignants()).append("\n");
            rapport.append("Emplois en attente: ").append(getNombreEmploisEnAttente()).append("\n\n");

            rapport.append("=== RÉPARTITION PAR CLASSE ===\n");
            Map<String, Integer> statsClasses = getStatistiquesParClasse();
            for (Map.Entry<String, Integer> entry : statsClasses.entrySet()) {
                rapport.append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" étudiants\n");
            }

            return rapport.toString();
        }

        public Map<String, Double> getTauxPresenceParClasse(int classeId) {
            return statistiqueDAO.getTauxPresenceParClasse(classeId);
        }
    }


