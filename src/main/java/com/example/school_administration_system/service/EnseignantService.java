package com.example.school_administration_system.service;

import com.example.school_administration_system.model.*;
import java.time.LocalDate;
import java.util.List;

public class EnseignantService {

    // On utilise notre DataStore (qui utilise maintenant les DAO)
    private final DataStore dataStore;

    public EnseignantService() {
        this.dataStore = DataStore.getInstance();
    }

    // Consultation emploi du temps
    public List<DataStore.SeanceEntry> getEmploiDuTempsEnseignant(String enseignantNom) {
        return dataStore.getSeancesByEnseignant(enseignantNom);
    }

    public List<DataStore.SeanceEntry> getSeancesDuJour(String enseignantNom, String jour) {
        return dataStore.getSeancesByEnseignantAndJour(enseignantNom, jour);
    }

    // Gestion des rapports de séance
    public boolean creerRapportSeance(DataStore.RapportSeanceEntry rapport) {
        try {
            dataStore.addRapport(rapport);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur création rapport: " + e.getMessage());
            return false;
        }
    }

    public boolean modifierRapportSeance(DataStore.RapportSeanceEntry rapport) {
        try {
            dataStore.updateRapport(rapport);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur modification rapport: " + e.getMessage());
            return false;
        }
    }

    public List<DataStore.RapportSeanceEntry> getRapportsEnseignant(String enseignantNom) {
        return dataStore.getRapportsByEnseignant(enseignantNom);
    }

    // Gestion des présences
    public boolean enregistrerPresence(DataStore.PresenceEntry presence) {
        try {
            dataStore.addPresence(presence);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur enregistrement présence: " + e.getMessage());
            return false;
        }
    }

    public boolean modifierPresence(DataStore.PresenceEntry presence) {
        try {
            dataStore.updatePresence(presence);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur modification présence: " + e.getMessage());
            return false;
        }
    }

    public List<DataStore.PresenceEntry> getPresencesSeance(String enseignantNom, String matiereNom, String classeNom,
            LocalDate date) {
        return dataStore.getPresencesBySession(enseignantNom, matiereNom, classeNom, date);
    }

    public boolean faireAppel(String enseignantNom, String matiereNom, String classeNom, List<Etudiant> etudiants) {
        try {
            LocalDate aujourdhui = LocalDate.now();
            for (Etudiant etudiant : etudiants) {
                DataStore.PresenceEntry presence = new DataStore.PresenceEntry(
                        enseignantNom, matiereNom, classeNom, aujourdhui,
                        etudiant.getPrenom() + " " + etudiant.getNom(), "Présent");
                dataStore.addPresence(presence);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Erreur appel: " + e.getMessage());
            return false;
        }
    }

    public double getTauxPresenceClasse(int classeId) {
        // Calculer le taux de présence moyen d'une classe
        return 0.0;
    }
}
