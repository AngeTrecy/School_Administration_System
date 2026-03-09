package com.example.school_administration_system.controller;

import com.example.school_administration_system.model.*;
import com.example.school_administration_system.service.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private HBox infoCardsContainer;
    @FXML
    private VBox scheduleContainer;
    @FXML
    private VBox teachersContainer;

    // ===== CONSTANTES DE DESIGN (uniformisées) =====
    private static final String CARD_STYLE = "-fx-background-color: white; -fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 6, 0, 0, 2);";
    private static final String CARD_TITLE_STYLE = "-fx-font-size: 10px; -fx-text-fill: #95a5a6;";
    private static final String CARD_VALUE_STYLE = "-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e6b;";
    private static final String CARD_ICON_STYLE = "-fx-font-size: 18px;";
    private static final double CARD_WIDTH = 200;
    private static final double CARD_HEIGHT = 100;

    private static final String ITEM_BG = "-fx-background-color: #f5f6fa; -fx-background-radius: 6;";
    private static final String ITEM_TITLE = "-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;";
    private static final String ITEM_SUBTITLE = "-fx-font-size: 11px; -fx-text-fill: #b2bec3;";
    private static final String ITEM_TIME = "-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #636e72;";

    private String currentUserName = "Trecy";
    private String currentClassName = "CE2-A";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDashboardData();
    }

    private void loadDashboardData() {
        welcomeLabel.setText("Bonjour, " + currentUserName + " !");

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", new Locale("fr", "FR"));
        String dateStr = today.format(formatter);
        dateStr = dateStr.substring(0, 1).toUpperCase() + dateStr.substring(1);
        dateLabel.setText(dateStr);

        loadInfoCards();
        loadSchedule();
        loadTeachers();
    }

    private void loadInfoCards() {
        infoCardsContainer.getChildren().clear();
        infoCardsContainer.getChildren().add(createInfoCard("MA CLASSE", currentClassName, "📖"));
        infoCardsContainer.getChildren().add(createInfoCard("ÉLÈVES", "22", "🎓"));
        infoCardsContainer.getChildren().add(createInfoCard("COURS AUJOURD'HUI", "5", "📅"));
        infoCardsContainer.getChildren().add(createInfoCard("PROCHAIN COURS", "Français", "🕐"));
    }

    private VBox createInfoCard(String title, String value, String icon) {
        VBox card = new VBox(10);
        card.setPrefWidth(CARD_WIDTH);
        card.setPrefHeight(CARD_HEIGHT);
        card.setPadding(new Insets(15));
        card.setStyle(CARD_STYLE);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label(title);
        titleLabel.setStyle(CARD_TITLE_STYLE);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle(CARD_ICON_STYLE);
        header.getChildren().addAll(titleLabel, spacer, iconLabel);

        Label valueLabel = new Label(value);
        valueLabel.setStyle(CARD_VALUE_STYLE);

        card.getChildren().addAll(header, valueLabel);
        return card;
    }

    private void loadSchedule() {
        scheduleContainer.getChildren().clear();
        scheduleContainer.getChildren().add(createCourseItem("08:30", "Français", "M. Messi - Salle A102"));
        scheduleContainer.getChildren().add(createCourseItem("09:30", "Mathématiques", "M. Messi - Salle A102"));
        scheduleContainer.getChildren().add(createCourseItem("10:45", "Sciences", "M. Messi - Salle Labo"));
        scheduleContainer.getChildren().add(createCourseItem("13:30", "Arts", "Mme Kenfack - Salle B201"));
        scheduleContainer.getChildren().add(createCourseItem("14:45", "Sport", "M. Karim - Gymnase"));
    }

    private HBox createCourseItem(String time, String subject, String details) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle(ITEM_BG);

        Label timeLabel = new Label(time);
        timeLabel.setStyle(ITEM_TIME);
        timeLabel.setPrefWidth(50);

        VBox info = new VBox(2);
        Label subjectLabel = new Label(subject);
        subjectLabel.setStyle(ITEM_TITLE);
        Label detailsLabel = new Label(details);
        detailsLabel.setStyle(ITEM_SUBTITLE);
        info.getChildren().addAll(subjectLabel, detailsLabel);

        item.getChildren().addAll(timeLabel, info);
        return item;
    }

    private void loadTeachers() {
        teachersContainer.getChildren().clear();
        teachersContainer.getChildren().add(createTeacherItem("MM", "M. Messi", "Mathématiques, Français"));
        teachersContainer.getChildren().add(createTeacherItem("MM", "M. Messi", "Sciences"));
        teachersContainer.getChildren().add(createTeacherItem("SK", "Mme Kenfack", "Arts"));
        teachersContainer.getChildren().add(createTeacherItem("MK", "M. Karim", "Sport"));
    }

    private HBox createTeacherItem(String initials, String name, String subjects) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle(ITEM_BG);

        Circle circle = new Circle(18, Color.web("#d5e8ff"));
        Label initialsLabel = new Label(initials);
        initialsLabel.setStyle("-fx-text-fill: #2c3e6b; -fx-font-weight: bold; -fx-font-size: 11px;");
        StackPane avatar = new StackPane(circle, initialsLabel);

        VBox info = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.setStyle(ITEM_TITLE);
        Label subjectLabel = new Label(subjects);
        subjectLabel.setStyle(ITEM_SUBTITLE);
        info.getChildren().addAll(nameLabel, subjectLabel);

        item.getChildren().addAll(avatar, info);
        return item;
    }

    // ===== NAVIGATION =====

    @FXML
    private void handleMenuHover(MouseEvent event) {
        ((HBox) event.getSource())
                .setStyle("-fx-background-color: #354a7a; -fx-cursor: hand; -fx-min-height: 42; -fx-max-height: 42;");
    }

    @FXML
    private void handleMenuExit(MouseEvent event) {
        ((HBox) event.getSource()).setStyle(
                "-fx-background-color: transparent; -fx-cursor: hand; -fx-min-height: 42; -fx-max-height: 42;");
    }

    @FXML
    private void handleTableauDeBord() {
        System.out.println("Tableau de bord");
    }

    @FXML
    private void handleMaClasse() {
        System.out.println("Ma classe");
    }

    @FXML
    private void handleEnseignants() {
        System.out.println("Enseignants");
    }

    @FXML
    private void handleEmploiDuTemps() {
        System.out.println("Emploi du temps");
    }

    @FXML
    private void handleNotifications() {
        System.out.println("Notifications");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            Stage stage = (Stage) mainContent.getScene().getWindow();
            stage.setTitle("Système de Gestion Scolaire - Connexion");
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}