package com.example.school_administration_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {


    private static final String USER_ROLE = "student"; // "student" ou "teacher"

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();

            // Charger le FXML approprié selon le rôle
            String fxmlFile = USER_ROLE.equals("teacher") 
                ? "/fxml/teacher-dashboard.fxml" 
                : "/fxml/student-dashboard.fxml";

            var url = getClass().getResource(fxmlFile);

            if (url == null) {
                System.err.println("Erreur : Fichier FXML non trouvé !");
                System.err.println("Vérifiez que le fichier est dans : src/main/resources/fxml/");
                return;
            }

            fxmlLoader.setLocation(url);
            Scene scene = new Scene(fxmlLoader.load(), 1400, 900);

            String titleSuffix = USER_ROLE.equals("teacher") 
                ? "Dashboard Enseignant" 
                : "Dashboard Étudiant";
            stage.setTitle("École Primaire Voltaire - " + titleSuffix);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            System.err.println("Erreur de chargement FXML : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}