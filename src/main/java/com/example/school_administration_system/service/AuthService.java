package com.example.school_administration_system.service;

import com.example.school_administration_system.DAO.UtilisateurDAO;
import com.example.school_administration_system.model.Utilisateur;

public class AuthService {
    private UtilisateurDAO utilisateurDAO;
    private Utilisateur currentUser;

    public AuthService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public boolean login(String email, String motDePasse) {
        currentUser = utilisateurDAO.authenticate(email, motDePasse);
        return currentUser != null;
    }

    public void logout() {
        if (currentUser != null) {
            currentUser.seDeconnecter();
            currentUser = null;
        }
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public String getUserRole() {
        if (currentUser != null) {
            return utilisateurDAO.getUserRole(currentUser.getId());
        }
        return null;
    }
}