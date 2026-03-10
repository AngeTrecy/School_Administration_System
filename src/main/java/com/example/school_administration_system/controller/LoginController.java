package com.example.school_administration_system.controller;

import com.example.school_administration_system.model.Enseignant;
import com.example.school_administration_system.model.Etudiant;
import com.example.school_administration_system.service.DataStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Stocke l'utilisateur authentifié pour passer ses infos au dashboard
    private Enseignant authenticatedEnseignant = null;
    private Etudiant authenticatedEtudiant = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
    }

    @FXML
    private void handleConnect() {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier les identifiants
        String role = authenticate(login, password);

        if (role != null) {
            loadDashboard(role);
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
            passwordField.clear();
        }
    }

    private String authenticate(String login, String password) {
        // Réinitialiser
        authenticatedEnseignant = null;
        authenticatedEtudiant = null;

        // 1. Comptes administrateur (fixes)
        if (login.equals("admin@ecole.cm") && password.equals("admin123")) {
            return "admin";
        } else if (login.equals("directeur@ecole.cm") && password.equals("admin123")) {
            return "directeur";
        }

        DataStore store = DataStore.getInstance();

        // 2. Vérifier parmi les enseignants inscrits dans la BDD
        List<Enseignant> enseignants = store.getAllEnseignants();
        for (Enseignant ens : enseignants) {
            if (ens.getEmail() != null && ens.getEmail().equalsIgnoreCase(login)
                    && ens.getMotDePasse() != null && ens.getMotDePasse().equals(password)) {
                authenticatedEnseignant = ens;
                return "teacher";
            }
        }

        // 3. Vérifier parmi les étudiants inscrits dans la BDD
        List<Etudiant> etudiants = store.getAllEtudiants();
        for (Etudiant et : etudiants) {
            if (et.getEmail() != null && et.getEmail().equalsIgnoreCase(login)
                    && et.getMotDePasse() != null && et.getMotDePasse().equals(password)) {
                authenticatedEtudiant = et;
                return "student";
            }
        }

        return null;
    }

    private void loadDashboard(String role) {
        try {
            String fxmlFile;
            String title;

            switch (role) {
                case "admin":
                    fxmlFile = "/fxml/admin-dashboard.fxml";
                    title = "Système de Gestion Scolaire - Dashboard Administrateur";
                    break;
                case "teacher":
                    fxmlFile = "/fxml/teacher-dashboard.fxml";
                    title = "Système de Gestion Scolaire - Dashboard Enseignant";
                    break;
                case "student":
                    fxmlFile = "/fxml/student-dashboard.fxml";
                    title = "Système de Gestion Scolaire - Dashboard Étudiant";
                    break;
                case "directeur":
                    fxmlFile = "/fxml/admin-dashboard.fxml";
                    title = "Système de Gestion Scolaire - Dashboard Directeur";
                    break;
                default:
                    return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load(), 1400, 900);

            // Passer les infos de l'enseignant connecté
            if ("teacher".equals(role) && authenticatedEnseignant != null) {
                TeacherDashboardController ctrl = loader.getController();
                ctrl.setEnseignantNom(authenticatedEnseignant.getPrenom() + " " + authenticatedEnseignant.getNom());
            }

            // Passer les infos de l'étudiant connecté
            if ("student".equals(role) && authenticatedEtudiant != null) {
                StudentDashboardController ctrl = loader.getController();
                ctrl.setStudentInfo(authenticatedEtudiant.getPrenom(), authenticatedEtudiant.getId());
            }

            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            errorLabel.setText("Erreur de chargement du tableau de bord.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        loginField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
}
