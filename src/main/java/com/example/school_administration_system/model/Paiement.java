package com.example.school_administration_system.model;

import java.time.LocalDate;

public class Paiement {
    private int id;
    private int etudiantId;
    private String typePaiement; // "INSCRIPTION" ou "SCOLARITE"
    private String tranche; // "1ère Tranche" ou "2ème Tranche"
    private double montant;
    private LocalDate datePaiement;
    private String modePaiement; // "Espèces", "Mobile Money", "Virement Bancaire"
    private String reference; // Numéro de référence unique
    private String statut; // "Payé", "En attente", "Partiel"
    private String anneeAcademique;

    public Paiement() {
        this.datePaiement = LocalDate.now();
        this.statut = "Payé";
    }

    public Paiement(int etudiantId, String typePaiement, String tranche, double montant,
            String modePaiement, String anneeAcademique) {
        this.etudiantId = etudiantId;
        this.typePaiement = typePaiement;
        this.tranche = tranche;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.datePaiement = LocalDate.now();
        this.anneeAcademique = anneeAcademique;
        this.statut = "Payé";
        this.reference = generateReference();
    }

    private String generateReference() {
        return "FAC-" + System.currentTimeMillis() % 100000;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(String tranche) {
        this.tranche = tranche;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(String anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }
}
