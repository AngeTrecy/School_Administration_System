package com.example.school_administration_system.service;

import com.example.school_administration_system.DAO.*;
import com.example.school_administration_system.model.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Le DataStore fait maintenant office de "Façade" (Proxy).
 * Ses méthodes appellent les DAO pour interagir avec la base de données
 * PostgreSQL.
 */
public class DataStore {

    private static DataStore instance;

    // Instances des DAOs
    private final EtudiantDAO etudiantDAO;
    private final ClasseDAO classeDAO;
    private final EnseignantDAO enseignantDAO;
    private final MatiereDAO matiereDAO;
    private final PaiementDAO paiementDAO;
    private final SeanceDAO seanceDAO;
    private final RapportSeanceDAO rapportDAO;
    private final PresenceDAO presenceDAO;
    private final CahierTexteDAO cahierDAO;

    private DataStore() {
        this.etudiantDAO = new EtudiantDAO();
        this.classeDAO = new ClasseDAO();
        this.enseignantDAO = new EnseignantDAO();
        this.matiereDAO = new MatiereDAO();
        this.paiementDAO = new PaiementDAO();
        this.seanceDAO = new SeanceDAO();
        this.rapportDAO = new RapportSeanceDAO();
        this.presenceDAO = new PresenceDAO();
        this.cahierDAO = new CahierTexteDAO();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ==================== ÉTUDIANTS ====================

    public void addEtudiant(Etudiant etudiant, int classeId) {
        etudiantDAO.addEtudiant(etudiant, classeId);
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.getAllEtudiants();
    }

    public Etudiant getEtudiantById(int id) {
        return etudiantDAO.getEtudiantById(id);
    }

    public void updateEtudiant(Etudiant etudiant) {
        int classeId = getClasseIdForEtudiant(etudiant.getId());
        etudiantDAO.updateEtudiant(etudiant, classeId);
    }

    public void deleteEtudiant(int id) {
        etudiantDAO.deleteEtudiant(id);
    }

    public int getClasseIdForEtudiant(int etudiantId) {
        return etudiantDAO.getClasseIdForEtudiant(etudiantId);
    }

    public String getClasseNameForEtudiant(int etudiantId) {
        return etudiantDAO.getClasseNameForEtudiant(etudiantId);
    }

    public void setEtudiantClasse(int etudiantId, int classeId) {
        Etudiant e = etudiantDAO.getEtudiantById(etudiantId);
        if (e != null) {
            etudiantDAO.updateEtudiant(e, classeId);
        }
    }

    public List<Etudiant> getEtudiantsByClasse(int classeId) {
        return etudiantDAO.getEtudiantsByClasse(classeId);
    }

    public int getEffectifClasse(int classeId) {
        return etudiantDAO.getEtudiantsByClasse(classeId).size();
    }

    // ==================== CLASSES ====================

    public void addClasse(Classe classe) {
        classeDAO.addClasse(classe);
    }

    public List<Classe> getAllClasses() {
        return classeDAO.getAllClasses();
    }

    public Classe getClasseById(int id) {
        return classeDAO.getClasseById(id);
    }

    public void updateClasse(Classe classe) {
        classeDAO.updateClasse(classe);
    }

    public void deleteClasse(int id) {
        classeDAO.deleteClasse(id);
    }

    // ==================== ENSEIGNANTS ====================

    public void addEnseignant(Enseignant enseignant) {
        enseignantDAO.addEnseignant(enseignant);
    }

    public List<Enseignant> getAllEnseignants() {
        return enseignantDAO.getAllEnseignants();
    }

    public Enseignant getEnseignantById(int id) {
        return enseignantDAO.getEnseignantById(id);
    }

    public void updateEnseignant(Enseignant enseignant) {
        enseignantDAO.updateEnseignant(enseignant);
    }

    public void deleteEnseignant(int id) {
        enseignantDAO.deleteEnseignant(id);
    }

    public Enseignant getEnseignantByEmail(String email) {
        return enseignantDAO.getAllEnseignants().stream()
                .filter(e -> e.getEmail() != null && e.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    // ==================== MATIÈRES ====================

    public void addMatiere(Matiere matiere) {
        matiereDAO.addMatiere(matiere);
    }

    public List<Matiere> getAllMatieres() {
        return matiereDAO.getAllMatieres();
    }

    public Matiere getMatiereById(int id) {
        return matiereDAO.getMatiereById(id);
    }

    public void updateMatiere(Matiere matiere) {
        matiereDAO.updateMatiere(matiere);
    }

    public void deleteMatiere(int id) {
        matiereDAO.deleteMatiere(id);
    }

    public List<Matiere> getMatieresByClasse(String classeNom) {
        List<String> matiereNoms = seanceDAO.getSeanceEntriesByClasse(classeNom).stream()
                .map(SeanceEntry::getMatiereNom)
                .distinct()
                .toList();
        return matiereDAO.getAllMatieres().stream()
                .filter(m -> matiereNoms.contains(m.getNom()))
                .toList();
    }

    // ==================== PAIEMENTS ====================

    public void addPaiement(Paiement paiement) {
        paiementDAO.addPaiement(paiement);
    }

    public List<Paiement> getAllPaiements() {
        return paiementDAO.getAllPaiements();
    }

    public List<Paiement> getPaiementsByEtudiant(int etudiantId) {
        return paiementDAO.getPaiementsByEtudiant(etudiantId);
    }

    public Paiement getPaiementById(int id) {
        return paiementDAO.getPaiementById(id);
    }

    public void updatePaiement(Paiement paiement) {
        paiementDAO.updatePaiement(paiement);
    }

    public void deletePaiement(int id) {
        paiementDAO.deletePaiement(id);
    }

    public double getTotalPaiements() {
        return paiementDAO.getAllPaiements().stream().mapToDouble(Paiement::getMontant).sum();
    }

    public int getNombrePaiements() {
        return paiementDAO.getAllPaiements().size();
    }

    public List<Paiement> getRecentPaiements(int limit) {
        List<Paiement> all = paiementDAO.getAllPaiements();
        java.util.Collections.reverse(all);
        return all.stream().limit(limit).toList();
    }

    // ==================== STATISTIQUES ====================

    public int getTotalEtudiants() {
        return etudiantDAO.getAllEtudiants().size();
    }

    public int getTotalClasses() {
        return classeDAO.getAllClasses().size();
    }

    public int getTotalEnseignants() {
        return enseignantDAO.getAllEnseignants().size();
    }

    public int getTotalMatieres() {
        return matiereDAO.getAllMatieres().size();
    }

    // ==================== CLASSES INTERNES & LEURS METHODES ====================

    public static class SeanceEntry {
        private int id;
        private String classeNom;
        private String matiereNom;
        private String enseignantNom;
        private String jour;
        private String heureDebut;
        private String heureFin;
        private String salle;

        public SeanceEntry(String classeNom, String matiereNom, String enseignantNom,
                String jour, String heureDebut, String heureFin, String salle) {
            this.classeNom = classeNom;
            this.matiereNom = matiereNom;
            this.enseignantNom = enseignantNom;
            this.jour = jour;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
            this.salle = salle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClasseNom() {
            return classeNom;
        }

        public String getMatiereNom() {
            return matiereNom;
        }

        public String getEnseignantNom() {
            return enseignantNom;
        }

        public String getJour() {
            return jour;
        }

        public String getHeureDebut() {
            return heureDebut;
        }

        public String getHeureFin() {
            return heureFin;
        }

        public String getSalle() {
            return salle;
        }
    }

    public void addSeanceEntry(SeanceEntry entry) {
        seanceDAO.addSeanceEntry(entry);
    }

    public List<SeanceEntry> getAllSeanceEntries() {
        return seanceDAO.getAllSeanceEntries();
    }

    public List<SeanceEntry> getSeanceEntriesByClasse(String classeNom) {
        return seanceDAO.getSeanceEntriesByClasse(classeNom);
    }

    public void deleteSeanceEntry(int id) {
        seanceDAO.deleteSeanceEntry(id);
    }

    public List<SeanceEntry> getSeancesByEnseignant(String enseignantNom) {
        return seanceDAO.getAllSeanceEntries().stream()
                .filter(s -> s.getEnseignantNom().equals(enseignantNom))
                .toList();
    }

    public List<SeanceEntry> getSeancesByEnseignantAndJour(String enseignantNom, String jour) {
        return seanceDAO.getAllSeanceEntries().stream()
                .filter(s -> s.getEnseignantNom().equals(enseignantNom) && s.getJour().equalsIgnoreCase(jour))
                .toList();
    }

    public static class RapportSeanceEntry {
        private int id;
        private String enseignantNom;
        private String matiereNom;
        private String classeNom;
        private LocalDate date;
        private String contenu;
        private String objectifs;
        private String observations;
        private String statut;

        public RapportSeanceEntry() {
        }

        public RapportSeanceEntry(String enseignantNom, String matiereNom, String classeNom,
                LocalDate date, String contenu, String objectifs, String observations, String statut) {
            this.enseignantNom = enseignantNom;
            this.matiereNom = matiereNom;
            this.classeNom = classeNom;
            this.date = date;
            this.contenu = contenu;
            this.objectifs = objectifs;
            this.observations = observations;
            this.statut = statut;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnseignantNom() {
            return enseignantNom;
        }

        public void setEnseignantNom(String v) {
            this.enseignantNom = v;
        }

        public String getMatiereNom() {
            return matiereNom;
        }

        public void setMatiereNom(String v) {
            this.matiereNom = v;
        }

        public String getClasseNom() {
            return classeNom;
        }

        public void setClasseNom(String v) {
            this.classeNom = v;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate v) {
            this.date = v;
        }

        public String getContenu() {
            return contenu;
        }

        public void setContenu(String v) {
            this.contenu = v;
        }

        public String getObjectifs() {
            return objectifs;
        }

        public void setObjectifs(String v) {
            this.objectifs = v;
        }

        public String getObservations() {
            return observations;
        }

        public void setObservations(String v) {
            this.observations = v;
        }

        public String getStatut() {
            return statut;
        }

        public void setStatut(String v) {
            this.statut = v;
        }
    }

    public void addRapport(RapportSeanceEntry r) {
        rapportDAO.addRapport(r);
    }

    public void updateRapport(RapportSeanceEntry r) {
        rapportDAO.updateRapport(r);
    }

    public void deleteRapport(int id) {
        rapportDAO.deleteRapport(id);
    }

    public List<RapportSeanceEntry> getRapportsByEnseignant(String enseignantNom) {
        return rapportDAO.getRapportsByEnseignant(enseignantNom);
    }

    public List<RapportSeanceEntry> getAllRapports() {
        return rapportDAO.getAllRapports();
    }

    public static class PresenceEntry {
        private int id;
        private String enseignantNom;
        private String matiereNom;
        private String classeNom;
        private LocalDate date;
        private String etudiantNom;
        private String statut;

        public PresenceEntry() {
        }

        public PresenceEntry(String enseignantNom, String matiereNom, String classeNom,
                LocalDate date, String etudiantNom, String statut) {
            this.enseignantNom = enseignantNom;
            this.matiereNom = matiereNom;
            this.classeNom = classeNom;
            this.date = date;
            this.etudiantNom = etudiantNom;
            this.statut = statut;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnseignantNom() {
            return enseignantNom;
        }

        public void setEnseignantNom(String v) {
            this.enseignantNom = v;
        }

        public String getMatiereNom() {
            return matiereNom;
        }

        public void setMatiereNom(String v) {
            this.matiereNom = v;
        }

        public String getClasseNom() {
            return classeNom;
        }

        public void setClasseNom(String v) {
            this.classeNom = v;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate v) {
            this.date = v;
        }

        public String getEtudiantNom() {
            return etudiantNom;
        }

        public void setEtudiantNom(String v) {
            this.etudiantNom = v;
        }

        public String getStatut() {
            return statut;
        }

        public void setStatut(String v) {
            this.statut = v;
        }
    }

    public void addPresence(PresenceEntry p) {
        presenceDAO.addPresence(p);
    }

    public void updatePresence(PresenceEntry p) {
        presenceDAO.updatePresence(p);
    }

    public void deletePresencesBySession(String enseignantNom, String matiereNom, String classeNom, LocalDate date) {
        presenceDAO.deletePresencesBySession(enseignantNom, matiereNom, classeNom, date);
    }

    public List<PresenceEntry> getPresencesByEnseignant(String enseignantNom) {
        return presenceDAO.getPresencesByEnseignant(enseignantNom);
    }

    public List<PresenceEntry> getPresencesBySession(String enseignantNom, String matiereNom, String classeNom,
            LocalDate date) {
        return presenceDAO.getPresencesBySession(enseignantNom, matiereNom, classeNom, date);
    }

    public List<PresenceEntry> getAllPresences() {
        return presenceDAO.getAllPresences();
    }

    public static class CahierEntry {
        private int id;
        private String enseignantNom;
        private String matiereNom;
        private String classeNom;
        private LocalDate date;
        private String titre;
        private String contenu;
        private String devoirs;

        public CahierEntry() {
        }

        public CahierEntry(String enseignantNom, String matiereNom, String classeNom,
                LocalDate date, String titre, String contenu, String devoirs) {
            this.enseignantNom = enseignantNom;
            this.matiereNom = matiereNom;
            this.classeNom = classeNom;
            this.date = date;
            this.titre = titre;
            this.contenu = contenu;
            this.devoirs = devoirs;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnseignantNom() {
            return enseignantNom;
        }

        public void setEnseignantNom(String v) {
            this.enseignantNom = v;
        }

        public String getMatiereNom() {
            return matiereNom;
        }

        public void setMatiereNom(String v) {
            this.matiereNom = v;
        }

        public String getClasseNom() {
            return classeNom;
        }

        public void setClasseNom(String v) {
            this.classeNom = v;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate v) {
            this.date = v;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String v) {
            this.titre = v;
        }

        public String getContenu() {
            return contenu;
        }

        public void setContenu(String v) {
            this.contenu = v;
        }

        public String getDevoirs() {
            return devoirs;
        }

        public void setDevoirs(String v) {
            this.devoirs = v;
        }
    }

    public void addCahier(CahierEntry c) {
        cahierDAO.addCahierEntry(c);
    }

    public void updateCahier(CahierEntry c) {
        cahierDAO.updateCahierEntry(c);
    }

    public void deleteCahier(int id) {
        cahierDAO.deleteCahierEntry(id);
    }

    public List<CahierEntry> getCahierByEnseignant(String enseignantNom) {
        return cahierDAO.getCahierEntriesByEnseignant(enseignantNom);
    }

    public List<CahierEntry> getAllCahierEntries() {
        return cahierDAO.getAllCahierEntries();
    }
}
