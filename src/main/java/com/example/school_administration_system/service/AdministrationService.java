package com.example.school_administration_system.service;
import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;
import java.util.List;
public class AdministrationService {
        private final EtudiantDAO etudiantDAO;
        private final ClasseDAO classeDAO;
        private final EnseignantDAO enseignantDAO;
        private final EmploiDuTempsDAO emploiDuTempsDAO;

        public AdministrationService() {
            this.etudiantDAO = new EtudiantDAO();
            this.classeDAO = new ClasseDAO();
            this.enseignantDAO = new EnseignantDAO();
            this.emploiDuTempsDAO = new EmploiDuTempsDAO();
        }

        // Gestion des étudiants
        public boolean inscrireEtudiant(Etudiant etudiant) {
            try {
                etudiantDAO.create(etudiant);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur inscription étudiant: " + e.getMessage());
                return false;
            }
        }

        public List<Etudiant> getTousLesEtudiants() {
            return etudiantDAO.findAll();
        }

        public boolean modifierEtudiant(Etudiant etudiant) {
            try {
                etudiantDAO.update(etudiant);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur modification étudiant: " + e.getMessage());
                return false;
            }
        }

        public boolean supprimerEtudiant(int id) {
            try {
                etudiantDAO.delete(id);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur suppression étudiant: " + e.getMessage());
                return false;
            }
        }

        // Gestion des classes
        public boolean creerClasse(Classe classe) {
            try {
                classeDAO.create(classe);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur création classe: " + e.getMessage());
                return false;
            }
        }

        public List<Classe> getToutesLesClasses() {
            return classeDAO.findAll();
        }

        public Classe getClasseById(int id) {
            return classeDAO.findById(id);
        }

        public int getNombreElevesParClasse(int classeId) {
            List<Etudiant> etudiants = etudiantDAO.findByClasse(classeId);
            return etudiants.size();
        }

        public boolean modifierClasse(Classe classe) {
            try {
                classeDAO.update(classe);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur modification classe: " + e.getMessage());
                return false;
            }
        }

        public boolean supprimerClasse(int id) {
            try {
                classeDAO.delete(id);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur suppression classe: " + e.getMessage());
                return false;
            }
        }

        // Gestion des enseignants
        public boolean affecterEnseignant(int enseignantId, int classeId) {
            try {
                // Logique d'affectation (à implémenter selon votre table de liaison)
                System.out.println("Enseignant " + enseignantId + " affecté à la classe " + classeId);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur affectation enseignant: " + e.getMessage());
                return false;
            }
        }

        public List<Enseignant> getTousLesEnseignants() {
            return enseignantDAO.findAll();
        }

        // Gestion des emplois du temps
        public boolean etablirEmploiDuTemps(EmploiDuTemps emploi) {
            try {
                emploiDuTempsDAO.create(emploi);
                return true;
            } catch (Exception e) {
                System.err.println("Erreur création emploi du temps: " + e.getMessage());
                return false;
            }
        }

        public List<EmploiDuTemps> getTousLesEmploisDuTemps() {
            return emploiDuTempsDAO.findAll();
        }

        public boolean ajouterSeanceAEmploi(int emploiId, Seance seance) {
            try {
                EmploiDuTemps emploi = emploiDuTempsDAO.findById(emploiId);
                if (emploi != null) {
                    emploi.ajouterSeance(seance);
                    emploiDuTempsDAO.update(emploi);
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.err.println("Erreur ajout séance: " + e.getMessage());
                return false;
            }
        }
    }

