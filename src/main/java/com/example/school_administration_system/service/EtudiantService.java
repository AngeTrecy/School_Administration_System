package com.example.school_administration_system.service;
import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;
import java.util.List;


    public class EtudiantService {
        private final EtudiantDAO etudiantDAO;
        private final ClasseDAO classeDAO;
        private final EnseignantDAO enseignantDAO;
        private final EmploiDuTempsDAO emploiDuTempsDAO;
        private final PresenceDAO presenceDAO;

        public EtudiantService() {
            this.etudiantDAO = new EtudiantDAO();
            this.classeDAO = new ClasseDAO();
            this.enseignantDAO = new EnseignantDAO();
            this.emploiDuTempsDAO = new EmploiDuTempsDAO();
            this.presenceDAO = new PresenceDAO();
        }

        // Consultation informations personnelles
        public Etudiant getEtudiantById(int id) {
            return etudiantDAO.findById(id);
        }

        // Consultation classe
        public Classe getClasseEtudiant(int etudiantId) {
            // Logique pour trouver la classe de l'étudiant
            // À implémenter selon votre table de liaison
            return null;
        }

        public List<Etudiant> getCamaradesClasse(int etudiantId) {
            Classe classe = getClasseEtudiant(etudiantId);
            if (classe != null) {
                return etudiantDAO.findByClasse(classe.getId());
            }
            return List.of();
        }

        // Consultation enseignants
        public List<Enseignant> getEnseignantsClasse(int classeId) {
            // Logique pour récupérer les enseignants d'une classe
            // À implémenter selon votre table de liaison
            return enseignantDAO.findAll();
        }

        // Consultation emploi du temps
        public EmploiDuTemps getEmploiDuTempsEtudiant(int etudiantId) {
            Classe classe = getClasseEtudiant(etudiantId);
            if (classe != null) {
                List<EmploiDuTemps> emplois = emploiDuTempsDAO.findByClasse(classe.getId());
                if (!emplois.isEmpty()) {
                    return emplois.get(0);
                }
            }
            return null;
        }

        public List<Seance> getSeancesJour(int etudiantId, String jour) {
            EmploiDuTemps emploi = getEmploiDuTempsEtudiant(etudiantId);
            if (emploi != null) {
                return emploi.getSeances().stream()
                        .filter(s -> s.getJour().equalsIgnoreCase(jour))
                        .toList();
            }
            return List.of();
        }

        // Consultation présences
        public List<Presence> getPresencesEtudiant(int etudiantId) {
            return presenceDAO.findByEtudiant(etudiantId);
        }

        public double getTauxPresence(int etudiantId) {
            return presenceDAO.getTauxPresenceEtudiant(etudiantId);
        }

        public int getNombreAbsences(int etudiantId) {
            List<Presence> presences = presenceDAO.findByEtudiant(etudiantId);
            return (int) presences.stream()
                    .filter(p -> "Absent".equals(p.getStatut()))
                    .count();
        }

        public int getNombreRetards(int etudiantId) {
            List<Presence> presences = presenceDAO.findByEtudiant(etudiantId);
            return (int) presences.stream()
                    .filter(p -> "Retard".equals(p.getStatut()))
                    .count();
        }
    }


