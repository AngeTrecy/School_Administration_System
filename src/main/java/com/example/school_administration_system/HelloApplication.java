package com.example.school_administration_system;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // --- 1. Fenêtre de Splash Screen (Logo uniquement) ---
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED); 

        ImageView splashLogo = new ImageView(new Image(getClass().getResourceAsStream("/assets/images/d.png")));
        splashLogo.setFitWidth(300); 
        splashLogo.setPreserveRatio(true);

        StackPane splashLayout = new StackPane(splashLogo);
        splashLayout.setStyle(
                "-fx-background-color: white; -fx-padding: 50; -fx-background-radius: 15; -fx-border-radius: 15;");

        // Pour gérer l'arrondi, il faut rendre la scène transparente
        Scene splashScene = new Scene(splashLayout);
        splashScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        splashStage.setScene(splashScene);
        splashStage.initStyle(StageStyle.TRANSPARENT);

       
        Image icon = new Image(getClass().getResourceAsStream("/assets/images/d_white.png"));
        splashStage.getIcons().add(icon);

        splashStage.centerOnScreen();
        splashStage.show();

       
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
          
            splashStage.close();

            
            afficherLoginPage(primaryStage, icon);
        });
        delay.play();
    }

    private void afficherLoginPage(Stage stage, Image icon) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            var url = getClass().getResource("/fxml/login.fxml");

            if (url == null) {
                System.err.println("Erreur : Fichier FXML login.fxml non trouvé !");
                return;
            }

            fxmlLoader.setLocation(url);
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setTitle("Système de Gestion Scolaire - Connexion");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.getIcons().add(icon);
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