package com.example.school_administration_system.controller;

import com.example.school_administration_system.model.*;
import com.example.school_administration_system.service.DataStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TeacherDashboardController implements Initializable {

    @FXML
    private VBox mainContent;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label pageTitle;
    @FXML
    private HBox menuAccueil;
    @FXML
    private HBox menuEmploi;
    @FXML
    private HBox menuRapports;
    @FXML
    private HBox menuCahier;
    @FXML
    private HBox menuPresences;
    @FXML
    private HBox menuClasses;

    // ===== DESIGN =====
    private static final String PRIMARY = "#2c3e6b";
    private static final String SECONDARY = "#7c3aed";
    private static final String BG_COLOR = "#f0f2f5";
    private static final String CARD_STYLE = "-fx-background-color: white; -fx-background-radius: 10; "
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);";
    private static final String SECTION_TITLE_STYLE = "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e6b;";
    private static final String INPUT_STYLE = "-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-radius: 6; "
            + "-fx-background-radius: 6; -fx-padding: 8 12 8 12; -fx-font-size: 13px;";
    private static final String BTN_PRIMARY = "-fx-background-color: #2c3e6b; -fx-text-fill: white; -fx-font-weight: bold; "
            + "-fx-background-radius: 7; -fx-padding: 9 20 9 20; -fx-cursor: hand;";
    private static final String BTN_SECONDARY = "-fx-background-color: #7c3aed; -fx-text-fill: white; -fx-font-weight: bold; "
            + "-fx-background-radius: 7; -fx-padding: 9 20 9 20; -fx-cursor: hand;";
    private static final String BTN_DANGER = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; "
            + "-fx-background-radius: 7; -fx-padding: 9 16 9 16; -fx-cursor: hand;";
    private static final String BTN_SUCCESS = "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; "
            + "-fx-background-radius: 7; -fx-padding: 9 20 9 20; -fx-cursor: hand;";

    private DataStore store;
    private String currentEnseignantNom = "Jean MESSI"; // par défaut

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        store = DataStore.getInstance();
        LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", new Locale("fr", "FR"));
        String fd = today.format(fmt);
        dateLabel.setText(fd.substring(0, 1).toUpperCase() + fd.substring(1));
        welcomeLabel.setText("Bonjour, " + getPrenomNom() + " !");
        showAccueil();
    }

    public void setEnseignantNom(String nom) {
        this.currentEnseignantNom = nom;
        welcomeLabel.setText("Bonjour, " + getPrenomNom() + " !");
    }

    private String getPrenomNom() {
        // "Jean MESSI" -> "Jean"
        String[] parts = currentEnseignantNom.split(" ");
        return parts.length > 0 ? parts[0] : currentEnseignantNom;
    }

    // ==================== NAVIGATION ====================

    private void setActiveMenu(HBox active) {
        List<HBox> menus = List.of(menuAccueil, menuEmploi, menuRapports, menuCahier, menuPresences, menuClasses);
        String normal = "-fx-background-color: transparent; -fx-cursor: hand; -fx-min-height: 42; -fx-max-height: 42;";
        String selected = "-fx-background-color: #7c3aed; -fx-cursor: hand; -fx-min-height: 42; -fx-max-height: 42;";
        for (HBox m : menus) {
            if (m != null)
                m.setStyle(normal);
        }
        if (active != null)
            active.setStyle(selected);
    }

    @FXML
    private void handleMenuHover(javafx.scene.input.MouseEvent e) {
        HBox box = (HBox) e.getSource();
        if (!box.getStyle().contains("#7c3aed")) {
            box.setStyle("-fx-background-color: #354a7a; -fx-cursor: hand; -fx-min-height: 42; -fx-max-height: 42;");
        }
    }

    @FXML
    private void handleMenuExit(javafx.scene.input.MouseEvent e) {
        HBox box = (HBox) e.getSource();
        if (!box.getStyle().contains("#7c3aed")) {
            box.setStyle(
                    "-fx-background-color: transparent; -fx-cursor: hand; -fx-min-height: 42; -fx-max-height: 42;");
        }
    }

    @FXML
    private void handleTableauDeBord() {
        showAccueil();
    }

    @FXML
    private void handleMonEmploiDuTemps() {
        showEmploiDuTemps();
    }

    @FXML
    private void handleRapportsDeSeance() {
        showRapports();
    }

    @FXML
    private void handleCahierDeTexte() {
        showCahier();
    }

    @FXML
    private void handlePresences() {
        showPresences();
    }

    @FXML
    private void handleMesClasses() {
        showClasses();
    }

    @FXML
    private void handleNotifications() {
    }

    @FXML
    private void handleProfileMenu() {
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

    // ==================== ACCUEIL ====================

    private void showAccueil() {
        setActiveMenu(menuAccueil);
        pageTitle.setText("Tableau de bord");
        mainContent.getChildren().clear();

        LocalDate today = LocalDate.now();
        String jourFr = today.format(DateTimeFormatter.ofPattern("EEEE", new Locale("fr", "FR")));
        String jourCapit = jourFr.substring(0, 1).toUpperCase() + jourFr.substring(1);

        List<DataStore.SeanceEntry> seancesAujourdHui = store.getSeancesByEnseignantAndJour(currentEnseignantNom,
                jourCapit);
        List<DataStore.RapportSeanceEntry> rapports = store.getRapportsByEnseignant(currentEnseignantNom);
        List<DataStore.SeanceEntry> toutesSeances = store.getSeancesByEnseignant(currentEnseignantNom);
        long rapportsEnAttente = rapports.stream().filter(r -> "En attente".equals(r.getStatut())).count();
        Set<String> classesSet = toutesSeances.stream().map(DataStore.SeanceEntry::getClasseNom)
                .collect(Collectors.toSet());

        // Cartes stats
        HBox cards = new HBox(15);
        cards.getChildren().addAll(
                createStatCard("COURS AUJOURD'HUI", String.valueOf(seancesAujourdHui.size()), "🕐", "#3498db"),
                createStatCard("RAPPORTS EN ATTENTE", String.valueOf(rapportsEnAttente), "📋", "#e67e22"),
                createStatCard("TAUX DE PRÉSENCE", "94%", "✅", "#27ae60"),
                createStatCard("MES CLASSES", String.valueOf(classesSet.size()), "🎓", SECONDARY));

        // Cours du jour — carte avec corps séparé
        VBox coursCard = new VBox(12);
        coursCard.setPadding(new Insets(18));
        coursCard.setStyle(CARD_STYLE);
        HBox coursHdr = new HBox(8);
        coursHdr.setAlignment(Pos.CENTER_LEFT);
        Label coursIc = new Label("🕐");
        coursIc.setStyle("-fx-font-size: 16px;");
        Label coursTl = new Label("Cours du jour — " + jourCapit);
        coursTl.setStyle(SECTION_TITLE_STYLE);
        coursHdr.getChildren().addAll(coursIc, coursTl);
        VBox coursList = new VBox(8);
        if (seancesAujourdHui.isEmpty()) {
            coursList.getChildren().add(makePlaceholder("Aucun cours aujourd'hui"));
        } else {
            for (DataStore.SeanceEntry s : seancesAujourdHui) {
                coursList.getChildren().add(makeCourseRow(s.getHeureDebut() + "-" + s.getHeureFin(), s.getMatiereNom(),
                        s.getClasseNom(), s.getSalle()));
            }
        }
        coursCard.getChildren().addAll(coursHdr, coursList);

        // Derniers rapports — carte avec corps séparé
        VBox rapportCard = new VBox(12);
        rapportCard.setPadding(new Insets(18));
        rapportCard.setStyle(CARD_STYLE);
        HBox rapportHdr = new HBox(8);
        rapportHdr.setAlignment(Pos.CENTER_LEFT);
        Label rapIc = new Label("📋");
        rapIc.setStyle("-fx-font-size: 16px;");
        Label rapTl = new Label("Derniers rapports");
        rapTl.setStyle(SECTION_TITLE_STYLE);
        rapportHdr.getChildren().addAll(rapIc, rapTl);
        VBox rapportList = new VBox(8);
        List<DataStore.RapportSeanceEntry> recent = rapports.stream()
                .sorted(Comparator.comparing(DataStore.RapportSeanceEntry::getDate).reversed())
                .limit(4).collect(Collectors.toList());
        if (recent.isEmpty()) {
            rapportList.getChildren().add(makePlaceholder("Aucun rapport disponible"));
        } else {
            for (DataStore.RapportSeanceEntry r : recent) {
                rapportList.getChildren().add(makeRapportRow(r));
            }
        }
        rapportCard.getChildren().addAll(rapportHdr, rapportList);

        HBox bottomSection = new HBox(15);
        HBox.setHgrow(coursCard, Priority.ALWAYS);
        rapportCard.setPrefWidth(360);
        bottomSection.getChildren().addAll(coursCard, rapportCard);

        mainContent.getChildren().addAll(cards, bottomSection);
        mainContent.setStyle("-fx-background-color: " + BG_COLOR + ";");
    }

    // ==================== EMPLOI DU TEMPS ====================

    private void showEmploiDuTemps() {
        setActiveMenu(menuEmploi);
        pageTitle.setText("Mon Emploi du Temps");
        mainContent.getChildren().clear();

        List<DataStore.SeanceEntry> seances = store.getSeancesByEnseignant(currentEnseignantNom);

        // Grouper par jour
        String[] jours = { "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" };
        String[] colors = { "#3498db", "#e67e22", "#9b59b6", "#27ae60", "#e74c3c", "#f39c12" };

        VBox container = new VBox(16);

        for (int i = 0; i < jours.length; i++) {
            final String jour = jours[i];
            final String color = colors[i];
            List<DataStore.SeanceEntry> jourSeances = seances.stream()
                    .filter(s -> jour.equalsIgnoreCase(s.getJour()))
                    .sorted(Comparator.comparing(DataStore.SeanceEntry::getHeureDebut))
                    .collect(Collectors.toList());

            if (jourSeances.isEmpty())
                continue;

            VBox jourCard = new VBox(10);
            jourCard.setPadding(new Insets(18));
            jourCard.setStyle(CARD_STYLE);

            // En-tête jour
            HBox header = new HBox(10);
            header.setAlignment(Pos.CENTER_LEFT);
            Label dot = new Label("●");
            dot.setStyle("-fx-font-size: 16px; -fx-text-fill: " + color + ";");
            Label jourLabel = new Label(jour);
            jourLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");
            Label count = new Label("(" + jourSeances.size() + " cours)");
            count.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6;");
            header.getChildren().addAll(dot, jourLabel, count);

            // Séances
            for (DataStore.SeanceEntry s : jourSeances) {
                HBox row = new HBox(15);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(10, 14, 10, 14));
                row.setStyle("-fx-background-color: " + color + "15; -fx-background-radius: 8;");

                // Heure
                VBox hBox = new VBox(2);
                Label h1 = new Label(s.getHeureDebut());
                h1.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + color + ";");
                Label h2 = new Label(s.getHeureFin());
                h2.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
                hBox.getChildren().addAll(h1, h2);
                hBox.setMinWidth(60);

                // Séparateur vertical
                Region sep = new Region();
                sep.setPrefWidth(3);
                sep.setPrefHeight(36);
                sep.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 3;");

                // Infos
                VBox info = new VBox(3);
                Label mat = new Label(s.getMatiereNom());
                mat.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
                HBox badges = new HBox(8);
                Label cl = makeBadge(s.getClasseNom(), "#2c3e6b");
                Label sl = makeBadge("📍 " + s.getSalle(), "#636e72");
                badges.getChildren().addAll(cl, sl);
                info.getChildren().addAll(mat, badges);
                HBox.setHgrow(info, Priority.ALWAYS);

                row.getChildren().addAll(hBox, sep, info);
                jourCard.getChildren().add(row);
            }

            container.getChildren().add(jourCard);
        }

        if (container.getChildren().isEmpty()) {
            container.getChildren().add(makePlaceholder("Aucune séance dans l'emploi du temps."));
        }

        ScrollPane scroll = wrapInScroll(container);
        mainContent.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
    }

    // ==================== RAPPORTS DE SÉANCE ====================

    private void showRapports() {
        setActiveMenu(menuRapports);
        pageTitle.setText("Rapports de Séance");
        mainContent.getChildren().clear();

        List<DataStore.RapportSeanceEntry> rapports = store.getRapportsByEnseignant(currentEnseignantNom);

        // Bouton ajouter
        Button btnAjouter = new Button("+ Nouveau rapport");
        btnAjouter.setStyle(BTN_SECONDARY);
        btnAjouter.setOnAction(e -> showFormulaireRapport(null));

        HBox topBar = new HBox(btnAjouter);
        topBar.setAlignment(Pos.CENTER_RIGHT);

        VBox listCard = new VBox(12);
        listCard.setPadding(new Insets(20));
        listCard.setStyle(CARD_STYLE);

        Label listTitle = new Label("Liste des rapports (" + rapports.size() + ")");
        listTitle.setStyle(SECTION_TITLE_STYLE);

        VBox rows = new VBox(10);
        if (rapports.isEmpty()) {
            rows.getChildren().add(makePlaceholder("Aucun rapport enregistré"));
        } else {
            // trier par date desc
            rapports.stream()
                    .sorted(Comparator.comparing(DataStore.RapportSeanceEntry::getDate).reversed())
                    .forEach(r -> rows.getChildren().add(makeRapportCardFull(r)));
        }
        listCard.getChildren().addAll(listTitle, rows);

        mainContent.getChildren().addAll(topBar, listCard);
    }

    private void showFormulaireRapport(DataStore.RapportSeanceEntry existing) {
        mainContent.getChildren().clear();
        boolean isEdit = existing != null;

        VBox form = new VBox(16);
        form.setPadding(new Insets(24));
        form.setStyle(CARD_STYLE);
        form.setMaxWidth(700);

        Label title = new Label(isEdit ? "✏️ Modifier le rapport" : "📋 Nouveau rapport de séance");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");

        // Matière
        Label lMat = fieldLabel("Matière *");
        ComboBox<String> cbMat = new ComboBox<>();
        cbMat.setMaxWidth(Double.MAX_VALUE);
        cbMat.setStyle(INPUT_STYLE);
        store.getSeancesByEnseignant(currentEnseignantNom).stream()
                .map(DataStore.SeanceEntry::getMatiereNom).distinct().sorted()
                .forEach(cbMat.getItems()::add);

        // Classe
        Label lCl = fieldLabel("Classe *");
        ComboBox<String> cbCl = new ComboBox<>();
        cbCl.setMaxWidth(Double.MAX_VALUE);
        cbCl.setStyle(INPUT_STYLE);
        store.getSeancesByEnseignant(currentEnseignantNom).stream()
                .map(DataStore.SeanceEntry::getClasseNom).distinct().sorted()
                .forEach(cbCl.getItems()::add);

        // Date
        Label lDate = fieldLabel("Date *");
        DatePicker dp = new DatePicker(isEdit ? existing.getDate() : LocalDate.now());
        dp.setMaxWidth(Double.MAX_VALUE);
        dp.setStyle(INPUT_STYLE);

        // Contenu
        Label lContenu = fieldLabel("Contenu de la séance *");
        TextArea taContenu = new TextArea(isEdit ? existing.getContenu() : "");
        taContenu.setPrefRowCount(3);
        taContenu.setWrapText(true);
        taContenu.setStyle(INPUT_STYLE);

        // Objectifs
        Label lObj = fieldLabel("Objectifs");
        TextArea taObj = new TextArea(isEdit ? existing.getObjectifs() : "");
        taObj.setPrefRowCount(2);
        taObj.setWrapText(true);
        taObj.setStyle(INPUT_STYLE);

        // Observations
        Label lObs = fieldLabel("Observations");
        TextArea taObs = new TextArea(isEdit ? existing.getObservations() : "");
        taObs.setPrefRowCount(2);
        taObs.setWrapText(true);
        taObs.setStyle(INPUT_STYLE);

        // Statut
        Label lStatut = fieldLabel("Statut");
        ComboBox<String> cbStatut = new ComboBox<>();
        cbStatut.getItems().addAll("Complété", "En attente");
        cbStatut.setValue(isEdit ? existing.getStatut() : "En attente");
        cbStatut.setMaxWidth(Double.MAX_VALUE);
        cbStatut.setStyle(INPUT_STYLE);

        if (isEdit) {
            cbMat.setValue(existing.getMatiereNom());
            cbCl.setValue(existing.getClasseNom());
        }

        Label errLabel = new Label("");
        errLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");

        Button btnSave = new Button(isEdit ? "💾 Enregistrer" : "✅ Créer le rapport");
        btnSave.setStyle(BTN_PRIMARY);
        btnSave.setOnAction(e -> {
            if (cbMat.getValue() == null || cbCl.getValue() == null || taContenu.getText().isBlank()) {
                errLabel.setText("Veuillez remplir tous les champs obligatoires (*)");
                return;
            }
            DataStore.RapportSeanceEntry r = isEdit ? existing : new DataStore.RapportSeanceEntry();
            r.setEnseignantNom(currentEnseignantNom);
            r.setMatiereNom(cbMat.getValue());
            r.setClasseNom(cbCl.getValue());
            r.setDate(dp.getValue() != null ? dp.getValue() : LocalDate.now());
            r.setContenu(taContenu.getText().trim());
            r.setObjectifs(taObj.getText().trim());
            r.setObservations(taObs.getText().trim());
            r.setStatut(cbStatut.getValue());
            if (isEdit)
                store.updateRapport(r);
            else
                store.addRapport(r);
            showRapports();
        });

        Button btnCancel = new Button("Annuler");
        btnCancel.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2d3436; -fx-font-weight: bold; "
                + "-fx-background-radius: 7; -fx-padding: 9 20 9 20; -fx-cursor: hand;");
        btnCancel.setOnAction(e -> showRapports());

        HBox buttons = new HBox(12, btnSave, btnCancel);

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(12);
        grid.setMaxWidth(Double.MAX_VALUE);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(c1, c2);

        grid.addRow(0, lMat, lCl);
        grid.addRow(1, cbMat, cbCl);
        grid.addRow(2, lDate, lStatut);
        grid.addRow(3, dp, cbStatut);

        VBox full = new VBox(12, lContenu, taContenu, lObj, taObj, lObs, taObs);

        form.getChildren().addAll(title, grid, full, errLabel, buttons);

        ScrollPane scroll = wrapInScroll(form);
        mainContent.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
    }

    // ==================== CAHIER DE TEXTE ====================

    private void showCahier() {
        setActiveMenu(menuCahier);
        pageTitle.setText("Cahier de Texte");
        mainContent.getChildren().clear();

        List<DataStore.CahierEntry> entries = store.getCahierByEnseignant(currentEnseignantNom);

        Button btnAjouter = new Button("+ Nouvelle entrée");
        btnAjouter.setStyle(BTN_SECONDARY);
        btnAjouter.setOnAction(e -> showFormulaireCahier(null));

        HBox topBar = new HBox(btnAjouter);
        topBar.setAlignment(Pos.CENTER_RIGHT);

        VBox listCard = new VBox(12);
        listCard.setPadding(new Insets(20));
        listCard.setStyle(CARD_STYLE);

        Label listTitle = new Label("Entrées du cahier (" + entries.size() + ")");
        listTitle.setStyle(SECTION_TITLE_STYLE);

        VBox rows = new VBox(10);
        if (entries.isEmpty()) {
            rows.getChildren().add(makePlaceholder("Aucune entrée dans le cahier de texte"));
        } else {
            entries.stream()
                    .sorted(Comparator.comparing(DataStore.CahierEntry::getDate).reversed())
                    .forEach(c -> rows.getChildren().add(makeCahierCard(c)));
        }
        listCard.getChildren().addAll(listTitle, rows);
        mainContent.getChildren().addAll(topBar, listCard);
    }

    private void showFormulaireCahier(DataStore.CahierEntry existing) {
        mainContent.getChildren().clear();
        boolean isEdit = existing != null;

        VBox form = new VBox(16);
        form.setPadding(new Insets(24));
        form.setStyle(CARD_STYLE);
        form.setMaxWidth(700);

        Label title = new Label(isEdit ? "✏️ Modifier l'entrée" : "📝 Nouvelle entrée du cahier");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");

        Label lMat = fieldLabel("Matière *");
        ComboBox<String> cbMat = new ComboBox<>();
        cbMat.setMaxWidth(Double.MAX_VALUE);
        cbMat.setStyle(INPUT_STYLE);
        store.getSeancesByEnseignant(currentEnseignantNom).stream()
                .map(DataStore.SeanceEntry::getMatiereNom).distinct().sorted()
                .forEach(cbMat.getItems()::add);

        Label lCl = fieldLabel("Classe *");
        ComboBox<String> cbCl = new ComboBox<>();
        cbCl.setMaxWidth(Double.MAX_VALUE);
        cbCl.setStyle(INPUT_STYLE);
        store.getSeancesByEnseignant(currentEnseignantNom).stream()
                .map(DataStore.SeanceEntry::getClasseNom).distinct().sorted()
                .forEach(cbCl.getItems()::add);

        Label lDate = fieldLabel("Date *");
        DatePicker dp = new DatePicker(isEdit ? existing.getDate() : LocalDate.now());
        dp.setMaxWidth(Double.MAX_VALUE);
        dp.setStyle(INPUT_STYLE);

        Label lTitre = fieldLabel("Titre de la leçon *");
        TextField tfTitre = new TextField(isEdit ? existing.getTitre() : "");
        tfTitre.setStyle(INPUT_STYLE);

        Label lContenu = fieldLabel("Contenu / Déroulement *");
        TextArea taContenu = new TextArea(isEdit ? existing.getContenu() : "");
        taContenu.setPrefRowCount(4);
        taContenu.setWrapText(true);
        taContenu.setStyle(INPUT_STYLE);

        Label lDevoirs = fieldLabel("Devoirs à la maison");
        TextArea taDevoirs = new TextArea(isEdit ? existing.getDevoirs() : "");
        taDevoirs.setPrefRowCount(2);
        taDevoirs.setWrapText(true);
        taDevoirs.setStyle(INPUT_STYLE);

        if (isEdit) {
            cbMat.setValue(existing.getMatiereNom());
            cbCl.setValue(existing.getClasseNom());
        }

        Label errLabel = new Label("");
        errLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");

        Button btnSave = new Button(isEdit ? "💾 Enregistrer" : "✅ Ajouter");
        btnSave.setStyle(BTN_PRIMARY);
        btnSave.setOnAction(e -> {
            if (cbMat.getValue() == null || cbCl.getValue() == null || tfTitre.getText().isBlank()) {
                errLabel.setText("Veuillez remplir les champs obligatoires (*)");
                return;
            }
            DataStore.CahierEntry c = isEdit ? existing : new DataStore.CahierEntry();
            c.setEnseignantNom(currentEnseignantNom);
            c.setMatiereNom(cbMat.getValue());
            c.setClasseNom(cbCl.getValue());
            c.setDate(dp.getValue() != null ? dp.getValue() : LocalDate.now());
            c.setTitre(tfTitre.getText().trim());
            c.setContenu(taContenu.getText().trim());
            c.setDevoirs(taDevoirs.getText().trim());
            if (isEdit)
                store.updateCahier(c);
            else
                store.addCahier(c);
            showCahier();
        });

        Button btnCancel = new Button("Annuler");
        btnCancel.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2d3436; -fx-font-weight: bold; "
                + "-fx-background-radius: 7; -fx-padding: 9 20 9 20; -fx-cursor: hand;");
        btnCancel.setOnAction(e -> showCahier());

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(12);
        grid.setMaxWidth(Double.MAX_VALUE);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(c1, c2);
        grid.addRow(0, lMat, lCl);
        grid.addRow(1, cbMat, cbCl);
        grid.addRow(2, lDate, new Label(""));
        grid.addRow(3, dp, new Label(""));

        form.getChildren().addAll(title, grid, lTitre, tfTitre, lContenu, taContenu, lDevoirs, taDevoirs, errLabel,
                new HBox(12, btnSave, btnCancel));

        ScrollPane scroll = wrapInScroll(form);
        mainContent.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
    }

    // ==================== PRÉSENCES ====================

    private void showPresences() {
        setActiveMenu(menuPresences);
        pageTitle.setText("Gestion des Présences");
        mainContent.getChildren().clear();

        // Sélection séance
        VBox selectCard = new VBox(14);
        selectCard.setPadding(new Insets(20));
        selectCard.setStyle(CARD_STYLE);

        Label selectTitle = new Label("📋 Faire l'appel");
        selectTitle.setStyle(SECTION_TITLE_STYLE);

        List<DataStore.SeanceEntry> seances = store.getSeancesByEnseignant(currentEnseignantNom);

        Label lMat = fieldLabel("Matière");
        ComboBox<String> cbMat = new ComboBox<>();
        cbMat.setStyle(INPUT_STYLE);
        seances.stream().map(DataStore.SeanceEntry::getMatiereNom).distinct().sorted()
                .forEach(cbMat.getItems()::add);

        Label lCl = fieldLabel("Classe");
        ComboBox<String> cbCl = new ComboBox<>();
        cbCl.setStyle(INPUT_STYLE);
        seances.stream().map(DataStore.SeanceEntry::getClasseNom).distinct().sorted()
                .forEach(cbCl.getItems()::add);

        Label lDate = fieldLabel("Date");
        DatePicker dp = new DatePicker(LocalDate.now());
        dp.setStyle(INPUT_STYLE);

        VBox appel = new VBox(10);
        appel.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8; -fx-padding: 12;");

        Button btnCharge = new Button("Charger les élèves");
        btnCharge.setStyle(BTN_PRIMARY);
        btnCharge.setOnAction(e -> {
            if (cbMat.getValue() == null || cbCl.getValue() == null)
                return;
            String classe = cbCl.getValue();
            LocalDate date = dp.getValue();
            String mat = cbMat.getValue();

            // Récupérer les étudiants de la classe
            Classe cl = store.getAllClasses().stream()
                    .filter(c -> c.getNom().equals(classe)).findFirst().orElse(null);
            List<Etudiant> etudiants = cl != null
                    ? store.getEtudiantsByClasse(cl.getId())
                    : List.of();

            // Charger présences existantes
            List<DataStore.PresenceEntry> existantes = store.getPresencesBySession(
                    currentEnseignantNom, mat, classe, date);

            appel.getChildren().clear();
            Label appTitle = new Label(
                    "Appel — " + mat + " · " + classe + " · " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            appTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + PRIMARY + ";");
            appel.getChildren().add(appTitle);

            // Map nom -> ComboBox statut
            Map<String, ComboBox<String>> statutMap = new LinkedHashMap<>();

            if (etudiants.isEmpty()) {
                appel.getChildren().add(makePlaceholder("Aucun élève trouvé pour cette classe."));
            } else {
                for (Etudiant et : etudiants) {
                    String nom = et.getNomComplet();
                    HBox row = new HBox(15);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(8, 12, 8, 12));
                    row.setStyle("-fx-background-color: white; -fx-background-radius: 6;");

                    Label lNom = new Label(nom);
                    lNom.setStyle("-fx-font-size: 13px; -fx-text-fill: #2d3436;");
                    Region sp = new Region();
                    HBox.setHgrow(sp, Priority.ALWAYS);

                    ComboBox<String> cbStatut = new ComboBox<>();
                    cbStatut.getItems().addAll("Présent", "Absent", "Retard");
                    // Chercher statut existant
                    String statutExistant = existantes.stream()
                            .filter(p -> p.getEtudiantNom().equals(nom))
                            .map(DataStore.PresenceEntry::getStatut)
                            .findFirst().orElse("Présent");
                    cbStatut.setValue(statutExistant);
                    cbStatut.setStyle(INPUT_STYLE);

                    statutMap.put(nom, cbStatut);
                    row.getChildren().addAll(lNom, sp, cbStatut);
                    appel.getChildren().add(row);
                }

                Button btnSave = new Button("💾 Enregistrer l'appel");
                btnSave.setStyle(BTN_SUCCESS);
                btnSave.setOnAction(ev -> {
                    // Supprimer anciennes présences de cette session
                    store.deletePresencesBySession(currentEnseignantNom, mat, classe, date);
                    // Enregistrer nouvelles
                    for (Map.Entry<String, ComboBox<String>> entry : statutMap.entrySet()) {
                        DataStore.PresenceEntry p = new DataStore.PresenceEntry(
                                currentEnseignantNom, mat, classe, date, entry.getKey(), entry.getValue().getValue());
                        store.addPresence(p);
                    }
                    showAlert("Succès", "Appel enregistré pour " + mat + " · " + classe);
                });
                appel.getChildren().add(btnSave);
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(33);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(33);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(34);
        grid.getColumnConstraints().addAll(c1, c2, c3);
        grid.addRow(0, lMat, lCl, lDate);
        grid.addRow(1, cbMat, cbCl, dp);

        selectCard.getChildren().addAll(selectTitle, grid, btnCharge, appel);

        // Historique des appels
        VBox histCard = new VBox(12);
        histCard.setPadding(new Insets(20));
        histCard.setStyle(CARD_STYLE);
        Label histTitle = new Label("📊 Historique des présences");
        histTitle.setStyle(SECTION_TITLE_STYLE);

        List<DataStore.PresenceEntry> toutes = store.getPresencesByEnseignant(currentEnseignantNom);
        VBox histRows = new VBox(8);
        if (toutes.isEmpty()) {
            histRows.getChildren().add(makePlaceholder("Aucun appel enregistré"));
        } else {
            // Grouper par (matière, classe, date)
            Map<String, List<DataStore.PresenceEntry>> grouped = toutes.stream()
                    .collect(Collectors
                            .groupingBy(p -> p.getMatiereNom() + " | " + p.getClasseNom() + " | " + p.getDate()));
            grouped.entrySet().stream()
                    .sorted(Map.Entry.<String, List<DataStore.PresenceEntry>>comparingByKey().reversed())
                    .forEach(entry -> {
                        List<DataStore.PresenceEntry> list = entry.getValue();
                        long presents = list.stream().filter(p -> "Présent".equals(p.getStatut())).count();
                        HBox row = new HBox(15);
                        row.setAlignment(Pos.CENTER_LEFT);
                        row.setPadding(new Insets(10, 14, 10, 14));
                        row.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");
                        Label lKey = new Label(entry.getKey());
                        lKey.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #2d3436;");
                        Region sp = new Region();
                        HBox.setHgrow(sp, Priority.ALWAYS);
                        Label lCount = new Label(presents + "/" + list.size() + " présents");
                        lCount.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");
                        row.getChildren().addAll(lKey, sp, lCount);
                        histRows.getChildren().add(row);
                    });
        }
        histCard.getChildren().addAll(histTitle, histRows);

        VBox content = new VBox(16, selectCard, histCard);
        ScrollPane scroll = wrapInScroll(content);
        mainContent.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
    }

    // ==================== MES CLASSES ====================

    private void showClasses() {
        setActiveMenu(menuClasses);
        pageTitle.setText("Mes Classes");
        mainContent.getChildren().clear();

        List<DataStore.SeanceEntry> seances = store.getSeancesByEnseignant(currentEnseignantNom);
        Set<String> classesNoms = seances.stream().map(DataStore.SeanceEntry::getClasseNom)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        String[] palette = { "#3498db", "#e67e22", "#9b59b6", "#27ae60", "#e74c3c" };
        VBox container = new VBox(16);
        int ci = 0;
        for (String classeNom : classesNoms) {
            String color = palette[ci % palette.length];
            ci++;

            Classe cl = store.getAllClasses().stream().filter(c -> c.getNom().equals(classeNom)).findFirst()
                    .orElse(null);
            List<Etudiant> etudiants = cl != null ? store.getEtudiantsByClasse(cl.getId()) : List.of();
            List<DataStore.SeanceEntry> seancesClasse = seances.stream()
                    .filter(s -> classeNom.equals(s.getClasseNom()))
                    .collect(Collectors.toList());

            VBox card = new VBox(14);
            card.setPadding(new Insets(20));
            card.setStyle(CARD_STYLE);

            // En-tête
            HBox header = new HBox(12);
            header.setAlignment(Pos.CENTER_LEFT);
            StackPane iconBox = new StackPane();
            Circle circle = new Circle(24);
            circle.setStyle("-fx-fill: " + color + "20; -fx-stroke: " + color + "; -fx-stroke-width: 2;");
            Label icon = new Label("🎓");
            icon.setStyle("-fx-font-size: 18px;");
            iconBox.getChildren().addAll(circle, icon);

            VBox infos = new VBox(3);
            Label lNom = new Label(classeNom);
            lNom.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");
            HBox badges2 = new HBox(8);
            badges2.getChildren().addAll(
                    makeBadge(etudiants.size() + " élèves", color),
                    makeBadge(seancesClasse.size() + " séances/sem", "#636e72"));
            infos.getChildren().addAll(lNom, badges2);
            HBox.setHgrow(infos, Priority.ALWAYS);

            // Matières
            Set<String> matieres = seancesClasse.stream().map(DataStore.SeanceEntry::getMatiereNom)
                    .collect(Collectors.toSet());
            Label lMat = new Label("Matières : " + String.join(", ", matieres));
            lMat.setStyle("-fx-font-size: 11px; -fx-text-fill: #636e72;");
            lMat.setWrapText(true);

            header.getChildren().addAll(iconBox, infos);
            card.getChildren().addAll(header, lMat);

            // Liste élèves (10 max)
            if (!etudiants.isEmpty()) {
                TitledPane tp = new TitledPane("Voir les élèves (" + etudiants.size() + ")",
                        buildElevesList(etudiants, color));
                tp.setExpanded(false);
                tp.setStyle("-fx-font-size: 12px; -fx-text-fill: " + PRIMARY + ";");
                card.getChildren().add(tp);
            }

            container.getChildren().add(card);
        }

        if (container.getChildren().isEmpty()) {
            container.getChildren().add(makePlaceholder("Aucune classe attribuée."));
        }

        ScrollPane scroll = wrapInScroll(container);
        mainContent.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
    }

    private VBox buildElevesList(List<Etudiant> etudiants, String color) {
        VBox box = new VBox(6);
        box.setPadding(new Insets(8));
        for (Etudiant et : etudiants) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(7, 12, 7, 12));
            row.setStyle("-fx-background-color: " + color + "10; -fx-background-radius: 6;");
            Label lNom = new Label(et.getNomComplet());
            lNom.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);
            Label lMat = new Label(et.getMatricule() != null ? et.getMatricule() : "");
            lMat.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
            row.getChildren().addAll(lNom, sp, lMat);
            box.getChildren().add(row);
        }
        return box;
    }

    // ==================== HELPERS ====================

    private VBox createStatCard(String titre, String valeur, String icone, String color) {
        VBox card = new VBox(8);
        card.setPrefWidth(210);
        card.setPrefHeight(100);
        card.setPadding(new Insets(18));
        card.setStyle(CARD_STYLE);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label t = new Label(titre);
        t.setStyle("-fx-font-size: 10px; -fx-text-fill: #95a5a6; -fx-font-weight: bold;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label ic = new Label(icone);
        ic.setStyle("-fx-font-size: 18px;");
        header.getChildren().addAll(t, sp, ic);

        Label v = new Label(valeur);
        v.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        card.getChildren().addAll(header, v);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    // createSectionCard n'est plus utilisée directement, les cartes sont
    // construites inline

    private HBox makeCourseRow(String heure, String matiere, String classe, String salle) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 12, 10, 12));
        row.setStyle("-fx-background-color: #f5f6fa; -fx-background-radius: 7;");

        Label h = new Label(heure);
        h.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #636e72;");
        h.setMinWidth(90);

        VBox info = new VBox(2);
        Label m = new Label(matiere);
        m.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
        Label sub = new Label(classe + " · " + salle);
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #b2bec3;");
        info.getChildren().addAll(m, sub);
        HBox.setHgrow(info, Priority.ALWAYS);
        row.getChildren().addAll(h, info);
        return row;
    }

    private HBox makeRapportRow(DataStore.RapportSeanceEntry r) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 12, 10, 12));
        row.setStyle("-fx-background-color: #f5f6fa; -fx-background-radius: 7;");

        VBox info = new VBox(3);
        Label m = new Label(r.getMatiereNom());
        m.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
        Label sub = new Label(r.getContenu());
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #636e72;");
        sub.setMaxWidth(200);
        info.getChildren().addAll(m, sub);
        HBox.setHgrow(info, Priority.ALWAYS);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label statut = new Label(r.getStatut());
        String sc = "Complété".equals(r.getStatut())
                ? "-fx-background-color: #27ae60; -fx-text-fill: white;"
                : "-fx-background-color: #f39c12; -fx-text-fill: #333;";
        statut.setStyle(sc + " -fx-padding: 2 8 2 8; -fx-background-radius: 10; -fx-font-size: 10px;");

        row.getChildren().addAll(info, sp, statut);
        return row;
    }

    private VBox makeRapportCardFull(DataStore.RapportSeanceEntry r) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);
        Label mat = new Label(r.getMatiereNom());
        mat.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + PRIMARY + ";");
        Label cl = makeBadge(r.getClasseNom(), "#2c3e6b");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label statut = new Label(r.getStatut());
        String sc = "Complété".equals(r.getStatut())
                ? "-fx-background-color: #27ae60; -fx-text-fill: white;"
                : "-fx-background-color: #f39c12; -fx-text-fill: #333;";
        statut.setStyle(
                sc + " -fx-padding: 3 10 3 10; -fx-background-radius: 12; -fx-font-size: 10px; -fx-font-weight: bold;");

        top.getChildren().addAll(mat, cl, sp, statut);

        Label lDate = new Label(r.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lDate.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
        Label lContenu = new Label("📌 " + r.getContenu());
        lContenu.setStyle("-fx-font-size: 12px; -fx-text-fill: #2d3436;");
        lContenu.setWrapText(true);

        HBox actions = new HBox(8);
        Button btnEdit = new Button("✏️ Modifier");
        btnEdit.setStyle(BTN_PRIMARY + " -fx-font-size: 11px; -fx-padding: 5 12 5 12;");
        btnEdit.setOnAction(e -> showFormulaireRapport(r));
        Button btnDel = new Button("🗑️ Supprimer");
        btnDel.setStyle(BTN_DANGER + " -fx-font-size: 11px; -fx-padding: 5 12 5 12;");
        btnDel.setOnAction(e -> {
            store.deleteRapport(r.getId());
            showRapports();
        });
        actions.getChildren().addAll(btnEdit, btnDel);

        card.getChildren().addAll(top, lDate, lContenu, actions);
        return card;
    }

    private VBox makeCahierCard(DataStore.CahierEntry c) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);
        Label mat = new Label(c.getMatiereNom());
        mat.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + PRIMARY + ";");
        Label cl = makeBadge(c.getClasseNom(), "#2c3e6b");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label lDate = new Label(c.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lDate.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
        top.getChildren().addAll(mat, cl, sp, lDate);

        Label titre = new Label("📖 " + c.getTitre());
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #2d3436;");
        Label contenu = new Label(c.getContenu());
        contenu.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");
        contenu.setWrapText(true);

        HBox devoirsBox = new HBox(6);
        if (c.getDevoirs() != null && !c.getDevoirs().isBlank()) {
            Label dTag = new Label("📝 Devoirs :");
            dTag.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");
            Label d = new Label(c.getDevoirs());
            d.setStyle("-fx-font-size: 11px; -fx-text-fill: #636e72;");
            d.setWrapText(true);
            devoirsBox.getChildren().addAll(dTag, d);
        }

        HBox actions = new HBox(8);
        Button btnEdit = new Button("✏️ Modifier");
        btnEdit.setStyle(BTN_PRIMARY + " -fx-font-size: 11px; -fx-padding: 5 12 5 12;");
        btnEdit.setOnAction(e -> showFormulaireCahier(c));
        Button btnDel = new Button("🗑️ Supprimer");
        btnDel.setStyle(BTN_DANGER + " -fx-font-size: 11px; -fx-padding: 5 12 5 12;");
        btnDel.setOnAction(e -> {
            store.deleteCahier(c.getId());
            showCahier();
        });
        actions.getChildren().addAll(btnEdit, btnDel);

        card.getChildren().addAll(top, titre, contenu);
        if (!devoirsBox.getChildren().isEmpty())
            card.getChildren().add(devoirsBox);
        card.getChildren().add(actions);
        return card;
    }

    private Label makeBadge(String text, String color) {
        Label badge = new Label(text);
        badge.setStyle("-fx-background-color: " + color + "20; -fx-text-fill: " + color + "; "
                + "-fx-padding: 2 8 2 8; -fx-background-radius: 10; -fx-font-size: 11px; -fx-font-weight: bold;");
        return badge;
    }

    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #636e72;");
        return l;
    }

    private Node makePlaceholder(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 13px; -fx-font-style: italic;");
        HBox box = new HBox(l);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        return box;
    }

    private ScrollPane wrapInScroll(Node content) {
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: " + BG_COLOR + "; -fx-background-color: " + BG_COLOR
                + "; -fx-border-color: transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);
        return sp;
    }

    private void showAlert(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
