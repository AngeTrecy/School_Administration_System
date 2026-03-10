package com.example.school_administration_system.controller;

import com.example.school_administration_system.model.*;
import com.example.school_administration_system.service.DataStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private StackPane contentArea;
    @FXML
    private VBox sidebarMenu;
    @FXML
    private VBox subMenuEtudiants;
    @FXML
    private VBox subMenuClasses;
    @FXML
    private VBox subMenuEnseignants;
    @FXML
    private VBox subMenuEmploiDuTemps;
    @FXML
    private VBox subMenuCommunication;
    @FXML
    private VBox subMenuPaiements;
    @FXML
    private HBox menuAccueil;
    @FXML
    private HBox menuEtudiants;
    @FXML
    private HBox menuClasses;
    @FXML
    private HBox menuEnseignants;
    @FXML
    private HBox menuEmploiDuTemps;
    @FXML
    private HBox menuCommunication;
    @FXML
    private HBox menuPaiements;
    @FXML
    private HBox menuMatieres;
    @FXML
    private HBox menuStatistiques;
    @FXML
    private HBox menuSuiviActivite;
    @FXML
    private VBox subMenuSuiviActivite;
    @FXML
    private VBox sidebarContainer;
    @FXML
    private VBox sidebarLogo;
    @FXML
    private ScrollPane sidebarScrollPane;
    @FXML
    private HBox sidebarTitleBar;
    @FXML
    private Label sidebarIconLabel;
    @FXML
    private Label sidebarTitleLabel;
    @FXML
    private Region sidebarSpacer;

    private boolean sidebarExpanded = true;

    private final DataStore dataStore = DataStore.getInstance();

    // ===== CONSTANTES DE DESIGN =====
    private static final String CARD_STYLE = "-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(48,79,109,0.06), 8, 0, 0, 2);";
    private static final String CARD_HOVER = "-fx-background-color: #FFF8EB; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(224,125,84,0.15), 12, 0, 0, 3); -fx-cursor: hand;";
    private static final String ITEM_BG = "-fx-background-color: #f5f8fa; -fx-background-radius: 8;";
    private static final String BTN_CANCEL = "-fx-background-color: #E07D54; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 25; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String BTN_SUBMIT = "-fx-background-color: #304F6D; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 25; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String BTN_SUCCESS = "-fx-background-color: #899481; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 15; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String MENU_DEFAULT = "-fx-background-color: transparent; -fx-cursor: hand; -fx-min-height: 40; -fx-max-height: 40; -fx-background-radius: 8;";
    private static final String MENU_ACTIVE = "-fx-background-color: #E07D54; -fx-cursor: hand; -fx-min-height: 40; -fx-max-height: 40; -fx-background-radius: 8;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAccueil();
        setActiveMenu(menuAccueil);
    }
    // ==================== SIDEBAR TOGGLE ====================

    @FXML
    private void handleToggleSidebar() {
        sidebarExpanded = !sidebarExpanded;
        if (sidebarExpanded) {
            sidebarContainer.setPrefWidth(220);
            sidebarContainer.setMaxWidth(220);
            sidebarIconLabel.setVisible(true);
            sidebarIconLabel.setManaged(true);
            sidebarTitleLabel.setVisible(true);
            sidebarTitleLabel.setManaged(true);
            sidebarSpacer.setVisible(true);
            sidebarSpacer.setManaged(true);
            sidebarLogo.setVisible(true);
            sidebarLogo.setManaged(true);
            sidebarScrollPane.setVisible(true);
            sidebarScrollPane.setManaged(true);
        } else {
            sidebarContainer.setPrefWidth(50);
            sidebarContainer.setMaxWidth(50);
            sidebarIconLabel.setVisible(false);
            sidebarIconLabel.setManaged(false);
            sidebarTitleLabel.setVisible(false);
            sidebarTitleLabel.setManaged(false);
            sidebarSpacer.setVisible(false);
            sidebarSpacer.setManaged(false);
            sidebarLogo.setVisible(false);
            sidebarLogo.setManaged(false);
            sidebarScrollPane.setVisible(false);
            sidebarScrollPane.setManaged(false);
            // Fermer tous les sous-menus
            subMenuEtudiants.setVisible(false);
            subMenuEtudiants.setManaged(false);
            subMenuClasses.setVisible(false);
            subMenuClasses.setManaged(false);
            subMenuEnseignants.setVisible(false);
            subMenuEnseignants.setManaged(false);
            subMenuEmploiDuTemps.setVisible(false);
            subMenuEmploiDuTemps.setManaged(false);
            subMenuCommunication.setVisible(false);
            subMenuCommunication.setManaged(false);
            subMenuPaiements.setVisible(false);
            subMenuPaiements.setManaged(false);
            subMenuSuiviActivite.setVisible(false);
            subMenuSuiviActivite.setManaged(false);
        }
    }

    // ==================== SOUS-MENUS ====================

    private void toggleSubMenu(VBox subMenu) {
        boolean isVisible = subMenu.isVisible();
        VBox[] allSubs = { subMenuEtudiants, subMenuClasses, subMenuEnseignants, subMenuEmploiDuTemps,
                subMenuCommunication, subMenuPaiements, subMenuSuiviActivite };
        for (VBox sub : allSubs) {
            sub.setVisible(false);
            sub.setManaged(false);
        }
        if (!isVisible) {
            subMenu.setVisible(true);
            subMenu.setManaged(true);
        }
    }

    private void setActiveMenu(HBox activeItem) {
        HBox[] menus = { menuAccueil, menuEtudiants, menuClasses, menuEnseignants, menuEmploiDuTemps,
                menuCommunication, menuPaiements, menuMatieres, menuStatistiques, menuSuiviActivite };
        for (HBox m : menus) {
            if (m != null)
                m.setStyle(MENU_DEFAULT);
        }
        if (activeItem != null)
            activeItem.setStyle(MENU_ACTIVE);
    }

    // ==================== NAVIGATION ====================

    @FXML
    private void handleAccueil() {
        setActiveMenu(menuAccueil);
        showAccueil();
    }

    @FXML
    private void handleEtudiants() {
        setActiveMenu(menuEtudiants);
        toggleSubMenu(subMenuEtudiants);
    }

    @FXML
    private void handleClasses() {
        setActiveMenu(menuClasses);
        toggleSubMenu(subMenuClasses);
    }

    @FXML
    private void handleEnseignants() {
        setActiveMenu(menuEnseignants);
        toggleSubMenu(subMenuEnseignants);
    }

    @FXML
    private void handleEmploiDuTemps() {
        setActiveMenu(menuEmploiDuTemps);
        toggleSubMenu(subMenuEmploiDuTemps);
    }

    @FXML
    private void handleMatieres() {
        setActiveMenu(menuMatieres);
        showListeMatieres();
    }

    @FXML
    private void handleStatistiques() {
        setActiveMenu(menuStatistiques);
        showStatistiques();
    }

    @FXML
    private void handleNotifications() {
        showAlert("Notifications", "Aucune nouvelle notification.");
    }

    @FXML
    private void handleAjouterEtudiant() {
        showFormulaireEtudiant(null);
    }

    @FXML
    private void handleListeEtudiants() {
        showListeEtudiants();
    }

    @FXML
    private void handleCreerClasse() {
        showFormulaireClasse(null);
    }

    @FXML
    private void handleListeClasses() {
        showListeClasses();
    }

    @FXML
    private void handleAffecterEnseignant() {
        showFormulaireEnseignant(null);
    }

    @FXML
    private void handleListeEnseignants() {
        showListeEnseignants();
    }

    @FXML
    private void handleCreerEmploiDuTemps() {
        showFormulaireEmploiDuTemps();
    }

    @FXML
    private void handleVoirEmploiDuTemps() {
        showEmploiDuTemps();
    }

    @FXML
    private void handleCommunication() {
        setActiveMenu(menuCommunication);
        toggleSubMenu(subMenuCommunication);
    }

    @FXML
    private void handleEnvoyerMails() {
        showEnvoyerMails();
    }

    @FXML
    private void handleListerContacts() {
        showListerContacts();
    }

    @FXML
    private void handleEnvoyerSMS() {
        showEnvoyerSMS();
    }

    @FXML
    private void handlePaiements() {
        setActiveMenu(menuPaiements);
        toggleSubMenu(subMenuPaiements);
    }

    @FXML
    private void handleNouveauPaiement() {
        showFormulairePaiement();
    }

    @FXML
    private void handleListePaiements() {
        showListePaiements();
    }

    @FXML
    private void handleGenererFacture() {
        showGenererFacture();
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            Stage stage = (Stage) contentArea.getScene().getWindow();
            stage.setTitle("Système de Gestion Scolaire - Connexion");
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSuiviActivite() {
        setActiveMenu(menuSuiviActivite);
        toggleSubMenu(subMenuSuiviActivite);
    }

    @FXML
    private void handlePresencesAdmin() {
        setActiveMenu(menuSuiviActivite);
        showPresencesAdmin();
    }

    @FXML
    private void handleRapportsAdmin() {
        setActiveMenu(menuSuiviActivite);
        showRapportsAdmin();
    }

    @FXML
    private void handleCahierAdmin() {
        setActiveMenu(menuSuiviActivite);
        showCahierAdmin();
    }

    // ====================================================================
    // PAGE D'ACCUEIL
    // ====================================================================

    @SuppressWarnings("unchecked")
    private void showAccueil() {
        contentArea.getChildren().clear();
        VBox page = new VBox(20);
        page.setPadding(new Insets(20));
        page.setAlignment(Pos.TOP_LEFT);

        // === Bannière de bienvenue ===
        HBox welcome = new HBox(15);
        welcome.setAlignment(Pos.CENTER_LEFT);
        welcome.setPadding(new Insets(20, 25, 20, 25));
        welcome.setStyle(
                "-fx-background-color: linear-gradient(to right, #304F6D, #899481); -fx-background-radius: 12;");

        VBox welcomeText = new VBox(5);
        Label welcomeTitle = new Label("Bienvenue, Administrateur !");
        welcomeTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label welcomeSub = new Label("Tableau de bord — Année académique 2025-2026");
        welcomeSub.setStyle("-fx-font-size: 13px; -fx-text-fill: #d1d5db;");
        Label dateLabel = new Label(java.time.LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", java.util.Locale.FRENCH)));
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #a5b4fc;");
        welcomeText.getChildren().addAll(welcomeTitle, welcomeSub, dateLabel);
        welcome.getChildren().add(welcomeText);

        // === Cartes statistiques ===
        HBox statsRow = new HBox(15);
        statsRow.setAlignment(Pos.CENTER);
        statsRow.getChildren().addAll(
                createDashboardStatCard("🎓", "Étudiants", String.valueOf(dataStore.getTotalEtudiants()), "#6c5ce7",
                        e -> showListeEtudiants()),
                createDashboardStatCard("📖", "Classes", String.valueOf(dataStore.getTotalClasses()), "#0984e3",
                        e -> showListeClasses()),
                createDashboardStatCard("👨‍🏫", "Enseignants", String.valueOf(dataStore.getTotalEnseignants()),
                        "#00b894", e -> showListeEnseignants()),
                createDashboardStatCard("📚", "Matières", String.valueOf(dataStore.getTotalMatieres()), "#e17055",
                        e -> showListeMatieres()),
                createDashboardStatCard("💰", "Paiements", String.valueOf(dataStore.getNombrePaiements()), "#fdcb6e",
                        e -> showListePaiements()));

        // === Section inférieure : 2 colonnes ===
        HBox bottomSection = new HBox(20);
        bottomSection.setAlignment(Pos.TOP_CENTER);

        // --- Colonne gauche : Effectif par classe ---
        VBox classeBox = new VBox(10);
        classeBox.setStyle(CARD_STYLE);
        classeBox.setPadding(new Insets(20));
        classeBox.setPrefWidth(450);
        Label classeTitle = new Label("📊 Effectif par classe");
        classeTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
        classeBox.getChildren().add(classeTitle);
        for (Classe c : dataStore.getAllClasses()) {
            int eff = dataStore.getEffectifClasse(c.getId());
            double pct = c.getCapaciteMax() > 0 ? (eff * 100.0 / c.getCapaciteMax()) : 0;
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            Label name = new Label(c.getNom());
            name.setPrefWidth(70);
            name.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
            ProgressBar bar = new ProgressBar(pct / 100.0);
            bar.setPrefWidth(220);
            bar.setPrefHeight(16);
            Label cnt = new Label(eff + "/" + c.getCapaciteMax());
            cnt.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");
            row.getChildren().addAll(name, bar, cnt);
            classeBox.getChildren().add(row);
        }

        // --- Colonne droite : Derniers paiements ---
        VBox paiementBox = new VBox(10);
        paiementBox.setStyle(CARD_STYLE);
        paiementBox.setPadding(new Insets(20));
        paiementBox.setPrefWidth(450);
        HBox.setHgrow(paiementBox, javafx.scene.layout.Priority.ALWAYS);
        Label paiementTitle = new Label("💳 Derniers paiements");
        paiementTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
        paiementBox.getChildren().add(paiementTitle);

        TableView<Paiement> recentTable = new TableView<>();
        recentTable.setStyle("-fx-border-color: #eee;");
        recentTable.setPrefHeight(180);
        recentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Paiement, String> rEtCol = new TableColumn<>("ÉTUDIANT");
        rEtCol.setCellValueFactory(d -> {
            Etudiant et = dataStore.getEtudiantById(d.getValue().getEtudiantId());
            return new SimpleStringProperty(et != null ? et.getNomComplet() : "ID:" + d.getValue().getEtudiantId());
        });
        TableColumn<Paiement, String> rTypeCol = new TableColumn<>("TYPE");
        rTypeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTypePaiement()));
        TableColumn<Paiement, String> rMontCol = new TableColumn<>("MONTANT");
        rMontCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.format("%,.0f FCFA", d.getValue().getMontant())));
        TableColumn<Paiement, String> rDateCol = new TableColumn<>("DATE");
        rDateCol.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getDatePaiement() != null
                        ? d.getValue().getDatePaiement().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : ""));

        recentTable.getColumns().addAll(rEtCol, rTypeCol, rMontCol, rDateCol);
        recentTable.setItems(FXCollections.observableArrayList(dataStore.getRecentPaiements(5)));
        paiementBox.getChildren().add(recentTable);

        bottomSection.getChildren().addAll(classeBox, paiementBox);

        page.getChildren().addAll(welcome, statsRow, bottomSection);
        contentArea.getChildren().add(page);
    }

    private VBox createDashboardStatCard(String icon, String title, String count, String color,
            javafx.event.EventHandler<javafx.scene.input.MouseEvent> onClick) {
        VBox card = new VBox(6);
        card.setPrefSize(160, 110);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setStyle(CARD_STYLE + " -fx-cursor: hand;");
        HBox.setHgrow(card, javafx.scene.layout.Priority.ALWAYS);

        Label countLabel = new Label(count);
        countLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 20px;");

        card.getChildren().addAll(iconLabel, countLabel, titleLabel);
        card.setOnMouseEntered(e -> card.setStyle(CARD_HOVER));
        card.setOnMouseExited(e -> card.setStyle(CARD_STYLE + " -fx-cursor: hand;"));
        card.setOnMouseClicked(onClick);
        return card;
    }

    // ====================================================================
    // CRUD ÉTUDIANTS
    // ====================================================================

    private void showFormulaireEtudiant(Etudiant existing) {
        contentArea.getChildren().clear();
        boolean isEdit = existing != null;

        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(900);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());

        Label titre = createPageTitle(isEdit ? "MODIFIER ÉTUDIANT" : "INSCRIPTION D'UN ÉTUDIANT");
        page.getChildren().add(titre);

        GridPane form = new GridPane();
        form.setHgap(30);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        // === Section: Identité de l'élève ===
        Label sectionIdentite = new Label("— Identité de l'élève —");
        sectionIdentite.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #E07D54;");
        sectionIdentite.setPadding(new Insets(5, 0, 5, 0));

        TextField matriculeField = createFormField(isEdit ? existing.getMatricule() : "");
        matriculeField.setPromptText("Ex: 22SI4831");
        TextField nomField = createFormField(isEdit ? existing.getNom() : "");
        nomField.setPromptText("Nom de famille");
        TextField prenomField = createFormField(isEdit ? existing.getPrenom() : "");
        prenomField.setPromptText("Prénom(s)");

        DatePicker dateNaissance = new DatePicker(isEdit ? existing.getDateNaissance() : null);
        dateNaissance.setPrefWidth(250);
        dateNaissance.setPrefHeight(35);
        dateNaissance.setPromptText("JJ/MM/AAAA");
        TextField lieuNaissanceField = createFormField(isEdit ? existing.getAdresse() : "");
        lieuNaissanceField.setPromptText("Ex: Yaoundé");

        // === Section: Informations des parents ===
        Label sectionParents = new Label("— Informations des parents / tuteur —");
        sectionParents.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #E07D54;");
        sectionParents.setPadding(new Insets(10, 0, 5, 0));

        TextField nomPereField = createFormField(isEdit ? existing.getNomPere() : "");
        nomPereField.setPromptText("Nom complet du père");
        TextField nomMereField = createFormField(isEdit ? existing.getNomMere() : "");
        nomMereField.setPromptText("Nom complet de la mère");
        TextField telParentField = createFormField(isEdit ? existing.getTelephoneParent() : "");
        telParentField.setPromptText("Ex: 677112233");

        // === Section: Contact de l'élève (optionnel) ===
        Label sectionContact = new Label("— Contact de l'élève (optionnel) —");
        sectionContact.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #E07D54;");
        sectionContact.setPadding(new Insets(10, 0, 5, 0));

        TextField contactField = createFormField(isEdit ? existing.getTelephone() : "");
        contactField.setPromptText("Téléphone de l'élève (optionnel)");
        TextField emailField = createFormField(isEdit ? existing.getEmail() : "");
        emailField.setPromptText("exemple@email.com");

        // === Section: Informations scolaires ===
        Label sectionScolaire = new Label("— Informations scolaires —");
        sectionScolaire.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #E07D54;");
        sectionScolaire.setPadding(new Insets(10, 0, 5, 0));

        ComboBox<String> niveauCombo = new ComboBox<>();
        niveauCombo.getItems().addAll("Sixième", "Cinquième", "Quatrième", "Troisième", "Seconde", "Première",
                "Terminale");
        niveauCombo.setPromptText("Sélectionner le niveau");
        niveauCombo.setPrefWidth(250);
        niveauCombo.setPrefHeight(35);

        ComboBox<String> classeCombo = new ComboBox<>();
        classeCombo.setPromptText("Sélectionner une classe");
        classeCombo.setPrefWidth(250);
        classeCombo.setPrefHeight(35);
        List<Classe> allClasses = dataStore.getAllClasses();
        for (Classe c : allClasses) {
            classeCombo.getItems().add(c.getId() + " - " + c.getNom());
        }
        if (isEdit) {
            int cid = dataStore.getClasseIdForEtudiant(existing.getId());
            if (cid > 0) {
                Classe c = dataStore.getClasseById(cid);
                if (c != null)
                    classeCombo.setValue(c.getId() + " - " + c.getNom());
            }
        }

        int row = 0;
        // -- Section Identité --
        form.add(sectionIdentite, 0, row, 4, 1);
        row++;
        // Row: Matricule | Nom
        form.add(createFormLabel("Matricule"), 0, row);
        form.add(matriculeField, 1, row);
        form.add(createFormLabel("Nom"), 2, row);
        form.add(nomField, 3, row);
        row++;
        // Row: Prénom | Date de naissance
        form.add(createFormLabel("Prénom"), 0, row);
        form.add(prenomField, 1, row);
        form.add(createFormLabel("Date de naissance"), 2, row);
        form.add(dateNaissance, 3, row);
        row++;
        // Row: Lieu de naissance
        form.add(createFormLabel("Lieu de naissance"), 0, row);
        form.add(lieuNaissanceField, 1, row);
        row++;

        // -- Section Parents --
        form.add(sectionParents, 0, row, 4, 1);
        row++;
        // Row: Nom du père | Nom de la mère
        form.add(createFormLabel("Nom du père"), 0, row);
        form.add(nomPereField, 1, row);
        form.add(createFormLabel("Nom de la mère"), 2, row);
        form.add(nomMereField, 3, row);
        row++;
        // Row: Tél. Parent | Email Parent
        form.add(createFormLabel("Tél. Parent *"), 0, row);
        form.add(telParentField, 1, row);
        form.add(createFormLabel("Email Parent"), 2, row);
        form.add(emailField, 3, row);
        row++;

        // -- Section Contact élève (optionnel) --
        form.add(sectionContact, 0, row, 4, 1);
        row++;
        // Row: Téléphone élève | Mot de passe
        form.add(createFormLabel("Tél. Élève"), 0, row);
        form.add(contactField, 1, row);
        PasswordField etudPasswordField = new PasswordField();
        etudPasswordField.setPromptText(isEdit ? "Laisser vide pour garder l'ancien" : "Mot de passe");
        etudPasswordField.setPrefHeight(35);
        etudPasswordField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #dee2e6;");
        form.add(createFormLabel("Mot de passe *"), 2, row);
        form.add(etudPasswordField, 3, row);
        row++;

        // -- Section Scolaire --
        form.add(sectionScolaire, 0, row, 4, 1);
        row++;
        // Row: Niveau | Classe
        form.add(createFormLabel("Niveau"), 0, row);
        form.add(niveauCombo, 1, row);
        form.add(createFormLabel("Classe"), 2, row);
        form.add(classeCombo, 3, row);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 12px;");

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(15, 0, 25, 0));

        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showListeEtudiants());

        Button submitBtn = new Button(isEdit ? "Modifier" : "Inscrire");
        submitBtn.setStyle(BTN_SUBMIT);
        submitBtn.setOnAction(e -> {
            if (nomField.getText().trim().isEmpty()) {
                errorLabel.setText("Le nom est obligatoire.");
                return;
            }
            if (prenomField.getText().trim().isEmpty()) {
                errorLabel.setText("Le prénom est obligatoire.");
                return;
            }
            if (dateNaissance.getValue() == null) {
                errorLabel.setText("La date de naissance est obligatoire.");
                return;
            }
            if (telParentField.getText().trim().isEmpty()) {
                errorLabel.setText("Le numéro de téléphone du parent est obligatoire.");
                return;
            }
            if (!isEdit && etudPasswordField.getText().trim().isEmpty()) {
                errorLabel.setText("Le mot de passe est obligatoire.");
                return;
            }
            if (isEdit) {
                existing.setMatricule(matriculeField.getText().trim());
                existing.setNom(nomField.getText().trim());
                existing.setPrenom(prenomField.getText().trim());
                existing.setEmail(emailField.getText().trim());
                if (!etudPasswordField.getText().trim().isEmpty()) {
                    existing.setMotDePasse(etudPasswordField.getText().trim());
                }
                existing.setTelephone(contactField.getText().trim());
                existing.setDateNaissance(dateNaissance.getValue());
                existing.setAdresse(lieuNaissanceField.getText().trim());
                existing.setNomPere(nomPereField.getText().trim());
                existing.setNomMere(nomMereField.getText().trim());
                existing.setTelephoneParent(telParentField.getText().trim());
                dataStore.updateEtudiant(existing);
                if (classeCombo.getValue() != null) {
                    int cid = Integer.parseInt(classeCombo.getValue().split(" - ")[0]);
                    dataStore.setEtudiantClasse(existing.getId(), cid);
                }
                showAlert("Succès", "Étudiant modifié avec succès !");
            } else {
                Etudiant newEtud = new Etudiant(
                        nomField.getText().trim(), prenomField.getText().trim(),
                        emailField.getText().trim(), etudPasswordField.getText().trim(),
                        contactField.getText().trim(), matriculeField.getText().trim(),
                        dateNaissance.getValue(),
                        lieuNaissanceField.getText().trim(),
                        nomPereField.getText().trim(), nomMereField.getText().trim(),
                        telParentField.getText().trim());
                int classeId = 0;
                if (classeCombo.getValue() != null) {
                    classeId = Integer.parseInt(classeCombo.getValue().split(" - ")[0]);
                }
                dataStore.addEtudiant(newEtud, classeId);
                showAlert("Succès", "Étudiant inscrit !\nLogin : " + emailField.getText().trim()
                        + "\nMot de passe : " + etudPasswordField.getText().trim());
            }
            showListeEtudiants();
        });

        buttons.getChildren().addAll(cancelBtn, submitBtn);
        page.getChildren().addAll(form, errorLabel, buttons);
        contentArea.getChildren().add(page);
    }

    @SuppressWarnings("unchecked")
    private void showListeEtudiants() {
        contentArea.getChildren().clear();
        VBox page = createListPage("LISTE DES ÉTUDIANTS");

        // Barre supérieure : filtre + bouton ajouter
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 10, 0));

        Label filterLabel = new Label("Filtrer par classe :");
        filterLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        ComboBox<String> classeFilter = new ComboBox<>();
        classeFilter.getItems().add("Toutes les classes");
        for (Classe c : dataStore.getAllClasses())
            classeFilter.getItems().add(c.getNom());
        classeFilter.setValue("Toutes les classes");
        classeFilter.setPrefHeight(35);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        Button addBtn = new Button("+ Inscrire un étudiant");
        addBtn.setStyle(BTN_SUCCESS);
        addBtn.setOnAction(e -> showFormulaireEtudiant(null));
        topBar.getChildren().addAll(filterLabel, classeFilter, spacer, addBtn);

        // Tableau
        TableView<Etudiant> table = new TableView<>();
        table.setStyle("-fx-border-color: #ddd;");
        table.setPrefHeight(450);

        TableColumn<Etudiant, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(
                data -> new SimpleStringProperty(String.valueOf(table.getItems().indexOf(data.getValue()) + 1)));
        numCol.setPrefWidth(40);

        TableColumn<Etudiant, String> matricCol = new TableColumn<>("MATRICULE");
        matricCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMatricule()));
        matricCol.setPrefWidth(100);

        TableColumn<Etudiant, String> nomCol = new TableColumn<>("NOM");
        nomCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
        nomCol.setPrefWidth(120);

        TableColumn<Etudiant, String> prenomCol = new TableColumn<>("PRÉNOM");
        prenomCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));
        prenomCol.setPrefWidth(120);

        TableColumn<Etudiant, String> classeCol = new TableColumn<>("CLASSE");
        classeCol.setCellValueFactory(
                data -> new SimpleStringProperty(dataStore.getClasseNameForEtudiant(data.getValue().getId())));
        classeCol.setPrefWidth(80);

        TableColumn<Etudiant, String> contactCol = new TableColumn<>("TÉL. PARENT");
        contactCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelephoneParent()));
        contactCol.setPrefWidth(110);

        TableColumn<Etudiant, String> emailCol = new TableColumn<>("EMAIL");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        emailCol.setPrefWidth(160);

        TableColumn<Etudiant, Void> actionCol = new TableColumn<>("ACTION");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                HBox actions = new HBox(5);
                actions.setAlignment(Pos.CENTER);
                Label editBtn = new Label("✏");
                editBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #2980b9;");
                editBtn.setOnMouseClicked(e -> showFormulaireEtudiant(getTableView().getItems().get(getIndex())));
                Label deleteBtn = new Label("🗑");
                deleteBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #e74c3c;");
                deleteBtn.setOnMouseClicked(e -> {
                    Etudiant et = getTableView().getItems().get(getIndex());
                    if (confirmDelete("Supprimer l'étudiant " + et.getNomComplet() + " ?")) {
                        dataStore.deleteEtudiant(et.getId());
                        showListeEtudiants();
                    }
                });
                actions.getChildren().addAll(editBtn, deleteBtn);
                setGraphic(actions);
            }
        });

        table.getColumns().addAll(numCol, matricCol, nomCol, prenomCol, classeCol, contactCol, emailCol, actionCol);

        Label countLabel = new Label("");
        countLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12px;");
        countLabel.setPadding(new Insets(5, 0, 0, 0));

        // Logique du filtre
        Runnable applyFilter = () -> {
            String selected = classeFilter.getValue();
            List<Etudiant> filtered;
            if (selected == null || "Toutes les classes".equals(selected)) {
                filtered = dataStore.getAllEtudiants();
            } else {
                Classe cls = dataStore.getAllClasses().stream()
                        .filter(c -> c.getNom().equals(selected)).findFirst().orElse(null);
                filtered = cls != null ? dataStore.getEtudiantsByClasse(cls.getId()) : dataStore.getAllEtudiants();
            }
            table.setItems(FXCollections.observableArrayList(filtered));
            countLabel.setText("Total : " + filtered.size() + " étudiant(s)");
        };
        classeFilter.setOnAction(e -> applyFilter.run());
        applyFilter.run();

        page.getChildren().addAll(topBar, table, countLabel);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // CRUD CLASSES
    // ====================================================================

    private void showFormulaireClasse(Classe existing) {
        contentArea.getChildren().clear();
        boolean isEdit = existing != null;

        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(600);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle(isEdit ? "MODIFIER LA CLASSE" : "CRÉER UNE CLASSE"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        TextField nomField = createFormField(isEdit ? existing.getNom() : "");
        nomField.setPromptText("Ex: Tle C");

        ComboBox<String> niveauCombo = new ComboBox<>();
        niveauCombo.getItems().addAll("Sixième", "Cinquième", "Quatrième", "Troisième", "Seconde", "Première",
                "Terminale");
        niveauCombo.setPromptText("Niveau");
        niveauCombo.setPrefWidth(250);
        niveauCombo.setPrefHeight(35);
        if (isEdit)
            niveauCombo.setValue(existing.getNiveau());

        TextField capaciteField = createFormField(isEdit ? String.valueOf(existing.getCapaciteMax()) : "50");
        capaciteField.setPromptText("Capacité");

        TextField anneeField = createFormField(isEdit ? existing.getAnneeAcademique() : "2025-2026");
        anneeField.setPromptText("Année académique");

        form.add(createFormLabel("Nom"), 0, 0);
        form.add(nomField, 1, 0);
        form.add(createFormLabel("Niveau"), 0, 1);
        form.add(niveauCombo, 1, 1);
        form.add(createFormLabel("Capacité max"), 0, 2);
        form.add(capaciteField, 1, 2);
        form.add(createFormLabel("Année"), 0, 3);
        form.add(anneeField, 1, 3);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showListeClasses());
        Button submitBtn = new Button(isEdit ? "Modifier" : "Submit");
        submitBtn.setStyle(BTN_SUBMIT);
        submitBtn.setOnAction(e -> {
            if (nomField.getText().trim().isEmpty()) {
                showAlert("Erreur", "Le nom est obligatoire.");
                return;
            }
            if (isEdit) {
                existing.setNom(nomField.getText().trim());
                existing.setNiveau(niveauCombo.getValue());
                try {
                    existing.setCapaciteMax(Integer.parseInt(capaciteField.getText().trim()));
                } catch (Exception ex) {
                }
                existing.setAnneeAcademique(anneeField.getText().trim());
                dataStore.updateClasse(existing);
                showAlert("Succès", "Classe modifiée !");
            } else {
                int cap = 50;
                try {
                    cap = Integer.parseInt(capaciteField.getText().trim());
                } catch (Exception ex) {
                }
                Classe c = new Classe(nomField.getText().trim(),
                        niveauCombo.getValue() != null ? niveauCombo.getValue() : "", cap, anneeField.getText().trim());
                dataStore.addClasse(c);
                showAlert("Succès", "Classe créée !");
            }
            showListeClasses();
        });
        buttons.getChildren().addAll(cancelBtn, submitBtn);

        page.getChildren().addAll(form, buttons);
        contentArea.getChildren().add(page);
    }

    @SuppressWarnings("unchecked")
    private void showListeClasses() {
        contentArea.getChildren().clear();
        VBox page = createListPage("LISTE DES CLASSES");

        HBox addBar = new HBox();
        addBar.setAlignment(Pos.CENTER_RIGHT);
        addBar.setPadding(new Insets(0, 0, 10, 0));
        Button addBtn = new Button("+ Créer une classe");
        addBtn.setStyle(BTN_SUCCESS);
        addBtn.setOnAction(e -> showFormulaireClasse(null));
        addBar.getChildren().add(addBtn);

        TableView<Classe> table = new TableView<>();
        table.setStyle("-fx-border-color: #ddd;");
        table.setPrefHeight(400);

        TableColumn<Classe, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.valueOf(table.getItems().indexOf(d.getValue()) + 1)));
        numCol.setPrefWidth(40);

        TableColumn<Classe, String> nomCol = new TableColumn<>("NOM CLASSE");
        nomCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNom()));
        nomCol.setPrefWidth(150);

        TableColumn<Classe, String> nivCol = new TableColumn<>("NIVEAU");
        nivCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNiveau()));
        nivCol.setPrefWidth(120);

        TableColumn<Classe, String> effCol = new TableColumn<>("EFFECTIF");
        effCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.valueOf(dataStore.getEffectifClasse(d.getValue().getId()))));
        effCol.setPrefWidth(80);

        TableColumn<Classe, String> capCol = new TableColumn<>("CAPACITÉ");
        capCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCapaciteMax())));
        capCol.setPrefWidth(80);

        TableColumn<Classe, String> annCol = new TableColumn<>("ANNÉE");
        annCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAnneeAcademique()));
        annCol.setPrefWidth(110);

        TableColumn<Classe, Void> actCol = new TableColumn<>("ACTION");
        actCol.setPrefWidth(100);
        actCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                HBox actions = new HBox(5);
                actions.setAlignment(Pos.CENTER);
                Label editBtn = new Label("✏");
                editBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #2980b9;");
                editBtn.setOnMouseClicked(e -> showFormulaireClasse(getTableView().getItems().get(getIndex())));
                Label delBtn = new Label("🗑");
                delBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #e74c3c;");
                delBtn.setOnMouseClicked(e -> {
                    Classe c = getTableView().getItems().get(getIndex());
                    if (confirmDelete("Supprimer la classe " + c.getNom() + " ?")) {
                        dataStore.deleteClasse(c.getId());
                        showListeClasses();
                    }
                });
                actions.getChildren().addAll(editBtn, delBtn);
                setGraphic(actions);
            }
        });

        table.getColumns().addAll(numCol, nomCol, nivCol, effCol, capCol, annCol, actCol);
        table.setItems(FXCollections.observableArrayList(dataStore.getAllClasses()));

        page.getChildren().addAll(addBar, table);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // CRUD ENSEIGNANTS
    // ====================================================================

    private void showFormulaireEnseignant(Enseignant existing) {
        contentArea.getChildren().clear();
        boolean isEdit = existing != null;

        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(700);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle(isEdit ? "MODIFIER ENSEIGNANT" : "AJOUTER UN ENSEIGNANT"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        TextField nomField = createFormField(isEdit ? existing.getNom() : "");
        nomField.setPromptText("Nom");
        TextField prenomField = createFormField(isEdit ? existing.getPrenom() : "");
        prenomField.setPromptText("Prénom");
        TextField emailField = createFormField(isEdit ? existing.getEmail() : "");
        emailField.setPromptText("Email (sera utilisé comme identifiant de connexion)");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(isEdit ? "Laisser vide pour garder l'ancien" : "Mot de passe");
        passwordField.setPrefHeight(35);
        passwordField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #dee2e6;");
        TextField telField = createFormField(isEdit ? existing.getTelephone() : "");
        telField.setPromptText("Téléphone");
        TextField gradeField = createFormField(isEdit ? existing.getGrade() : "");
        gradeField.setPromptText("Grade");
        TextField specField = createFormField(isEdit ? String.join(", ", existing.getSpecialites()) : "");
        specField.setPromptText("Spécialités (séparées par virgule)");

        form.add(createFormLabel("Nom *"), 0, 0);
        form.add(nomField, 1, 0);
        form.add(createFormLabel("Prénom *"), 0, 1);
        form.add(prenomField, 1, 1);
        form.add(createFormLabel("Email *"), 0, 2);
        form.add(emailField, 1, 2);
        form.add(createFormLabel("Mot de passe *"), 0, 3);
        form.add(passwordField, 1, 3);
        form.add(createFormLabel("Téléphone"), 0, 4);
        form.add(telField, 1, 4);
        form.add(createFormLabel("Grade"), 0, 5);
        form.add(gradeField, 1, 5);
        form.add(createFormLabel("Spécialités"), 0, 6);
        form.add(specField, 1, 6);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showListeEnseignants());
        Button submitBtn = new Button(isEdit ? "Modifier" : "Submit");
        submitBtn.setStyle(BTN_SUBMIT);
        submitBtn.setOnAction(e -> {
            if (nomField.getText().trim().isEmpty()) {
                showAlert("Erreur", "Le nom est obligatoire.");
                return;
            }
            if (emailField.getText().trim().isEmpty()) {
                showAlert("Erreur", "L'email est obligatoire (il servira de login).");
                return;
            }
            if (!isEdit && passwordField.getText().trim().isEmpty()) {
                showAlert("Erreur", "Le mot de passe est obligatoire.");
                return;
            }
            if (isEdit) {
                existing.setNom(nomField.getText().trim());
                existing.setPrenom(prenomField.getText().trim());
                existing.setEmail(emailField.getText().trim());
                if (!passwordField.getText().trim().isEmpty()) {
                    existing.setMotDePasse(passwordField.getText().trim());
                }
                existing.setTelephone(telField.getText().trim());
                existing.setGrade(gradeField.getText().trim());
                List<String> specs = List.of(specField.getText().split(","));
                existing.setSpecialites(new java.util.ArrayList<>(specs));
                dataStore.updateEnseignant(existing);
                showAlert("Succès", "Enseignant modifié !");
            } else {
                Enseignant en = new Enseignant(nomField.getText().trim(), prenomField.getText().trim(),
                        emailField.getText().trim(), passwordField.getText().trim(),
                        telField.getText().trim(), gradeField.getText().trim());
                for (String s : specField.getText().split(",")) {
                    if (!s.trim().isEmpty())
                        en.ajouterSpecialite(s.trim());
                }
                dataStore.addEnseignant(en);
                showAlert("Succès", "Enseignant ajouté !\nLogin : " + emailField.getText().trim()
                        + "\nMot de passe : " + passwordField.getText().trim());
            }
            showListeEnseignants();
        });
        buttons.getChildren().addAll(cancelBtn, submitBtn);

        page.getChildren().addAll(form, buttons);
        contentArea.getChildren().add(page);
    }

    @SuppressWarnings("unchecked")
    private void showListeEnseignants() {
        contentArea.getChildren().clear();
        VBox page = createListPage("LISTE DES ENSEIGNANTS");

        HBox addBar = new HBox();
        addBar.setAlignment(Pos.CENTER_RIGHT);
        addBar.setPadding(new Insets(0, 0, 10, 0));
        Button addBtn = new Button("+ Ajouter un enseignant");
        addBtn.setStyle(BTN_SUCCESS);
        addBtn.setOnAction(e -> showFormulaireEnseignant(null));
        addBar.getChildren().add(addBtn);

        TableView<Enseignant> table = new TableView<>();
        table.setStyle("-fx-border-color: #ddd;");
        table.setPrefHeight(400);

        TableColumn<Enseignant, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.valueOf(table.getItems().indexOf(d.getValue()) + 1)));
        numCol.setPrefWidth(40);

        TableColumn<Enseignant, String> nomCol = new TableColumn<>("NOM COMPLET");
        nomCol.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getNom() + " " + d.getValue().getPrenom()));
        nomCol.setPrefWidth(200);

        TableColumn<Enseignant, String> emailCol = new TableColumn<>("EMAIL");
        emailCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEmail()));
        emailCol.setPrefWidth(180);

        TableColumn<Enseignant, String> telCol = new TableColumn<>("CONTACT");
        telCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelephone()));
        telCol.setPrefWidth(120);

        TableColumn<Enseignant, String> gradeCol = new TableColumn<>("GRADE");
        gradeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getGrade()));
        gradeCol.setPrefWidth(120);

        TableColumn<Enseignant, Void> actCol = new TableColumn<>("ACTION");
        actCol.setPrefWidth(100);
        actCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                HBox actions = new HBox(5);
                actions.setAlignment(Pos.CENTER);
                Label editBtn = new Label("✏");
                editBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #2980b9;");
                editBtn.setOnMouseClicked(e -> showFormulaireEnseignant(getTableView().getItems().get(getIndex())));
                Label delBtn = new Label("🗑");
                delBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #e74c3c;");
                delBtn.setOnMouseClicked(e -> {
                    Enseignant en = getTableView().getItems().get(getIndex());
                    if (confirmDelete("Supprimer l'enseignant " + en.getNomComplet() + " ?")) {
                        dataStore.deleteEnseignant(en.getId());
                        showListeEnseignants();
                    }
                });
                actions.getChildren().addAll(editBtn, delBtn);
                setGraphic(actions);
            }
        });

        table.getColumns().addAll(numCol, nomCol, emailCol, telCol, gradeCol, actCol);
        table.setItems(FXCollections.observableArrayList(dataStore.getAllEnseignants()));

        page.getChildren().addAll(addBar, table);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // CRUD MATIÈRES
    // ====================================================================

    @SuppressWarnings("unchecked")
    private void showListeMatieres() {
        contentArea.getChildren().clear();
        VBox page = createListPage("LISTE DES MATIÈRES");

        // Barre supérieure : filtre par classe + bouton ajouter
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 10, 0));

        Label filterLabel = new Label("Filtrer par classe :");
        filterLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        ComboBox<String> classeFilter = new ComboBox<>();
        classeFilter.getItems().add("Toutes les matières");
        for (Classe c : dataStore.getAllClasses())
            classeFilter.getItems().add(c.getNom());
        classeFilter.setValue("Toutes les matières");
        classeFilter.setPrefHeight(35);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        Button addBtn = new Button("+ Ajouter une matière");
        addBtn.setStyle(BTN_SUCCESS);
        addBtn.setOnAction(e -> showFormulaireMatiere(null));
        topBar.getChildren().addAll(filterLabel, classeFilter, spacer, addBtn);

        TableView<Matiere> table = new TableView<>();
        table.setStyle("-fx-border-color: #ddd;");
        table.setPrefHeight(400);

        TableColumn<Matiere, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.valueOf(table.getItems().indexOf(d.getValue()) + 1)));
        numCol.setPrefWidth(40);

        TableColumn<Matiere, String> codeCol = new TableColumn<>("CODE");
        codeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCode()));
        codeCol.setPrefWidth(80);

        TableColumn<Matiere, String> nomCol = new TableColumn<>("NOM MATIÈRE");
        nomCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNom()));
        nomCol.setPrefWidth(200);

        TableColumn<Matiere, String> coefCol = new TableColumn<>("COEFFICIENT");
        coefCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf((int) d.getValue().getCoefficient())));
        coefCol.setPrefWidth(100);

        TableColumn<Matiere, String> hCol = new TableColumn<>("HEURES");
        hCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getNbHeures()) + "h"));
        hCol.setPrefWidth(80);

        TableColumn<Matiere, Void> actCol = new TableColumn<>("ACTION");
        actCol.setPrefWidth(100);
        actCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                HBox actions = new HBox(5);
                actions.setAlignment(Pos.CENTER);
                Label editBtn = new Label("✏");
                editBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #2980b9;");
                editBtn.setOnMouseClicked(e -> showFormulaireMatiere(getTableView().getItems().get(getIndex())));
                Label delBtn = new Label("🗑");
                delBtn.setStyle("-fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #e74c3c;");
                delBtn.setOnMouseClicked(e -> {
                    Matiere m = getTableView().getItems().get(getIndex());
                    if (confirmDelete("Supprimer la matière " + m.getNom() + " ?")) {
                        dataStore.deleteMatiere(m.getId());
                        showListeMatieres();
                    }
                });
                actions.getChildren().addAll(editBtn, delBtn);
                setGraphic(actions);
            }
        });

        table.getColumns().addAll(numCol, codeCol, nomCol, coefCol, hCol, actCol);

        Label countLabel = new Label("");
        countLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12px;");
        countLabel.setPadding(new Insets(5, 0, 0, 0));

        // Logique de filtre par classe
        Runnable applyFilter = () -> {
            String selected = classeFilter.getValue();
            List<Matiere> filtered;
            if (selected == null || "Toutes les matières".equals(selected)) {
                filtered = dataStore.getAllMatieres();
            } else {
                filtered = dataStore.getMatieresByClasse(selected);
            }
            table.setItems(FXCollections.observableArrayList(filtered));
            countLabel.setText("Total : " + filtered.size() + " matière(s)");
        };
        classeFilter.setOnAction(e -> applyFilter.run());
        applyFilter.run();

        page.getChildren().addAll(topBar, table, countLabel);
        contentArea.getChildren().add(page);
    }

    private void showFormulaireMatiere(Matiere existing) {
        contentArea.getChildren().clear();
        boolean isEdit = existing != null;

        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(500);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle(isEdit ? "MODIFIER MATIÈRE" : "AJOUTER UNE MATIÈRE"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        TextField codeField = createFormField(isEdit ? existing.getCode() : "");
        codeField.setPromptText("Code");
        TextField nomField = createFormField(isEdit ? existing.getNom() : "");
        nomField.setPromptText("Nom");
        TextField coefField = createFormField(isEdit ? String.valueOf((int) existing.getCoefficient()) : "");
        coefField.setPromptText("Coefficient");
        TextField heuresField = createFormField(isEdit ? String.valueOf(existing.getNbHeures()) : "");
        heuresField.setPromptText("Heures/semaine");

        form.add(createFormLabel("Code"), 0, 0);
        form.add(codeField, 1, 0);
        form.add(createFormLabel("Nom"), 0, 1);
        form.add(nomField, 1, 1);
        form.add(createFormLabel("Coefficient"), 0, 2);
        form.add(coefField, 1, 2);
        form.add(createFormLabel("Heures"), 0, 3);
        form.add(heuresField, 1, 3);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showListeMatieres());
        Button submitBtn = new Button(isEdit ? "Modifier" : "Submit");
        submitBtn.setStyle(BTN_SUBMIT);
        submitBtn.setOnAction(e -> {
            if (nomField.getText().trim().isEmpty()) {
                showAlert("Erreur", "Le nom est obligatoire.");
                return;
            }
            float coef = 1;
            int heures = 2;
            try {
                coef = Float.parseFloat(coefField.getText().trim());
            } catch (Exception ex) {
            }
            try {
                heures = Integer.parseInt(heuresField.getText().trim());
            } catch (Exception ex) {
            }
            if (isEdit) {
                existing.setCode(codeField.getText().trim());
                existing.setNom(nomField.getText().trim());
                existing.setCoefficient(coef);
                existing.setNbHeures(heures);
                dataStore.updateMatiere(existing);
                showAlert("Succès", "Matière modifiée !");
            } else {
                Matiere m = new Matiere(codeField.getText().trim(), nomField.getText().trim(), coef, heures);
                dataStore.addMatiere(m);
                showAlert("Succès", "Matière ajoutée !");
            }
            showListeMatieres();
        });
        buttons.getChildren().addAll(cancelBtn, submitBtn);
        page.getChildren().addAll(form, buttons);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // EMPLOI DU TEMPS
    // ====================================================================

    private void showFormulaireEmploiDuTemps() {
        contentArea.getChildren().clear();
        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(700);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle("AJOUTER UNE SÉANCE"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        ComboBox<String> classeCombo = new ComboBox<>();
        classeCombo.setPromptText("Sélectionner la classe");
        classeCombo.setPrefWidth(250);
        classeCombo.setPrefHeight(35);
        for (Classe c : dataStore.getAllClasses())
            classeCombo.getItems().add(c.getNom());

        ComboBox<String> matiereCombo = new ComboBox<>();
        matiereCombo.setPromptText("Sélectionner la matière");
        matiereCombo.setPrefWidth(250);
        matiereCombo.setPrefHeight(35);
        for (Matiere m : dataStore.getAllMatieres())
            matiereCombo.getItems().add(m.getNom());

        ComboBox<String> jourCombo = new ComboBox<>();
        jourCombo.getItems().addAll("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi");
        jourCombo.setPromptText("Sélectionner le jour");
        jourCombo.setPrefWidth(250);
        jourCombo.setPrefHeight(35);

        ComboBox<String> debutCombo = new ComboBox<>();
        debutCombo.getItems().addAll("07:00", "08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00");
        debutCombo.setPromptText("Heure de début");
        debutCombo.setPrefWidth(250);
        debutCombo.setPrefHeight(35);

        ComboBox<String> finCombo = new ComboBox<>();
        finCombo.getItems().addAll("08:00", "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00");
        finCombo.setPromptText("Heure de fin");
        finCombo.setPrefWidth(250);
        finCombo.setPrefHeight(35);

        ComboBox<String> enseignantCombo = new ComboBox<>();
        enseignantCombo.setPromptText("Sélectionner l'enseignant");
        enseignantCombo.setPrefWidth(250);
        enseignantCombo.setPrefHeight(35);
        for (Enseignant en : dataStore.getAllEnseignants())
            enseignantCombo.getItems().add(en.getNomComplet());

        TextField salleField = createFormField("");
        salleField.setPromptText("Ex: Salle A1");

        form.add(createFormLabel("Classe *"), 0, 0);
        form.add(classeCombo, 1, 0);
        form.add(createFormLabel("Matière *"), 0, 1);
        form.add(matiereCombo, 1, 1);
        form.add(createFormLabel("Jour *"), 0, 2);
        form.add(jourCombo, 1, 2);
        form.add(createFormLabel("Heure début *"), 0, 3);
        form.add(debutCombo, 1, 3);
        form.add(createFormLabel("Heure fin *"), 0, 4);
        form.add(finCombo, 1, 4);
        form.add(createFormLabel("Enseignant"), 0, 5);
        form.add(enseignantCombo, 1, 5);
        form.add(createFormLabel("Salle"), 0, 6);
        form.add(salleField, 1, 6);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 12px;");
        errorLabel.setPadding(new Insets(0, 40, 0, 40));

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showEmploiDuTemps());
        Button submitBtn = new Button("Ajouter la séance");
        submitBtn.setStyle(BTN_SUBMIT);
        submitBtn.setOnAction(e -> {
            if (classeCombo.getValue() == null) {
                errorLabel.setText("Veuillez sélectionner une classe.");
                return;
            }
            if (matiereCombo.getValue() == null) {
                errorLabel.setText("Veuillez sélectionner une matière.");
                return;
            }
            if (jourCombo.getValue() == null) {
                errorLabel.setText("Veuillez sélectionner un jour.");
                return;
            }
            if (debutCombo.getValue() == null || finCombo.getValue() == null) {
                errorLabel.setText("Veuillez sélectionner les heures de début et de fin.");
                return;
            }
            DataStore.SeanceEntry entry = new DataStore.SeanceEntry(
                    classeCombo.getValue(),
                    matiereCombo.getValue(),
                    enseignantCombo.getValue() != null ? enseignantCombo.getValue() : "",
                    jourCombo.getValue(),
                    debutCombo.getValue(),
                    finCombo.getValue(),
                    salleField.getText().trim());
            dataStore.addSeanceEntry(entry);
            showAlert("Succès", "Séance ajoutée à l'emploi du temps !");
            showEmploiDuTemps();
        });
        buttons.getChildren().addAll(cancelBtn, submitBtn);

        page.getChildren().addAll(form, errorLabel, buttons);
        contentArea.getChildren().add(page);
    }

    private void showEmploiDuTemps() {
        contentArea.getChildren().clear();
        VBox page = createListPage("EMPLOI DU TEMPS");

        // Barre de sélection de classe + bouton ajouter
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Classe :");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        ComboBox<String> classeSelect = new ComboBox<>();
        for (Classe c : dataStore.getAllClasses())
            classeSelect.getItems().add(c.getNom());
        classeSelect.setPromptText("Sélectionner une classe");
        classeSelect.setPrefHeight(35);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        Button saveBtn = new Button("💾 Enregistrer");
        saveBtn.setStyle(
                "-fx-background-color: #899481; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 15; -fx-background-radius: 8; -fx-cursor: hand;");
        Button printBtn = new Button("🖨 Imprimer");
        printBtn.setStyle(
                "-fx-background-color: #304F6D; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 15; -fx-background-radius: 8; -fx-cursor: hand;");
        Button addBtn = new Button("+ Ajouter une séance");
        addBtn.setStyle(BTN_SUCCESS);
        addBtn.setOnAction(e -> showFormulaireEmploiDuTemps());
        topBar.getChildren().addAll(label, classeSelect, spacer, saveBtn, printBtn, addBtn);

        // Grille emploi du temps
        String[] jours = { "", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" };
        String[] heuresDebut = { "07:00", "08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00" };
        String[] heuresFin = { "08:00", "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00" };

        GridPane timetable = new GridPane();
        timetable.setStyle("-fx-border-color: #ddd;");

        // En-têtes jours
        for (int j = 0; j < jours.length; j++) {
            Label h = new Label(jours[j]);
            h.setPrefWidth(j == 0 ? 90 : 120);
            h.setPrefHeight(32);
            h.setAlignment(Pos.CENTER);
            h.setStyle(
                    "-fx-background-color: #304F6D; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px;");
            timetable.add(h, j, 0);
        }
        // Lignes heures + cellules vides
        for (int i = 0; i < heuresDebut.length; i++) {
            Label hl = new Label(heuresDebut[i] + "-" + heuresFin[i]);
            hl.setPrefWidth(90);
            hl.setPrefHeight(50);
            hl.setAlignment(Pos.CENTER);
            hl.setStyle("-fx-background-color: #f0f0f0; -fx-font-size: 10px; -fx-font-weight: bold;");
            timetable.add(hl, 0, i + 1);
            for (int j = 1; j < jours.length; j++) {
                VBox cell = new VBox(1);
                cell.setPrefWidth(120);
                cell.setPrefHeight(50);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-background-color: white; -fx-border-color: #eee; -fx-padding: 2;");
                timetable.add(cell, j, i + 1);
            }
        }

        // Remplir la grille quand une classe est sélectionnée
        classeSelect.setOnAction(e -> {
            // Vider toutes les cellules
            for (int i = 0; i < heuresDebut.length; i++) {
                for (int j = 1; j < jours.length; j++) {
                    javafx.scene.Node node = getNodeFromGridPane(timetable, j, i + 1);
                    if (node instanceof VBox) {
                        ((VBox) node).getChildren().clear();
                        node.setStyle("-fx-background-color: white; -fx-border-color: #eee; -fx-padding: 2;");
                    }
                }
            }
            if (classeSelect.getValue() == null)
                return;
            List<DataStore.SeanceEntry> seances = dataStore.getSeanceEntriesByClasse(classeSelect.getValue());
            for (DataStore.SeanceEntry s : seances) {
                // Trouver la colonne du jour
                int colIndex = -1;
                for (int j = 1; j < jours.length; j++) {
                    if (jours[j].equals(s.getJour())) {
                        colIndex = j;
                        break;
                    }
                }
                if (colIndex < 0)
                    continue;

                // Trouver les lignes correspondantes
                for (int i = 0; i < heuresDebut.length; i++) {
                    String cellDebut = heuresDebut[i];
                    String cellFin = heuresFin[i];
                    // La séance occupe cette cellule si elle chevauche le créneau
                    if (s.getHeureDebut().compareTo(cellFin) < 0 && s.getHeureFin().compareTo(cellDebut) > 0) {
                        javafx.scene.Node node = getNodeFromGridPane(timetable, colIndex, i + 1);
                        if (node instanceof VBox) {
                            VBox cellBox = (VBox) node;
                            Label matLabel = new Label(s.getMatiereNom());
                            matLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
                            matLabel.setWrapText(true);
                            matLabel.setMaxWidth(115);
                            Label salleLabel = new Label(s.getSalle().isEmpty() ? "" : s.getSalle());
                            salleLabel.setStyle("-fx-font-size: 8px; -fx-text-fill: #899481;");
                            Label ensLabel = new Label(s.getEnseignantNom().isEmpty() ? "" : s.getEnseignantNom());
                            ensLabel.setStyle("-fx-font-size: 8px; -fx-text-fill: #E07D54;");
                            cellBox.getChildren().clear();
                            cellBox.getChildren().addAll(matLabel, salleLabel, ensLabel);
                            cellBox.setStyle(
                                    "-fx-background-color: #FFE1A0; -fx-border-color: #E07D54; -fx-padding: 2; -fx-background-radius: 3;");
                        }
                    }
                }
            }
        });

        // Sélectionner la première classe par défaut
        if (!classeSelect.getItems().isEmpty()) {
            classeSelect.setValue(classeSelect.getItems().get(0));
        }
        // Action d'enregistrement en image
        saveBtn.setOnAction(e -> {
            String selectedClasse = classeSelect.getValue();
            if (selectedClasse == null) {
                showAlert("Erreur", "Veuillez sélectionner une classe avant d'enregistrer.");
                return;
            }

            // Créer un conteneur pour le snapshot avec titre
            VBox snapContent = new VBox(10);
            snapContent.setPadding(new Insets(20));
            snapContent.setStyle("-fx-background-color: white;");

            Label snapTitle = new Label("EMPLOI DU TEMPS - " + selectedClasse);
            snapTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
            snapTitle.setAlignment(Pos.CENTER);
            snapTitle.setMaxWidth(Double.MAX_VALUE);

            Label snapDate = new Label(
                    "Généré le : " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            snapDate.setStyle("-fx-font-size: 11px; -fx-text-fill: #899481;");
            snapDate.setAlignment(Pos.CENTER);
            snapDate.setMaxWidth(Double.MAX_VALUE);

            // Retirer la grille temporairement pour le snap
            page.getChildren().remove(timetable);
            snapContent.getChildren().addAll(snapTitle, snapDate, timetable);

            // Forcer le layout du conteneur
            javafx.scene.Scene snapScene = new javafx.scene.Scene(snapContent);
            snapContent.applyCss();
            snapContent.layout();

            // Prendre le snapshot
            javafx.scene.image.WritableImage image = snapContent.snapshot(new javafx.scene.SnapshotParameters(), null);

            // Remettre la grille dans la page
            snapContent.getChildren().remove(timetable);
            page.getChildren().add(timetable);

            // Dialogue de sauvegarde
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Enregistrer l'emploi du temps");
            fileChooser.setInitialFileName("Emploi_du_temps_" + selectedClasse.replace(" ", "_") + ".png");
            fileChooser.getExtensionFilters().add(
                    new javafx.stage.FileChooser.ExtensionFilter("Image PNG", "*.png"));
            java.io.File file = fileChooser.showSaveDialog(contentArea.getScene().getWindow());

            if (file != null) {
                try {
                    javax.imageio.ImageIO.write(
                            javafx.embed.swing.SwingFXUtils.fromFXImage(image, null),
                            "png", file);
                    showAlert("Succès", "L'emploi du temps a été enregistré dans :\n" + file.getAbsolutePath());
                } catch (Exception ex) {
                    showAlert("Erreur", "Impossible d'enregistrer le fichier :\n" + ex.getMessage());
                }
            }
        });

        // Action d'impression
        printBtn.setOnAction(e -> {
            String selectedClasse = classeSelect.getValue();
            if (selectedClasse == null) {
                showAlert("Erreur", "Veuillez sélectionner une classe avant d'imprimer.");
                return;
            }

            // Créer un conteneur d'impression avec titre
            VBox printContent = new VBox(10);
            printContent.setPadding(new Insets(20));
            printContent.setStyle("-fx-background-color: white;");

            Label printTitle = new Label("EMPLOI DU TEMPS - " + selectedClasse);
            printTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
            printTitle.setAlignment(Pos.CENTER);
            printTitle.setMaxWidth(Double.MAX_VALUE);

            Label printDate = new Label(
                    "Imprimé le : " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            printDate.setStyle("-fx-font-size: 11px; -fx-text-fill: #899481;");
            printDate.setAlignment(Pos.CENTER);
            printDate.setMaxWidth(Double.MAX_VALUE);

            printContent.getChildren().addAll(printTitle, printDate, timetable);

            // Lancer l'impression via PrinterJob
            javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
            if (job != null) {
                // Configurer la mise en page en paysage
                javafx.print.Printer printer = job.getPrinter();
                javafx.print.PageLayout pageLayout = printer.createPageLayout(
                        javafx.print.Paper.A4,
                        javafx.print.PageOrientation.LANDSCAPE,
                        javafx.print.Printer.MarginType.DEFAULT);
                job.getJobSettings().setPageLayout(pageLayout);

                boolean proceed = job.showPrintDialog(contentArea.getScene().getWindow());
                if (proceed) {
                    // Adapter l'échelle pour que la grille tienne sur la page
                    double pageWidth = pageLayout.getPrintableWidth();
                    double pageHeight = pageLayout.getPrintableHeight();
                    double nodeWidth = printContent.getBoundsInParent().getWidth();
                    double nodeHeight = printContent.getBoundsInParent().getHeight();

                    double scaleX = pageWidth / nodeWidth;
                    double scaleY = pageHeight / nodeHeight;
                    double scale = Math.min(scaleX, scaleY);

                    printContent.getTransforms().add(new javafx.scene.transform.Scale(scale, scale));

                    boolean success = job.printPage(pageLayout, printContent);
                    if (success) {
                        job.endJob();
                        showAlert("Succès",
                                "L'emploi du temps de la classe " + selectedClasse + " a été envoyé à l'imprimante.");
                    } else {
                        showAlert("Erreur", "L'impression a échoué.");
                    }

                    // Restaurer l'échelle
                    printContent.getTransforms().clear();
                }
            } else {
                showAlert("Erreur", "Aucune imprimante disponible.");
            }

            // Remettre la grille dans la page
            if (!page.getChildren().contains(timetable)) {
                page.getChildren().add(timetable);
            }
        });

        page.getChildren().addAll(topBar, timetable);
        contentArea.getChildren().add(page);
    }

    /**
     * Utilitaire pour récupérer un noeud dans un GridPane par ses coordonnées.
     */
    private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer c = GridPane.getColumnIndex(node);
            Integer r = GridPane.getRowIndex(node);
            if (c != null && r != null && c == col && r == row) {
                return node;
            }
        }
        return null;
    }

    // ====================================================================
    // STATISTIQUES
    // ====================================================================

    private void showStatistiques() {
        contentArea.getChildren().clear();
        VBox page = new VBox(25);
        page.setPadding(new Insets(20));
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createPageTitle("STATISTIQUES GÉNÉRALES"));

        HBox row1 = new HBox(20);
        row1.setAlignment(Pos.CENTER);
        row1.getChildren().addAll(
                createStatCard("Total Étudiants", String.valueOf(dataStore.getTotalEtudiants()), "🎓", "#6c5ce7"),
                createStatCard("Total Enseignants", String.valueOf(dataStore.getTotalEnseignants()), "👨‍🏫",
                        "#00b894"),
                createStatCard("Total Classes", String.valueOf(dataStore.getTotalClasses()), "📖", "#0984e3"),
                createStatCard("Total Matières", String.valueOf(dataStore.getTotalMatieres()), "📚", "#e17055"));

        // Détail par classe
        VBox detailBox = new VBox(10);
        detailBox.setStyle(
                "-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 6, 0, 0, 2);");
        detailBox.setPadding(new Insets(20));
        detailBox.setMaxWidth(700);

        Label detailTitle = new Label("Effectif par classe");
        detailTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
        detailBox.getChildren().add(detailTitle);

        for (Classe c : dataStore.getAllClasses()) {
            int effectif = dataStore.getEffectifClasse(c.getId());
            double pct = c.getCapaciteMax() > 0 ? (effectif * 100.0 / c.getCapaciteMax()) : 0;

            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            Label nameLabel = new Label(c.getNom());
            nameLabel.setPrefWidth(80);
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

            ProgressBar bar = new ProgressBar(pct / 100.0);
            bar.setPrefWidth(300);
            bar.setPrefHeight(20);

            Label countLabel = new Label(effectif + " / " + c.getCapaciteMax());
            countLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            row.getChildren().addAll(nameLabel, bar, countLabel);
            detailBox.getChildren().add(row);
        }

        page.getChildren().addAll(row1, detailBox);
        contentArea.getChildren().add(page);
    }

    private VBox createStatCard(String title, String value, String icon, String color) {
        VBox card = new VBox(8);
        card.setPrefSize(180, 110);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setStyle(CARD_STYLE);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #777;");
        card.getChildren().addAll(iconLabel, valueLabel, titleLabel);
        return card;
    }

    // ====================================================================
    // COMMUNICATION (MAILS / SMS)
    // ====================================================================

    private void showEnvoyerMails() {
        contentArea.getChildren().clear();
        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(800);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle("ENVOYER DES MAILS AUX PARENTS"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        // Sélection destinataire
        ComboBox<String> destCombo = new ComboBox<>();
        destCombo.setPromptText("Sélectionner un destinataire");
        destCombo.setPrefWidth(400);
        destCombo.setPrefHeight(35);
        destCombo.getItems().add("Tous les parents");
        for (Classe c : dataStore.getAllClasses()) {
            destCombo.getItems().add("Parents de " + c.getNom());
        }
        for (Etudiant et : dataStore.getAllEtudiants()) {
            if (!et.getEmail().isEmpty()) {
                destCombo.getItems().add(et.getNomComplet() + " (" + et.getEmail() + ")");
            }
        }

        TextField sujetField = createFormField("");
        sujetField.setPromptText("Objet du mail");
        sujetField.setPrefWidth(400);

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Saisissez votre message ici...");
        messageArea.setPrefWidth(400);
        messageArea.setPrefHeight(200);
        messageArea.setStyle("-fx-border-color: #ccc; -fx-border-radius: 3; -fx-font-size: 13px;");

        form.add(createFormLabel("Destinataire"), 0, 0);
        form.add(destCombo, 1, 0);
        form.add(createFormLabel("Objet"), 0, 1);
        form.add(sujetField, 1, 1);
        form.add(createFormLabel("Message"), 0, 2);
        form.add(messageArea, 1, 2);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showAccueil());
        Button sendBtn = new Button("📧 Envoyer le Mail");
        sendBtn.setStyle(
                "-fx-background-color: #0984e3; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 25; -fx-background-radius: 4; -fx-cursor: hand;");
        sendBtn.setOnAction(e -> {
            if (destCombo.getValue() == null || sujetField.getText().trim().isEmpty()
                    || messageArea.getText().trim().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }
            showAlert("Succès", "Mail envoyé avec succès à : " + destCombo.getValue());
            showAccueil();
        });
        buttons.getChildren().addAll(cancelBtn, sendBtn);

        page.getChildren().addAll(form, buttons);
        contentArea.getChildren().add(page);
    }

    private void showEnvoyerSMS() {
        contentArea.getChildren().clear();
        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(800);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle("ENVOYER DES SMS AUX PARENTS"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        ComboBox<String> destCombo = new ComboBox<>();
        destCombo.setPromptText("Sélectionner un destinataire");
        destCombo.setPrefWidth(400);
        destCombo.setPrefHeight(35);
        destCombo.getItems().add("Tous les parents");
        for (Classe c : dataStore.getAllClasses()) {
            destCombo.getItems().add("Parents de " + c.getNom());
        }
        for (Etudiant et : dataStore.getAllEtudiants()) {
            if (!et.getTelephoneParent().isEmpty()) {
                destCombo.getItems().add(et.getNomComplet() + " - Parent: " + et.getTelephoneParent());
            }
        }

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Saisissez votre message SMS ici (160 caractères max)...");
        messageArea.setPrefWidth(400);
        messageArea.setPrefHeight(120);
        messageArea.setStyle("-fx-border-color: #ccc; -fx-border-radius: 3; -fx-font-size: 13px;");

        Label charCount = new Label("0 / 160 caractères");
        charCount.setStyle("-fx-text-fill: #999; -fx-font-size: 11px;");
        messageArea.textProperty().addListener((obs, oldVal, newVal) -> {
            charCount.setText(newVal.length() + " / 160 caractères");
            if (newVal.length() > 160) {
                charCount.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 11px; -fx-font-weight: bold;");
            } else {
                charCount.setStyle("-fx-text-fill: #999; -fx-font-size: 11px;");
            }
        });

        form.add(createFormLabel("Destinataire"), 0, 0);
        form.add(destCombo, 1, 0);
        form.add(createFormLabel("Message"), 0, 1);
        form.add(messageArea, 1, 1);
        form.add(new Label(""), 0, 2);
        form.add(charCount, 1, 2);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showAccueil());
        Button sendBtn = new Button("📱 Envoyer le SMS");
        sendBtn.setStyle(
                "-fx-background-color: #00b894; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 25; -fx-background-radius: 4; -fx-cursor: hand;");
        sendBtn.setOnAction(e -> {
            if (destCombo.getValue() == null || messageArea.getText().trim().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }
            showAlert("Succès", "SMS envoyé avec succès à : " + destCombo.getValue());
            showAccueil();
        });
        buttons.getChildren().addAll(cancelBtn, sendBtn);

        page.getChildren().addAll(form, buttons);
        contentArea.getChildren().add(page);
    }

    @SuppressWarnings("unchecked")
    private void showListerContacts() {
        contentArea.getChildren().clear();
        VBox page = createListPage("CONTACTS DES PARENTS");

        TableView<Etudiant> table = new TableView<>();
        table.setStyle("-fx-border-color: #ddd;");
        table.setPrefHeight(450);

        TableColumn<Etudiant, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.valueOf(table.getItems().indexOf(d.getValue()) + 1)));
        numCol.setPrefWidth(40);

        TableColumn<Etudiant, String> matricCol = new TableColumn<>("MATRICULE");
        matricCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMatricule()));
        matricCol.setPrefWidth(90);

        TableColumn<Etudiant, String> etudCol = new TableColumn<>("ÉTUDIANT");
        etudCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNomComplet()));
        etudCol.setPrefWidth(150);

        TableColumn<Etudiant, String> classeCol = new TableColumn<>("CLASSE");
        classeCol.setCellValueFactory(
                d -> new SimpleStringProperty(dataStore.getClasseNameForEtudiant(d.getValue().getId())));
        classeCol.setPrefWidth(80);

        TableColumn<Etudiant, String> pereCol = new TableColumn<>("NOM PÈRE");
        pereCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNomPere()));
        pereCol.setPrefWidth(130);

        TableColumn<Etudiant, String> mereCol = new TableColumn<>("NOM MÈRE");
        mereCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNomMere()));
        mereCol.setPrefWidth(130);

        TableColumn<Etudiant, String> telCol = new TableColumn<>("TÉL. PARENT");
        telCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelephoneParent()));
        telCol.setPrefWidth(120);

        TableColumn<Etudiant, String> emailCol = new TableColumn<>("EMAIL");
        emailCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEmail()));
        emailCol.setPrefWidth(160);

        table.getColumns().addAll(numCol, matricCol, etudCol, classeCol, pereCol, mereCol, telCol, emailCol);
        table.setItems(FXCollections.observableArrayList(dataStore.getAllEtudiants()));

        Label countLabel = new Label("Total : " + dataStore.getTotalEtudiants() + " contacts");
        countLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12px;");
        countLabel.setPadding(new Insets(5, 0, 0, 0));

        page.getChildren().addAll(table, countLabel);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // PAIEMENTS (INSCRIPTION / SCOLARITÉ)
    // ====================================================================

    private void showFormulairePaiement() {
        contentArea.getChildren().clear();
        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(700);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle("NOUVEAU PAIEMENT"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        // Sélection étudiant
        ComboBox<String> etudiantCombo = new ComboBox<>();
        etudiantCombo.setPromptText("Sélectionner un étudiant");
        etudiantCombo.setPrefWidth(300);
        etudiantCombo.setPrefHeight(35);
        for (Etudiant et : dataStore.getAllEtudiants()) {
            etudiantCombo.getItems().add(et.getId() + " - " + et.getMatricule() + " - " + et.getNomComplet());
        }

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("INSCRIPTION", "SCOLARITE");
        typeCombo.setPromptText("Type de paiement");
        typeCombo.setPrefWidth(300);
        typeCombo.setPrefHeight(35);

        Label trancheLabel = createFormLabel("Tranche");
        ComboBox<String> trancheCombo = new ComboBox<>();
        trancheCombo.getItems().addAll("1ère Tranche", "2ème Tranche");
        trancheCombo.setPromptText("Tranche");
        trancheCombo.setPrefWidth(300);
        trancheCombo.setPrefHeight(35);

        // Masquer tranche si type = INSCRIPTION
        typeCombo.setOnAction(ev -> {
            boolean isInscription = "INSCRIPTION".equals(typeCombo.getValue());
            trancheLabel.setVisible(!isInscription);
            trancheLabel.setManaged(!isInscription);
            trancheCombo.setVisible(!isInscription);
            trancheCombo.setManaged(!isInscription);
            if (isInscription)
                trancheCombo.setValue(null);
        });

        TextField montantField = createFormField("");
        montantField.setPromptText("Montant (FCFA)");

        ComboBox<String> modeCombo = new ComboBox<>();
        modeCombo.getItems().addAll("Espèces", "Mobile Money", "Virement Bancaire");
        modeCombo.setPromptText("Mode de paiement");
        modeCombo.setPrefWidth(300);
        modeCombo.setPrefHeight(35);

        TextField anneeField = createFormField("2025-2026");
        anneeField.setPromptText("Année académique");

        form.add(createFormLabel("Étudiant *"), 0, 0);
        form.add(etudiantCombo, 1, 0);
        form.add(createFormLabel("Type *"), 0, 1);
        form.add(typeCombo, 1, 1);
        form.add(trancheLabel, 0, 2);
        form.add(trancheCombo, 1, 2);
        form.add(createFormLabel("Montant *"), 0, 3);
        form.add(montantField, 1, 3);
        form.add(createFormLabel("Mode *"), 0, 4);
        form.add(modeCombo, 1, 4);
        form.add(createFormLabel("Année"), 0, 5);
        form.add(anneeField, 1, 5);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showListePaiements());
        Button submitBtn = new Button("💰 Enregistrer le Paiement");
        submitBtn.setStyle(BTN_SUBMIT);
        submitBtn.setOnAction(e -> {
            boolean isInscription = "INSCRIPTION".equals(typeCombo.getValue());
            if (etudiantCombo.getValue() == null || typeCombo.getValue() == null
                    || (!isInscription && trancheCombo.getValue() == null)
                    || montantField.getText().trim().isEmpty()
                    || modeCombo.getValue() == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }
            try {
                int etudiantId = Integer.parseInt(etudiantCombo.getValue().split(" - ")[0]);
                double montant = Double.parseDouble(montantField.getText().trim());
                String tranche = trancheCombo.getValue() != null ? trancheCombo.getValue() : "";
                Paiement p = new Paiement(etudiantId, typeCombo.getValue(), tranche,
                        montant, modeCombo.getValue(), anneeField.getText().trim());
                dataStore.addPaiement(p);
                showAlert("Succès", "Paiement enregistré avec succès !\nRéférence: " + p.getReference());
                showListePaiements();
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Le montant doit être un nombre valide.");
            }
        });
        buttons.getChildren().addAll(cancelBtn, submitBtn);

        page.getChildren().addAll(form, buttons);
        contentArea.getChildren().add(page);
    }

    @SuppressWarnings("unchecked")
    private void showListePaiements() {
        contentArea.getChildren().clear();
        VBox page = createListPage("LISTE DES PAIEMENTS");

        HBox addBar = new HBox();
        addBar.setAlignment(Pos.CENTER_RIGHT);
        addBar.setPadding(new Insets(0, 0, 10, 0));
        Button addBtn = new Button("+ Nouveau Paiement");
        addBtn.setStyle(BTN_SUCCESS);
        addBtn.setOnAction(e -> showFormulairePaiement());
        addBar.getChildren().add(addBtn);

        TableView<Paiement> table = new TableView<>();
        table.setStyle("-fx-border-color: #ddd;");
        table.setPrefHeight(400);

        TableColumn<Paiement, String> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.valueOf(table.getItems().indexOf(d.getValue()) + 1)));
        numCol.setPrefWidth(40);

        TableColumn<Paiement, String> refCol = new TableColumn<>("RÉFÉRENCE");
        refCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getReference()));
        refCol.setPrefWidth(100);

        TableColumn<Paiement, String> etudCol = new TableColumn<>("ÉTUDIANT");
        etudCol.setCellValueFactory(d -> {
            Etudiant et = dataStore.getEtudiantById(d.getValue().getEtudiantId());
            return new SimpleStringProperty(et != null ? et.getNomComplet() : "Inconnu");
        });
        etudCol.setPrefWidth(150);

        TableColumn<Paiement, String> typeCol = new TableColumn<>("TYPE");
        typeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTypePaiement()));
        typeCol.setPrefWidth(100);

        TableColumn<Paiement, String> trancheCol = new TableColumn<>("TRANCHE");
        trancheCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTranche()));
        trancheCol.setPrefWidth(100);

        TableColumn<Paiement, String> montantCol = new TableColumn<>("MONTANT");
        montantCol.setCellValueFactory(
                d -> new SimpleStringProperty(String.format("%,.0f FCFA", d.getValue().getMontant())));
        montantCol.setPrefWidth(110);

        TableColumn<Paiement, String> dateCol = new TableColumn<>("DATE");
        dateCol.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getDatePaiement() != null
                        ? d.getValue().getDatePaiement().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : ""));
        dateCol.setPrefWidth(90);

        TableColumn<Paiement, String> modeCol = new TableColumn<>("MODE");
        modeCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getModePaiement()));
        modeCol.setPrefWidth(100);

        table.getColumns().addAll(numCol, refCol, etudCol, typeCol, trancheCol, montantCol, dateCol, modeCol);
        table.setItems(FXCollections.observableArrayList(dataStore.getAllPaiements()));

        Label totalLabel = new Label(String.format("Total encaissé : %,.0f FCFA  |  %d paiements",
                dataStore.getTotalPaiements(), dataStore.getNombrePaiements()));
        totalLabel.setStyle("-fx-text-fill: #28a745; -fx-font-size: 13px; -fx-font-weight: bold;");
        totalLabel.setPadding(new Insets(10, 0, 0, 0));

        page.getChildren().addAll(addBar, table, totalLabel);
        contentArea.getChildren().add(page);
    }

    private void showGenererFacture() {
        contentArea.getChildren().clear();
        VBox page = new VBox(0);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setMaxWidth(700);
        page.setAlignment(Pos.TOP_CENTER);

        page.getChildren().add(createCloseBar());
        page.getChildren().add(createPageTitle("GÉNÉRER UNE FACTURE"));

        GridPane form = new GridPane();
        form.setHgap(25);
        form.setVgap(15);
        form.setPadding(new Insets(0, 40, 20, 40));

        ComboBox<String> paiementCombo = new ComboBox<>();
        paiementCombo.setPromptText("Sélectionner un paiement");
        paiementCombo.setPrefWidth(400);
        paiementCombo.setPrefHeight(35);
        for (Paiement p : dataStore.getAllPaiements()) {
            Etudiant et = dataStore.getEtudiantById(p.getEtudiantId());
            String etName = et != null ? et.getNomComplet() : "Inconnu";
            paiementCombo.getItems().add(p.getId() + " - " + p.getReference() + " - " + etName + " - "
                    + String.format("%,.0f FCFA", p.getMontant()));
        }

        form.add(createFormLabel("Paiement"), 0, 0);
        form.add(paiementCombo, 1, 0);

        // Zone aperçu facture
        VBox facturePreview = new VBox(10);
        facturePreview.setStyle(
                "-fx-background-color: #f8f9fa; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-background-radius: 5;");
        facturePreview.setPadding(new Insets(20));
        facturePreview.setMaxWidth(550);
        facturePreview.setVisible(false);
        facturePreview.setManaged(false);

        paiementCombo.setOnAction(ev -> {
            if (paiementCombo.getValue() != null) {
                int pId = Integer.parseInt(paiementCombo.getValue().split(" - ")[0]);
                Paiement p = dataStore.getPaiementById(pId);
                if (p != null) {
                    facturePreview.getChildren().clear();
                    facturePreview.setVisible(true);
                    facturePreview.setManaged(true);

                    Etudiant et = dataStore.getEtudiantById(p.getEtudiantId());
                    String etName = et != null ? et.getNomComplet() : "Inconnu";
                    String etMatricule = et != null ? et.getMatricule() : "";
                    String classe = et != null ? dataStore.getClasseNameForEtudiant(et.getId()) : "";

                    Label header = new Label("🏫  FACTURE - SYSTÈME DE GESTION SCOLAIRE");
                    header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");

                    Label separator = new Label("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    separator.setStyle("-fx-text-fill: #304F6D;");

                    Label refLabel = new Label("Référence : " + p.getReference());
                    refLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
                    Label dateLabel = new Label("Date : " + (p.getDatePaiement() != null
                            ? p.getDatePaiement().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            : "N/A"));
                    dateLabel.setStyle("-fx-font-size: 13px;");

                    Label sep2 = new Label("────────────────────────────────────────");
                    sep2.setStyle("-fx-text-fill: #ccc;");

                    Label etudLabel = new Label("Étudiant : " + etName);
                    etudLabel.setStyle("-fx-font-size: 13px;");
                    Label matLabel = new Label("Matricule : " + etMatricule);
                    matLabel.setStyle("-fx-font-size: 13px;");
                    Label classLabel = new Label("Classe : " + classe);
                    classLabel.setStyle("-fx-font-size: 13px;");

                    Label sep3 = new Label("────────────────────────────────────────");
                    sep3.setStyle("-fx-text-fill: #ccc;");

                    Label typeLabel = new Label("Type : " + p.getTypePaiement());
                    typeLabel.setStyle("-fx-font-size: 13px;");
                    Label trancheLabel = new Label("Tranche : " + p.getTranche());
                    trancheLabel.setStyle("-fx-font-size: 13px;");
                    Label modeLabel = new Label("Mode : " + p.getModePaiement());
                    modeLabel.setStyle("-fx-font-size: 13px;");

                    Label sep4 = new Label("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    sep4.setStyle("-fx-text-fill: #28a745;");

                    Label montantLabel = new Label(String.format("MONTANT PAYÉ : %,.0f FCFA", p.getMontant()));
                    montantLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #28a745;");

                    Label statutLabel = new Label("Statut : " + p.getStatut());
                    statutLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #28a745; -fx-font-weight: bold;");

                    facturePreview.getChildren().addAll(header, separator, refLabel, dateLabel, sep2,
                            etudLabel, matLabel, classLabel, sep3, typeLabel, trancheLabel, modeLabel,
                            sep4, montantLabel, statutLabel);
                }
            }
        });

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 30, 0));
        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle(BTN_CANCEL);
        cancelBtn.setOnAction(e -> showListePaiements());
        Button printBtn = new Button("🖨 Imprimer la Facture");
        printBtn.setStyle(
                "-fx-background-color: #6c5ce7; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 25; -fx-background-radius: 4; -fx-cursor: hand;");
        printBtn.setOnAction(e -> {
            if (paiementCombo.getValue() == null) {
                showAlert("Erreur", "Veuillez sélectionner un paiement.");
                return;
            }
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(contentArea.getScene().getWindow())) {
                boolean success = job.printPage(facturePreview);
                if (success) {
                    job.endJob();
                    showAlert("Succès", "Facture envoyée à l'imprimante !");
                }
            } else {
                showAlert("Info",
                        "Impression annulée ou aucune imprimante disponible.\nLa facture est affichée à l'écran.");
            }
        });
        buttons.getChildren().addAll(cancelBtn, printBtn);

        page.getChildren().addAll(form, facturePreview, buttons);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // UTILITAIRES
    // ====================================================================

    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #555;");
        label.setPrefWidth(110);
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }

    private TextField createFormField(String value) {
        TextField field = new TextField(value);
        field.setPrefWidth(250);
        field.setPrefHeight(35);
        field.setStyle(
                "-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 3; -fx-background-radius: 3; -fx-font-size: 13px;");
        return field;
    }

    private HBox createCloseBar() {
        HBox bar = new HBox();
        bar.setAlignment(Pos.TOP_RIGHT);
        bar.setPadding(new Insets(10, 15, 5, 15));
        Label closeBtn = new Label("✕");
        closeBtn.setStyle("-fx-font-size: 16px; -fx-text-fill: #999; -fx-cursor: hand;");
        closeBtn.setOnMouseClicked(e -> showAccueil());
        bar.getChildren().add(closeBtn);
        return bar;
    }

    private Label createPageTitle(String text) {
        Label titre = new Label(text);
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
        titre.setPadding(new Insets(0, 0, 15, 0));
        return titre;
    }

    private VBox createListPage(String title) {
        VBox page = new VBox(10);
        page.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        page.setPadding(new Insets(20, 25, 20, 25));
        page.setMaxWidth(1000);

        page.getChildren().add(createCloseBar());
        HBox titreBox = new HBox();
        titreBox.setAlignment(Pos.CENTER);
        titreBox.getChildren().add(createPageTitle(title));
        page.getChildren().add(titreBox);
        return page;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean confirmDelete(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    // ====================================================================
    // SUIVI ACTIVITÉ — PRÉSENCES
    // ====================================================================

    @SuppressWarnings("unchecked")
    private void showPresencesAdmin() {
        contentArea.getChildren().clear();
        VBox page = new VBox(20);
        page.setPadding(new Insets(20));

        // === En-tête ===
        HBox titleBar = makeSectionHeader("✅", "Suivi des Présences", "Rapports transmis par les enseignants");
        page.getChildren().add(titleBar);

        // === Filtres ===
        HBox filters = new HBox(15);
        filters.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        filters.setPadding(new Insets(0, 0, 5, 0));

        Label lEns = makeFLabel("Enseignant :");
        ComboBox<String> cbEns = new ComboBox<>();
        cbEns.getItems().add("Tous");
        dataStore.getAllEnseignants().stream()
                .map(e -> e.getPrenom() + " " + e.getNom())
                .forEach(cbEns.getItems()::add);
        cbEns.setValue("Tous");
        cbEns.setPrefWidth(160);

        Label lCl = makeFLabel("Classe :");
        ComboBox<String> cbCl = new ComboBox<>();
        cbCl.getItems().add("Toutes");
        dataStore.getAllClasses().stream().map(Classe::getNom).forEach(cbCl.getItems()::add);
        cbCl.setValue("Toutes");
        cbCl.setPrefWidth(130);

        Button btnFilter = new Button("🔍 Filtrer");
        btnFilter.setStyle(BTN_SUBMIT);

        filters.getChildren().addAll(lEns, cbEns, lCl, cbCl, btnFilter);

        // === Vue sessions groupées ===
        VBox sessionsList = new VBox(12);

        Runnable applyFilter = () -> {
            sessionsList.getChildren().clear();
            String filterEns = cbEns.getValue();
            String filterCl = cbCl.getValue();

            List<DataStore.PresenceEntry> all = dataStore.getAllPresences().stream()
                    .filter(p -> "Tous".equals(filterEns) || p.getEnseignantNom().equals(filterEns))
                    .filter(p -> "Toutes".equals(filterCl) || p.getClasseNom().equals(filterCl))
                    .collect(java.util.stream.Collectors.toList());

            if (all.isEmpty()) {
                sessionsList.getChildren().add(makePlaceholder("Aucune présence enregistrée"));
                return;
            }

            // Grouper par session (enseignant + matière + classe + date)
            java.util.Map<String, List<DataStore.PresenceEntry>> grouped = all.stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                            p -> p.getDate() + "||" + p.getEnseignantNom() + "||" + p.getMatiereNom() + "||"
                                    + p.getClasseNom()));

            grouped.entrySet().stream()
                    .sorted((a, b) -> b.getKey().compareTo(a.getKey()))
                    .forEach(entry -> {
                        List<DataStore.PresenceEntry> session = entry.getValue();
                        DataStore.PresenceEntry first = session.get(0);
                        long presents = session.stream().filter(p -> "Présent".equals(p.getStatut())).count();
                        long absents = session.stream().filter(p -> "Absent".equals(p.getStatut())).count();
                        long retards = session.stream().filter(p -> "Retard".equals(p.getStatut())).count();
                        double tauxPres = session.isEmpty() ? 0 : (presents * 100.0 / session.size());

                        VBox card = new VBox(10);
                        card.setPadding(new Insets(16));
                        card.setStyle(CARD_STYLE);

                        // En-tête session
                        HBox top = new HBox(12);
                        top.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                        VBox infos = new VBox(3);
                        Label lTitle = new Label(first.getMatiereNom() + " — " + first.getClasseNom());
                        lTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
                        Label lSub = new Label("👨‍🏫 " + first.getEnseignantNom() + "  ·  📅 "
                                + first.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        lSub.setStyle("-fx-font-size: 11px; -fx-text-fill: #636e72;");
                        infos.getChildren().addAll(lTitle, lSub);
                        HBox.setHgrow(infos, javafx.scene.layout.Priority.ALWAYS);

                        // Badges stats
                        HBox badges = new HBox(8);
                        badges.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
                        badges.getChildren().addAll(
                                makeAdminBadge("✅ " + presents + " prés.", "#27ae60"),
                                makeAdminBadge("❌ " + absents + " abs.", "#e74c3c"),
                                makeAdminBadge("⏰ " + retards + " retard", "#e67e22"),
                                makeAdminBadge(String.format("%.0f%%", tauxPres), "#2c3e6b"));

                        top.getChildren().addAll(infos, badges);

                        // Barre de progression
                        ProgressBar bar = new ProgressBar(tauxPres / 100.0);
                        bar.setMaxWidth(Double.MAX_VALUE);
                        bar.setPrefHeight(10);
                        bar.setStyle("-fx-accent: #27ae60;");

                        // Détail dépliable
                        TitledPane detail = new TitledPane();
                        detail.setText("‹› Détail des élèves (" + session.size() + ")");
                        detail.setExpanded(false);
                        detail.setStyle("-fx-font-size: 11px;");

                        VBox detailBox = new VBox(4);
                        detailBox.setPadding(new Insets(8));
                        session.stream()
                                .sorted(java.util.Comparator.comparing(DataStore.PresenceEntry::getEtudiantNom))
                                .forEach(p -> {
                                    HBox rowE = new HBox(12);
                                    rowE.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                                    rowE.setPadding(new Insets(5, 10, 5, 10));
                                    String bg = "Présent".equals(p.getStatut()) ? "#e8f5e9"
                                            : "Absent".equals(p.getStatut()) ? "#fdecea" : "#fff8e1";
                                    rowE.setStyle("-fx-background-color: " + bg + "; -fx-background-radius: 5;");
                                    Label lName = new Label(p.getEtudiantNom());
                                    lName.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
                                    Region sp = new Region();
                                    HBox.setHgrow(sp, javafx.scene.layout.Priority.ALWAYS);
                                    String statColor = "Présent".equals(p.getStatut()) ? "#27ae60"
                                            : "Absent".equals(p.getStatut()) ? "#e74c3c" : "#e67e22";
                                    Label lStatut = new Label(p.getStatut());
                                    lStatut.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: "
                                            + statColor + ";");
                                    rowE.getChildren().addAll(lName, sp, lStatut);
                                    detailBox.getChildren().add(rowE);
                                });
                        detail.setContent(detailBox);

                        card.getChildren().addAll(top, bar, detail);
                        sessionsList.getChildren().add(card);
                    });
        };

        btnFilter.setOnAction(e -> applyFilter.run());
        applyFilter.run();

        // === Stats globales par étudiant ===
        VBox statsBox = new VBox(12);
        statsBox.setPadding(new Insets(18));
        statsBox.setStyle(CARD_STYLE);
        Label statsTitle = new Label("📊 Stats par étudiant");
        statsTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");

        TableView<EtudiantStatRow> statsTable = new TableView<>();
        statsTable.setPrefHeight(300);
        statsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<EtudiantStatRow, String> colNom = new TableColumn<>("ETUDIANT");
        colNom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().nom));
        colNom.setPrefWidth(180);

        TableColumn<EtudiantStatRow, String> colCl = new TableColumn<>("CLASSE");
        colCl.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().classe));
        colCl.setPrefWidth(90);

        TableColumn<EtudiantStatRow, String> colPres = new TableColumn<>("PRÉSENCES");
        colPres.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().presents)));
        colPres.setPrefWidth(90);

        TableColumn<EtudiantStatRow, String> colAbs = new TableColumn<>("ABSENCES");
        colAbs.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().absents)));
        colAbs.setPrefWidth(90);

        TableColumn<EtudiantStatRow, String> colRetard = new TableColumn<>("RETARDS");
        colRetard.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().retards)));
        colRetard.setPrefWidth(80);

        TableColumn<EtudiantStatRow, String> colTaux = new TableColumn<>("TAUX PRE.");
        colTaux.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.0f%%", d.getValue().taux)));
        colTaux.setPrefWidth(90);

        statsTable.getColumns().addAll(colNom, colCl, colPres, colAbs, colRetard, colTaux);

        // Calculer les stats
        List<DataStore.PresenceEntry> allPres = dataStore.getAllPresences();
        java.util.Map<String, EtudiantStatRow> statsMap = new java.util.LinkedHashMap<>();
        for (DataStore.PresenceEntry p : allPres) {
            String key = p.getEtudiantNom() + "||" + p.getClasseNom();
            EtudiantStatRow row = statsMap.computeIfAbsent(key,
                    k -> new EtudiantStatRow(p.getEtudiantNom(), p.getClasseNom()));
            switch (p.getStatut()) {
                case "Présent" -> row.presents++;
                case "Absent" -> row.absents++;
                case "Retard" -> row.retards++;
            }
        }
        for (EtudiantStatRow row : statsMap.values()) {
            int total = row.presents + row.absents + row.retards;
            row.taux = total > 0 ? (row.presents * 100.0 / total) : 0;
        }
        statsTable.setItems(FXCollections.observableArrayList(statsMap.values()));
        statsBox.getChildren().addAll(statsTitle, statsTable);

        page.getChildren().addAll(filters, sessionsList, statsBox);
        contentArea.getChildren().add(page);
    }

    // Classe interne pour les statsétudiants
    private static class EtudiantStatRow {
        String nom;
        String classe;
        int presents;
        int absents;
        int retards;
        double taux;

        EtudiantStatRow(String nom, String classe) {
            this.nom = nom;
            this.classe = classe;
        }
    }

    // ====================================================================
    // SUIVI ACTIVITÉ — RAPPORTS DE SÉANCE
    // ====================================================================

    private void showRapportsAdmin() {
        contentArea.getChildren().clear();
        VBox page = new VBox(20);
        page.setPadding(new Insets(20));

        HBox titleBar = makeSectionHeader("📋", "Rapports de Séance", "Tous les rapports transmis par les enseignants");
        page.getChildren().add(titleBar);

        // Filtres
        HBox filters = new HBox(15);
        filters.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label lEns = makeFLabel("Enseignant :");
        ComboBox<String> cbEns = new ComboBox<>();
        cbEns.getItems().add("Tous");
        dataStore.getAllEnseignants().stream().map(e -> e.getPrenom() + " " + e.getNom())
                .forEach(cbEns.getItems()::add);
        cbEns.setValue("Tous");
        cbEns.setPrefWidth(160);

        Label lCl = makeFLabel("Classe :");
        ComboBox<String> cbCl = new ComboBox<>();
        cbCl.getItems().add("Toutes");
        dataStore.getAllClasses().stream().map(Classe::getNom).forEach(cbCl.getItems()::add);
        cbCl.setValue("Toutes");
        cbCl.setPrefWidth(130);

        Label lStat = makeFLabel("Statut :");
        ComboBox<String> cbStat = new ComboBox<>();
        cbStat.getItems().addAll("Tous", "Complété", "En attente");
        cbStat.setValue("Tous");
        cbStat.setPrefWidth(120);

        Button btnFilter = new Button("🔍 Filtrer");
        btnFilter.setStyle(BTN_SUBMIT);

        filters.getChildren().addAll(lEns, cbEns, lCl, cbCl, lStat, cbStat, btnFilter);

        VBox rapportsList = new VBox(10);

        Runnable applyFilter = () -> {
            rapportsList.getChildren().clear();
            String filterEns = cbEns.getValue();
            String filterCl = cbCl.getValue();
            String filterStat = cbStat.getValue();

            List<DataStore.RapportSeanceEntry> all = dataStore.getAllRapports().stream()
                    .filter(r -> "Tous".equals(filterEns) || r.getEnseignantNom().equals(filterEns))
                    .filter(r -> "Toutes".equals(filterCl) || r.getClasseNom().equals(filterCl))
                    .filter(r -> "Tous".equals(filterStat) || r.getStatut().equals(filterStat))
                    .sorted(java.util.Comparator.comparing(DataStore.RapportSeanceEntry::getDate).reversed())
                    .collect(java.util.stream.Collectors.toList());

            if (all.isEmpty()) {
                rapportsList.getChildren().add(makePlaceholder("Aucun rapport trouvé"));
                return;
            }

            // En-tête résumé
            long completes = all.stream().filter(r -> "Complété".equals(r.getStatut())).count();
            long attente = all.stream().filter(r -> "En attente".equals(r.getStatut())).count();
            HBox summary = new HBox(12);
            summary.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            summary.setPadding(new Insets(10, 14, 10, 14));
            summary.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");
            summary.getChildren().addAll(
                    makeAdminBadge(all.size() + " rapport(s)", "#304F6D"),
                    makeAdminBadge("✅ " + completes + " complété(s)", "#27ae60"),
                    makeAdminBadge("⏳ " + attente + " en attente", "#e67e22"));
            rapportsList.getChildren().add(summary);

            for (DataStore.RapportSeanceEntry r : all) {
                VBox card = new VBox(8);
                card.setPadding(new Insets(14));
                card.setStyle(CARD_STYLE);

                HBox top = new HBox(10);
                top.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                VBox infos = new VBox(3);
                Label lMat = new Label("📚 " + r.getMatiereNom() + " — " + r.getClasseNom());
                lMat.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
                Label lEnsLabel = new Label("👨‍🏫 " + r.getEnseignantNom() + "  ·  📅 "
                        + r.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                lEnsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #636e72;");
                infos.getChildren().addAll(lMat, lEnsLabel);
                HBox.setHgrow(infos, javafx.scene.layout.Priority.ALWAYS);

                String sc = "Complété".equals(r.getStatut())
                        ? "-fx-background-color: #27ae60; -fx-text-fill: white;"
                        : "-fx-background-color: #f39c12; -fx-text-fill: #333;";
                Label lStatut = new Label(r.getStatut());
                lStatut.setStyle(sc
                        + " -fx-padding: 3 10 3 10; -fx-background-radius: 12; -fx-font-size: 11px; -fx-font-weight: bold;");
                top.getChildren().addAll(infos, lStatut);

                // Contenu
                VBox details = new VBox(4);
                if (r.getContenu() != null && !r.getContenu().isBlank()) {
                    Label lC = new Label("📌 Contenu : " + r.getContenu());
                    lC.setStyle("-fx-font-size: 12px; -fx-text-fill: #2d3436;");
                    lC.setWrapText(true);
                    details.getChildren().add(lC);
                }
                if (r.getObjectifs() != null && !r.getObjectifs().isBlank()) {
                    Label lO = new Label("🎯 Objectifs : " + r.getObjectifs());
                    lO.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");
                    lO.setWrapText(true);
                    details.getChildren().add(lO);
                }
                if (r.getObservations() != null && !r.getObservations().isBlank()) {
                    Label lOb = new Label("💬 Observations : " + r.getObservations());
                    lOb.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");
                    lOb.setWrapText(true);
                    details.getChildren().add(lOb);
                }

                card.getChildren().addAll(top, details);
                rapportsList.getChildren().add(card);
            }
        };

        btnFilter.setOnAction(e -> applyFilter.run());
        applyFilter.run();
        page.getChildren().addAll(filters, rapportsList);
        contentArea.getChildren().add(page);
    }

    // ====================================================================
    // SUIVI ACTIVITÉ — CAHIER DE TEXTE
    // ====================================================================

    private void showCahierAdmin() {
        contentArea.getChildren().clear();
        VBox page = new VBox(20);
        page.setPadding(new Insets(20));

        HBox titleBar = makeSectionHeader("📝", "Cahier de Texte", "Contenu des séances et devoirs donnés");
        page.getChildren().add(titleBar);

        // Filtres
        HBox filters = new HBox(15);
        filters.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label lEns = makeFLabel("Enseignant :");
        ComboBox<String> cbEns = new ComboBox<>();
        cbEns.getItems().add("Tous");
        dataStore.getAllEnseignants().stream().map(e -> e.getPrenom() + " " + e.getNom())
                .forEach(cbEns.getItems()::add);
        cbEns.setValue("Tous");
        cbEns.setPrefWidth(160);

        Label lCl = makeFLabel("Classe :");
        ComboBox<String> cbCl = new ComboBox<>();
        cbCl.getItems().add("Toutes");
        dataStore.getAllClasses().stream().map(Classe::getNom).forEach(cbCl.getItems()::add);
        cbCl.setValue("Toutes");
        cbCl.setPrefWidth(130);

        Button btnFilter = new Button("🔍 Filtrer");
        btnFilter.setStyle(BTN_SUBMIT);

        filters.getChildren().addAll(lEns, cbEns, lCl, cbCl, btnFilter);

        VBox cahierList = new VBox(10);

        Runnable applyFilter = () -> {
            cahierList.getChildren().clear();
            String filterEns = cbEns.getValue();
            String filterCl = cbCl.getValue();

            List<DataStore.CahierEntry> all = dataStore.getAllCahierEntries().stream()
                    .filter(c -> "Tous".equals(filterEns) || c.getEnseignantNom().equals(filterEns))
                    .filter(c -> "Toutes".equals(filterCl) || c.getClasseNom().equals(filterCl))
                    .sorted(java.util.Comparator.comparing(DataStore.CahierEntry::getDate).reversed())
                    .collect(java.util.stream.Collectors.toList());

            if (all.isEmpty()) {
                cahierList.getChildren().add(makePlaceholder("Aucune entrée trouvée"));
                return;
            }

            // Résumé
            HBox summary = new HBox(12);
            summary.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            summary.setPadding(new Insets(10, 14, 10, 14));
            summary.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");
            long avecDevoirs = all.stream().filter(c -> c.getDevoirs() != null && !c.getDevoirs().isBlank()).count();
            summary.getChildren().addAll(
                    makeAdminBadge(all.size() + " entrée(s)", "#304F6D"),
                    makeAdminBadge("📝 " + avecDevoirs + " avec devoirs", "#e67e22"));
            cahierList.getChildren().add(summary);

            // Grouper par enseignant pour affichage structuré
            java.util.Map<String, List<DataStore.CahierEntry>> byEns = all.stream()
                    .collect(java.util.stream.Collectors.groupingBy(DataStore.CahierEntry::getEnseignantNom,
                            java.util.LinkedHashMap::new, java.util.stream.Collectors.toList()));

            for (java.util.Map.Entry<String, List<DataStore.CahierEntry>> entry : byEns.entrySet()) {
                // En-tête enseignant
                Label ensHeader = new Label("👨‍🏫 " + entry.getKey());
                ensHeader.setStyle(
                        "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #304F6D; -fx-padding: 8 0 4 0;");
                cahierList.getChildren().add(ensHeader);

                for (DataStore.CahierEntry c : entry.getValue()) {
                    VBox card = new VBox(8);
                    card.setPadding(new Insets(14));
                    card.setStyle(CARD_STYLE);

                    // Top
                    HBox top = new HBox(10);
                    top.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                    Label lMat = new Label("📚 " + c.getMatiereNom() + " — " + c.getClasseNom());
                    lMat.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #304F6D;");
                    Region sp = new Region();
                    HBox.setHgrow(sp, javafx.scene.layout.Priority.ALWAYS);
                    Label lDate = new Label(
                            "📅 " + c.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    lDate.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
                    top.getChildren().addAll(lMat, sp, lDate);

                    Label lTitre = new Label("📖 " + c.getTitre());
                    lTitre.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");

                    Label lContenu = new Label(c.getContenu() != null ? c.getContenu() : "");
                    lContenu.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");
                    lContenu.setWrapText(true);

                    card.getChildren().addAll(top, lTitre, lContenu);

                    if (c.getDevoirs() != null && !c.getDevoirs().isBlank()) {
                        HBox dBox = new HBox(6);
                        dBox.setPadding(new Insets(6, 10, 6, 10));
                        dBox.setStyle("-fx-background-color: #fff8e1; -fx-background-radius: 6;");
                        Label dTag = new Label("📌 Devoirs :");
                        dTag.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");
                        Label dVal = new Label(c.getDevoirs());
                        dVal.setStyle("-fx-font-size: 11px; -fx-text-fill: #2d3436;");
                        dVal.setWrapText(true);
                        dBox.getChildren().addAll(dTag, dVal);
                        card.getChildren().add(dBox);
                    }
                    cahierList.getChildren().add(card);
                }
            }
        };

        btnFilter.setOnAction(e -> applyFilter.run());
        applyFilter.run();
        page.getChildren().addAll(filters, cahierList);
        contentArea.getChildren().add(page);
    }

    // ===== HELPERS SUIVI =====

    private HBox makeSectionHeader(String icon, String title, String subtitle) {
        HBox bar = new HBox(14);
        bar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        bar.setPadding(new Insets(18, 20, 18, 20));
        bar.setStyle("-fx-background-color: linear-gradient(to right, #304F6D, #899481); -fx-background-radius: 12;");
        Label ic = new Label(icon);
        ic.setStyle("-fx-font-size: 26px;");
        VBox texts = new VBox(3);
        Label t = new Label(title);
        t.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label s = new Label(subtitle);
        s.setStyle("-fx-font-size: 12px; -fx-text-fill: #d1d5db;");
        texts.getChildren().addAll(t, s);
        bar.getChildren().addAll(ic, texts);
        return bar;
    }

    private Label makeAdminBadge(String text, String color) {
        Label badge = new Label(text);
        badge.setStyle("-fx-background-color: " + color + "20; -fx-text-fill: " + color + "; "
                + "-fx-padding: 3 10 3 10; -fx-background-radius: 12; -fx-font-size: 11px; -fx-font-weight: bold;");
        return badge;
    }

    private Label makeFLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #636e72;");
        return l;
    }

    private javafx.scene.Node makePlaceholder(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 13px; -fx-font-style: italic;");
        HBox box = new HBox(l);
        box.setAlignment(javafx.geometry.Pos.CENTER);
        box.setPadding(new Insets(20));
        return box;
    }
}
