package com.example.school_administration_system.controller;

import com.example.school_administration_system.model.*;
import com.example.school_administration_system.service.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Locale;

public class TeacherDashboardController implements Initializable {

    @FXML
    private VBox mainContent;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private HBox infoCardsContainer;

    @FXML
    private VBox scheduleContainer;

    @FXML
    private VBox reportsContainer;

    private String currentUserName = "Trecy";
    private String currentTeacherName = "trecy Demanou";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDashboard();
    }

    private void initializeDashboard() {
        // Définir la date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", new Locale("fr", "FR"));
        String formattedDate = today.format(formatter);
        dateLabel.setText(formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1));

        // Définir le message de bienvenue
        welcomeLabel.setText("Bonjour, " + currentUserName + " !");
        userNameLabel.setText(currentUserName);

        // Initialiser les cartes d'information
        initializeInfoCards();

        // Initialiser les cours du jour
        initializeSchedule();

        // Initialiser les rapports de séance
        initializeReports();
    }

    private void initializeInfoCards() {
        infoCardsContainer.getChildren().clear();

        // Carte 1: Cours aujourd'hui
        infoCardsContainer.getChildren().add(createInfoCard("COURS AUJOURD'HUI", "5", ""));

        // Carte 2: Rapports en attente
        infoCardsContainer.getChildren().add(createInfoCard("RAPPORTS EN ATTENTE", "1", ""));

        // Carte 3: Taux de présence
        infoCardsContainer.getChildren().add(createInfoCard("TAUX DE PRÉSENCE", "94%", ""));

        // Carte 4: Élèves
        infoCardsContainer.getChildren().add(createInfoCard("ÉLÈVES", "22", ""));
    }

    private VBox createInfoCard(String title, String value, String icon) {
        VBox card = new VBox();
        card.setPrefWidth(280);
        card.setPrefHeight(120);
        card.setSpacing(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 0);");

        // Conteneur titre + icône
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setSpacing(10);

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #b2bec3; -fx-font-weight: normal;");

        titleBox.getChildren().addAll(titleLabel, iconLabel);

        // Valeur
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");

        card.getChildren().addAll(titleBox, valueLabel);
        return card;
    }

    private void initializeSchedule() {
        scheduleContainer.getChildren().clear();

        // Données des cours
        String[][] courses = {
                {"08:30", "Français", "Salle A102"},
                {"09:30", "Mathématiques", "Salle A102"},
                {"10:45", "Sciences", "Salle Labo"},
                {"13:30", "Arts", "Salle Arts"}
        };

        for (String[] course : courses) {
            scheduleContainer.getChildren().add(createCourseItem(course[0], course[1], course[2]));
        }
    }

    private HBox createCourseItem(String time, String subject, String room) {
        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setSpacing(20);
        item.setPadding(new Insets(15));
        item.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10;");

        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #636e72;");
        timeLabel.setPrefWidth(60);

        VBox courseInfo = new VBox();
        courseInfo.setSpacing(3);

        Label subjectLabel = new Label(subject);
        subjectLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");

        Label roomLabel = new Label(room);
        roomLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #b2bec3;");

        courseInfo.getChildren().addAll(subjectLabel, roomLabel);
        item.getChildren().addAll(timeLabel, courseInfo);

        return item;
    }

    private void initializeReports() {
        reportsContainer.getChildren().clear();

        // Données des rapports
        String[][] reports = {
                {"Mathématiques", "Révision des tables de multiplication", "2024-01-15 - CE2-A", "Complété"},
                {"Français", "Dictée et correction", "2024-01-15 - CE2-A", "Complété"},
                {"Sciences", "Le cycle de l'eau", "2024-01-14 - CE2-A", "En attente"}
        };

        for (String[] report : reports) {
            reportsContainer.getChildren().add(createReportItem(report[0], report[1], report[2], report[3]));
        }
    }

    private VBox createReportItem(String subject, String description, String date, String status) {
        VBox item = new VBox();
        item.setSpacing(8);
        item.setPadding(new Insets(15));
        item.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10;");

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(150);
        Label subjectLabel = new Label(subject);
        subjectLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");

        Label statusLabel = new Label(status);
        String statusStyle = status.equals("Complété")
                ? "-fx-background-color: #1dd1a1; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 20; -fx-font-size: 10px; -fx-font-weight: bold;"
                : "-fx-background-color: #74b9ff; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 20; -fx-font-size: 10px; -fx-font-weight: bold;";
        statusLabel.setStyle(statusStyle);

        headerBox.getChildren().addAll(subjectLabel, statusLabel);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");

        Label dateLabel = new Label(date);
        dateLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #b2bec3;");

        item.getChildren().addAll(headerBox, descriptionLabel, dateLabel);
        return item;
    }

    // Gestionnaires d'événements pour les effets hover du menu
    @FXML
    private void handleMenuHover(MouseEvent event) {
        HBox menuItem = (HBox) event.getSource();
        menuItem.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-cursor: hand;");
    }

    @FXML
    private void handleMenuExit(MouseEvent event) {
        HBox menuItem = (HBox) event.getSource();
        menuItem.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-cursor: hand;");
    }

    // Gestionnaires de navigation
    @FXML
    private void handleTableauDeBord() {
        System.out.println("Navigation vers Tableau de bord");
        // Déjà sur le tableau de bord
    }

    @FXML
    private void handleMonEmploiDuTemps() {
        System.out.println("Navigation vers Mon emploi du temps");
        // TODO: Implémenter la navigation
    }

    @FXML
    private void handleRapportsDeSeance() {
        System.out.println("Navigation vers Rapports de séance");
        // TODO: Implémenter la navigation
    }

    @FXML
    private void handlePresences() {
        System.out.println("Navigation vers Présences");
        // TODO: Implémenter la navigation
    }

    @FXML
    private void handleNotifications() {
        System.out.println("Ouverture du menu des notifications");
        // TODO: Implémenter l'affichage des notifications
    }

    @FXML
    private void handleProfileMenu() {
        System.out.println("Ouverture du menu profil");
        // TODO: Implémenter le menu profil (paramètres, profil, déconnexion)
    }
}
