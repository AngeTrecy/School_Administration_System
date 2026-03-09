package com.example.school_administration_system.controller;

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
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

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
        // Comptes par défaut (à remplacer par AuthService + base de données)
        if (login.equals("admin@ecole.cm") && password.equals("admin123")) {
            return "admin";
        } else if (login.equals("directeur@ecole.cm") && password.equals("admin123")) {
            return "directeur";
        } else if (login.equals("prof@ecole.cm") && password.equals("prof123")) {
            return "teacher";
        } else if (login.equals("eleve@ecole.cm") && password.equals("eleve123")) {
            return "student";
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

            // Passer le nom de l'enseignant connecté
            if ("teacher".equals(role)) {
                TeacherDashboardController ctrl = loader.getController();
                // Identifier l'enseignant par son email
                String email = loginField.getText().trim();
                com.example.school_administration_system.model.Enseignant ens = com.example.school_administration_system.service.DataStore
                        .getInstance().getEnseignantByEmail(email);
                if (ens != null) {
                    ctrl.setEnseignantNom(ens.getPrenom() + " " + ens.getNom());
                }
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
