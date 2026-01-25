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

public class StudentDashboardController implements Initializable {

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
    private VBox teachersContainer;

    private String currentUserName = "Trecy";
    private String currentClassName = "CE2-A";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDashboardData();
    }

    private void loadDashboardData() {
        // Afficher le message de bienvenue
        welcomeLabel.setText("Bonjour, " + currentUserName + " !");
        userNameLabel.setText(currentUserName);

        // Afficher la date actuelle
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", new Locale("fr", "FR"));
        String dateStr = today.format(formatter);
        // Capitaliser la première lettre
        dateStr = dateStr.substring(0, 1).toUpperCase() + dateStr.substring(1);
        dateLabel.setText(dateStr);

        // Charger les cartes d'information
        loadInfoCards();

        // Charger l'emploi du temps
        loadSchedule();

        // Charger les enseignants
        loadTeachers();
    }

    private void loadInfoCards() {
        infoCardsContainer.getChildren().clear();
        infoCardsContainer.setSpacing(20);

        // Carte "Ma classe"
        VBox classeCard = createInfoCard("MA CLASSE", currentClassName, "👌", "#e3d5ff");

        // Carte "Élèves dans ma classe"
        VBox elevesCard = createInfoCard("ÉLÈVES DANS MA CLASSE", "22", "👌", "#d5f4e6");

        // Carte "Cours aujourd'hui"
        VBox coursCard = createInfoCard("COURS AUJOURD'HUI", "5", "📅", "#d5f4e6");

        // Carte "Prochain cours"
        VBox prochainCard = createInfoCard("PROCHAIN COURS", "Français", "👌", "#d5e8ff");

        infoCardsContainer.getChildren().addAll(classeCard, elevesCard, coursCard, prochainCard);
    }

    private VBox createInfoCard(String title, String value, String icon, String bgColor) {
        VBox card = new VBox(10);
        card.setPrefWidth(280);
        card.setPrefHeight(120);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.1));
        shadow.setRadius(10);
        card.setEffect(shadow);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 11px; -fx-font-weight: bold;");

        Circle iconCircle = new Circle(18, Color.web(bgColor));
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 16px;");
        StackPane iconPane = new StackPane(iconCircle, iconLabel);

        header.getChildren().addAll(titleLabel, spacer, iconPane);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        valueLabel.setStyle("-fx-text-fill: #2d3436;");

        card.getChildren().addAll(header, valueLabel);
        return card;
    }

    private void loadSchedule() {
        scheduleContainer.getChildren().clear();

        // Données temporaires - à remplacer par des données de la base
        scheduleContainer.getChildren().add(createCourseItem("08:30", "Français", "M. Messi - Salle A102"));
        scheduleContainer.getChildren().add(createCourseItem("09:30", "Mathématiques", "M. Messi - Salle A102"));
        scheduleContainer.getChildren().add(createCourseItem("10:45", "Sciences", "M. Messi - Salle Labo"));
        scheduleContainer.getChildren().add(createCourseItem("13:30", "Arts", "Mme kenfack - Salle B201"));
        scheduleContainer.getChildren().add(createCourseItem("14:45", "Sport", "M. karim - Gymnase"));
    }

    private HBox createCourseItem(String time, String subject, String details) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");

        Label timeLabel = new Label(time);
        timeLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        timeLabel.setStyle("-fx-text-fill: #636e72;");
        timeLabel.setPrefWidth(50);

        VBox info = new VBox(3);
        Label subjectLabel = new Label(subject);
        subjectLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        subjectLabel.setStyle("-fx-text-fill: #2d3436;");

        Label detailsLabel = new Label(details);
        detailsLabel.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 12px;");

        info.getChildren().addAll(subjectLabel, detailsLabel);

        item.getChildren().addAll(timeLabel, info);
        return item;
    }

    private void loadTeachers() {
        teachersContainer.getChildren().clear();

        // Données temporaires - à remplacer par des données de la base
        teachersContainer.getChildren().add(createTeacherItem("MD", "Messi", "#d5e8ff"));
        teachersContainer.getChildren().add(createTeacherItem("JM", "Messi", "#d5e8ff"));
        teachersContainer.getChildren().add(createTeacherItem("SB", "Sophie ", "#d5e8ff"));
        teachersContainer.getChildren().add(createTeacherItem("PP", "Karim", "#d5e8ff"));
    }

    private HBox createTeacherItem(String initials, String name, String bgColor) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");

        Circle circle = new Circle(20, Color.web(bgColor));
        Label initialsLabel = new Label(initials);
        initialsLabel.setStyle("-fx-text-fill: #0984e3; -fx-font-weight: bold; -fx-font-size: 12px;");
        StackPane avatar = new StackPane(circle, initialsLabel);

        VBox info = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLabel.setStyle("-fx-text-fill: #2d3436;");

        Label subjectLabel = new Label("Toutes matières");
        subjectLabel.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 11px;");

        info.getChildren().addAll(nameLabel, subjectLabel);

        item.getChildren().addAll(avatar, info);
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
    private void handleMaClasse() {
        System.out.println("Navigation vers Ma classe");
        // TODO: Implémenter la navigation
    }

    @FXML
    private void handleEnseignants() {
        System.out.println("Navigation vers Enseignants");
        // TODO: Implémenter la navigation
    }

    @FXML
    private void handleEmploiDuTemps() {
        System.out.println("Navigation vers Emploi du temps");
        // TODO: Implémenter la navigation
    }

    @FXML
    private void handleLogout() {
        System.out.println("Déconnexion");
        // TODO: Implémenter la déconnexion
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