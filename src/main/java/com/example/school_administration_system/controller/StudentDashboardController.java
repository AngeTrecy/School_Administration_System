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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StudentDashboardController implements Initializable {

    @FXML
    private StackPane contentArea;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label pageTitle;
    @FXML
    private HBox menuAccueil;
    @FXML
    private HBox menuMaClasse;
    @FXML
    private HBox menuEmploi;
    @FXML
    private HBox menuEnseignants;

    // ===== DESIGN (cohérent avec admin-dashboard) =====
    private static final String PRIMARY = "#304F6D";
    private static final String ACCENT = "#E07D54";
    private static final String BG_COLOR = "#E2F3FD";
    private static final String CARD_STYLE = "-fx-background-color: white; -fx-background-radius: 10; "
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);";
    private static final String SECTION_TITLE_STYLE = "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #304F6D;";
    private static final String MENU_NORMAL = "-fx-background-color: transparent; -fx-cursor: hand; -fx-min-height: 40; -fx-max-height: 40; -fx-background-radius: 8;";
    private static final String MENU_ACTIVE = "-fx-background-color: #E07D54; -fx-cursor: hand; -fx-min-height: 40; -fx-max-height: 40; -fx-background-radius: 8;";
    private static final String MENU_HOVER = "-fx-background-color: #3d6080; -fx-cursor: hand; -fx-min-height: 40; -fx-max-height: 40; -fx-background-radius: 8;";

    private DataStore store;
    private String currentStudentName = "Trecy";
    private String currentClassName = null; // sera déterminé dynamiquement
    private int currentStudentId = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        store = DataStore.getInstance();

        LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", new Locale("fr", "FR"));
        String fd = today.format(fmt);
        dateLabel.setText(fd.substring(0, 1).toUpperCase() + fd.substring(1));
        welcomeLabel.setText("Bonjour, " + currentStudentName + " !");

        // Chercher l'étudiant et sa classe
        findStudentInfo();

        showAccueil();
    }

    public void setStudentInfo(String name, int studentId) {
        this.currentStudentName = name;
        this.currentStudentId = studentId;
        welcomeLabel.setText("Bonjour, " + name + " !");
        findStudentInfo();
        showAccueil();
    }

    private void findStudentInfo() {
        // Trouver l'étudiant par nom dans la base
        List<Etudiant> etudiants = store.getAllEtudiants();
        for (Etudiant e : etudiants) {
            if (e.getNomComplet().toLowerCase().contains(currentStudentName.toLowerCase())) {
                currentStudentId = e.getId();
                String cn = store.getClasseNameForEtudiant(e.getId());
                if (cn != null && !cn.isEmpty()) {
                    currentClassName = cn;
                }
                break;
            }
        }
        if (currentClassName == null) {
            // Fallback : prendre la première classe disponible
            List<Classe> classes = store.getAllClasses();
            if (!classes.isEmpty()) {
                currentClassName = classes.get(0).getNom();
            }
        }
    }

    // ==================== NAVIGATION ====================

    private void setActiveMenu(HBox active) {
        List<HBox> menus = List.of(menuAccueil, menuMaClasse, menuEmploi, menuEnseignants);
        for (HBox m : menus) {
            if (m != null)
                m.setStyle(MENU_NORMAL);
        }
        if (active != null)
            active.setStyle(MENU_ACTIVE);
    }

    @FXML
    private void handleMenuHover(MouseEvent event) {
        HBox box = (HBox) event.getSource();
        if (!box.getStyle().contains(ACCENT)) {
            box.setStyle(MENU_HOVER);
        }
    }

    @FXML
    private void handleMenuExit(MouseEvent event) {
        HBox box = (HBox) event.getSource();
        if (!box.getStyle().contains(ACCENT)) {
            box.setStyle(MENU_NORMAL);
        }
    }

    @FXML
    private void handleTableauDeBord() {
        showAccueil();
    }

    @FXML
    private void handleMaClasse() {
        showMaClasse();
    }

    @FXML
    private void handleEmploiDuTemps() {
        showEmploiDuTemps();
    }

    @FXML
    private void handleEnseignants() {
        showEnseignants();
    }

    @FXML
    private void handleNotifications() {
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

    // ==================== ACCUEIL (Tableau de bord) ====================

    private void showAccueil() {
        setActiveMenu(menuAccueil);
        pageTitle.setText("Tableau de bord");
        contentArea.getChildren().clear();

        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_LEFT);

        // Cartes statistiques
        HBox cards = new HBox(15);

        String classeLabel = currentClassName != null ? currentClassName : "—";

        // Compter les camarades
        int nbCamarades = 0;
        if (currentClassName != null) {
            Classe cl = store.getAllClasses().stream()
                    .filter(c -> c.getNom().equals(currentClassName)).findFirst().orElse(null);
            if (cl != null) {
                nbCamarades = store.getEtudiantsByClasse(cl.getId()).size();
            }
        }

        // Compter les cours aujourd'hui
        LocalDate today = LocalDate.now();
        String jourFr = today.format(DateTimeFormatter.ofPattern("EEEE", new Locale("fr", "FR")));
        String jourCapit = jourFr.substring(0, 1).toUpperCase() + jourFr.substring(1);

        List<DataStore.SeanceEntry> seancesClasse = currentClassName != null
                ? store.getSeanceEntriesByClasse(currentClassName)
                : List.of();
        long coursAujourdhui = seancesClasse.stream()
                .filter(s -> jourCapit.equalsIgnoreCase(s.getJour())).count();

        // Compter les enseignants
        Set<String> enseignantsSet = seancesClasse.stream()
                .map(DataStore.SeanceEntry::getEnseignantNom)
                .collect(Collectors.toSet());

        cards.getChildren().addAll(
                createStatCard("MA CLASSE", classeLabel, "📖", "#3498db"),
                createStatCard("CAMARADES", String.valueOf(nbCamarades), "🎓", ACCENT),
                createStatCard("COURS AUJOURD'HUI", String.valueOf(coursAujourdhui), "📅", "#27ae60"),
                createStatCard("ENSEIGNANTS", String.valueOf(enseignantsSet.size()), "👥", "#9b59b6"));

        // Cours du jour
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
        List<DataStore.SeanceEntry> seancesAujd = seancesClasse.stream()
                .filter(s -> jourCapit.equalsIgnoreCase(s.getJour()))
                .sorted(Comparator.comparing(DataStore.SeanceEntry::getHeureDebut))
                .collect(Collectors.toList());

        if (seancesAujd.isEmpty()) {
            coursList.getChildren().add(makePlaceholder("Aucun cours aujourd'hui"));
        } else {
            for (DataStore.SeanceEntry s : seancesAujd) {
                coursList.getChildren().add(makeCourseRow(
                        s.getHeureDebut() + " - " + s.getHeureFin(),
                        s.getMatiereNom(),
                        s.getEnseignantNom() + " · " + s.getSalle()));
            }
        }
        coursCard.getChildren().addAll(coursHdr, coursList);

        // Enseignants rapides
        VBox ensCard = new VBox(12);
        ensCard.setPadding(new Insets(18));
        ensCard.setStyle(CARD_STYLE);
        ensCard.setPrefWidth(360);
        HBox ensHdr = new HBox(8);
        ensHdr.setAlignment(Pos.CENTER_LEFT);
        Label ensIc = new Label("👥");
        ensIc.setStyle("-fx-font-size: 16px;");
        Label ensTl = new Label("Mes enseignants");
        ensTl.setStyle(SECTION_TITLE_STYLE);
        ensHdr.getChildren().addAll(ensIc, ensTl);

        VBox ensList = new VBox(8);
        if (enseignantsSet.isEmpty()) {
            ensList.getChildren().add(makePlaceholder("Aucun enseignant"));
        } else {
            for (String ensNom : enseignantsSet) {
                Set<String> matieres = seancesClasse.stream()
                        .filter(s -> ensNom.equals(s.getEnseignantNom()))
                        .map(DataStore.SeanceEntry::getMatiereNom)
                        .collect(Collectors.toSet());
                String initials = getInitials(ensNom);
                ensList.getChildren().add(createTeacherItem(initials, ensNom, String.join(", ", matieres)));
            }
        }
        ensCard.getChildren().addAll(ensHdr, ensList);

        HBox bottomSection = new HBox(15);
        HBox.setHgrow(coursCard, Priority.ALWAYS);
        bottomSection.getChildren().addAll(coursCard, ensCard);

        container.getChildren().addAll(cards, bottomSection);

        ScrollPane scroll = wrapInScroll(container);
        contentArea.getChildren().add(scroll);
    }

    // ==================== MA CLASSE ====================

    private void showMaClasse() {
        setActiveMenu(menuMaClasse);
        pageTitle.setText("Ma classe");
        contentArea.getChildren().clear();

        VBox container = new VBox(16);

        if (currentClassName == null) {
            container.getChildren().add(makePlaceholder("Aucune classe attribuée."));
            ScrollPane scroll = wrapInScroll(container);
            contentArea.getChildren().add(scroll);
            return;
        }

        Classe cl = store.getAllClasses().stream()
                .filter(c -> c.getNom().equals(currentClassName)).findFirst().orElse(null);
        List<Etudiant> camarades = cl != null ? store.getEtudiantsByClasse(cl.getId()) : List.of();

        // Carte info classe
        VBox infoCard = new VBox(14);
        infoCard.setPadding(new Insets(20));
        infoCard.setStyle(CARD_STYLE);

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        StackPane iconBox = new StackPane();
        Circle circle = new Circle(24);
        circle.setStyle("-fx-fill: #3498db20; -fx-stroke: #3498db; -fx-stroke-width: 2;");
        Label icon = new Label("📖");
        icon.setStyle("-fx-font-size: 18px;");
        iconBox.getChildren().addAll(circle, icon);

        VBox infos = new VBox(3);
        Label lNom = new Label(currentClassName);
        lNom.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");
        HBox badges = new HBox(8);
        badges.getChildren().addAll(
                makeBadge(camarades.size() + " élèves", "#3498db"),
                makeBadge(cl != null && cl.getNiveau() != null ? cl.getNiveau() : "", "#636e72"));
        infos.getChildren().addAll(lNom, badges);
        HBox.setHgrow(infos, Priority.ALWAYS);
        header.getChildren().addAll(iconBox, infos);

        infoCard.getChildren().add(header);

        // Liste des camarades
        VBox listCard = new VBox(12);
        listCard.setPadding(new Insets(20));
        listCard.setStyle(CARD_STYLE);

        Label listTitle = new Label("Mes camarades (" + camarades.size() + ")");
        listTitle.setStyle(SECTION_TITLE_STYLE);

        VBox rows = new VBox(6);
        if (camarades.isEmpty()) {
            rows.getChildren().add(makePlaceholder("Aucun élève dans cette classe"));
        } else {
            String[] colors = { "#3498db", "#e67e22", "#9b59b6", "#27ae60", "#e74c3c" };
            int ci = 0;
            for (Etudiant et : camarades) {
                String color = colors[ci % colors.length];
                ci++;
                HBox row = new HBox(12);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(10, 14, 10, 14));
                row.setStyle("-fx-background-color: " + color + "10; -fx-background-radius: 8;");

                Circle avatar = new Circle(18, Color.web(color + "30"));
                avatar.setStroke(Color.web(color));
                avatar.setStrokeWidth(1.5);
                Label initLabel = new Label(getInitials(et.getNomComplet()));
                initLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 10px;");
                StackPane avatarPane = new StackPane(avatar, initLabel);

                VBox info = new VBox(2);
                Label nameLabel = new Label(et.getNomComplet());
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
                Label matLabel = new Label(et.getMatricule() != null ? "Matricule : " + et.getMatricule() : "");
                matLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
                info.getChildren().addAll(nameLabel, matLabel);
                HBox.setHgrow(info, Priority.ALWAYS);

                row.getChildren().addAll(avatarPane, info);
                rows.getChildren().add(row);
            }
        }
        listCard.getChildren().addAll(listTitle, rows);

        container.getChildren().addAll(infoCard, listCard);

        ScrollPane scroll = wrapInScroll(container);
        contentArea.getChildren().add(scroll);
    }

    // ==================== EMPLOI DU TEMPS ====================

    private void showEmploiDuTemps() {
        setActiveMenu(menuEmploi);
        pageTitle.setText("Mon emploi du temps");
        contentArea.getChildren().clear();

        List<DataStore.SeanceEntry> seances = currentClassName != null
                ? store.getSeanceEntriesByClasse(currentClassName)
                : List.of();

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
            HBox hdr = new HBox(10);
            hdr.setAlignment(Pos.CENTER_LEFT);
            Label dot = new Label("●");
            dot.setStyle("-fx-font-size: 16px; -fx-text-fill: " + color + ";");
            Label jourLabel = new Label(jour);
            jourLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");
            Label count = new Label("(" + jourSeances.size() + " cours)");
            count.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6;");
            hdr.getChildren().addAll(dot, jourLabel, count);

            jourCard.getChildren().add(hdr);

            for (DataStore.SeanceEntry s : jourSeances) {
                HBox row = new HBox(15);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(10, 14, 10, 14));
                row.setStyle("-fx-background-color: " + color + "15; -fx-background-radius: 8;");

                VBox hBox = new VBox(2);
                Label h1 = new Label(s.getHeureDebut());
                h1.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: " + color + ";");
                Label h2 = new Label(s.getHeureFin());
                h2.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
                hBox.getChildren().addAll(h1, h2);
                hBox.setMinWidth(60);

                Region sep = new Region();
                sep.setPrefWidth(3);
                sep.setPrefHeight(36);
                sep.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 3;");

                VBox info = new VBox(3);
                Label mat = new Label(s.getMatiereNom());
                mat.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
                HBox badgesRow = new HBox(8);
                badgesRow.getChildren().addAll(
                        makeBadge(s.getEnseignantNom(), PRIMARY),
                        makeBadge("📍 " + s.getSalle(), "#636e72"));
                info.getChildren().addAll(mat, badgesRow);
                HBox.setHgrow(info, Priority.ALWAYS);

                row.getChildren().addAll(hBox, sep, info);
                jourCard.getChildren().add(row);
            }

            container.getChildren().add(jourCard);
        }

        if (container.getChildren().isEmpty()) {
            container.getChildren().add(makePlaceholder("Aucun emploi du temps disponible."));
        }

        ScrollPane scroll = wrapInScroll(container);
        contentArea.getChildren().add(scroll);
    }

    // ==================== MES ENSEIGNANTS ====================

    private void showEnseignants() {
        setActiveMenu(menuEnseignants);
        pageTitle.setText("Mes enseignants");
        contentArea.getChildren().clear();

        List<DataStore.SeanceEntry> seances = currentClassName != null
                ? store.getSeanceEntriesByClasse(currentClassName)
                : List.of();

        // Grouper par enseignant
        Map<String, Set<String>> enseignantMatieres = new LinkedHashMap<>();
        Map<String, Long> enseignantNbSeances = new LinkedHashMap<>();
        for (DataStore.SeanceEntry s : seances) {
            enseignantMatieres.computeIfAbsent(s.getEnseignantNom(), k -> new LinkedHashSet<>())
                    .add(s.getMatiereNom());
            enseignantNbSeances.merge(s.getEnseignantNom(), 1L, Long::sum);
        }

        String[] palette = { "#3498db", "#e67e22", "#9b59b6", "#27ae60", "#e74c3c" };
        VBox container = new VBox(12);

        VBox listCard = new VBox(14);
        listCard.setPadding(new Insets(20));
        listCard.setStyle(CARD_STYLE);

        Label listTitle = new Label("Enseignants de " + (currentClassName != null ? currentClassName : "ma classe")
                + " (" + enseignantMatieres.size() + ")");
        listTitle.setStyle(SECTION_TITLE_STYLE);
        listCard.getChildren().add(listTitle);

        if (enseignantMatieres.isEmpty()) {
            listCard.getChildren().add(makePlaceholder("Aucun enseignant trouvé"));
        } else {
            int ci = 0;
            for (Map.Entry<String, Set<String>> entry : enseignantMatieres.entrySet()) {
                String ensNom = entry.getKey();
                Set<String> matieres = entry.getValue();
                String color = palette[ci % palette.length];
                ci++;

                VBox ensCard = new VBox(8);
                ensCard.setPadding(new Insets(14));
                ensCard.setStyle("-fx-background-color: " + color + "08; -fx-background-radius: 10; "
                        + "-fx-border-color: " + color + "30; -fx-border-radius: 10; -fx-border-width: 1;");

                HBox top = new HBox(12);
                top.setAlignment(Pos.CENTER_LEFT);

                Circle avatar = new Circle(22, Color.web(color + "25"));
                avatar.setStroke(Color.web(color));
                avatar.setStrokeWidth(2);
                Label initLabel = new Label(getInitials(ensNom));
                initLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 12px;");
                StackPane avatarPane = new StackPane(avatar, initLabel);

                VBox info = new VBox(3);
                Label nameLabel = new Label(ensNom);
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + PRIMARY + ";");
                HBox badgesRow = new HBox(6);
                for (String m : matieres) {
                    badgesRow.getChildren().add(makeBadge(m, color));
                }
                info.getChildren().addAll(nameLabel, badgesRow);
                HBox.setHgrow(info, Priority.ALWAYS);

                Region sp = new Region();
                HBox.setHgrow(sp, Priority.ALWAYS);

                Label nbSeances = new Label(enseignantNbSeances.getOrDefault(ensNom, 0L) + " séances/sem");
                nbSeances.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");

                top.getChildren().addAll(avatarPane, info, sp, nbSeances);
                ensCard.getChildren().add(top);

                listCard.getChildren().add(ensCard);
            }
        }

        container.getChildren().add(listCard);

        ScrollPane scroll = wrapInScroll(container);
        contentArea.getChildren().add(scroll);
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
        v.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        card.getChildren().addAll(header, v);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private HBox makeCourseRow(String heure, String matiere, String details) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 12, 10, 12));
        row.setStyle("-fx-background-color: #f5f6fa; -fx-background-radius: 7;");

        Label h = new Label(heure);
        h.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #636e72;");
        h.setMinWidth(100);

        VBox info = new VBox(2);
        Label m = new Label(matiere);
        m.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
        Label sub = new Label(details);
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #b2bec3;");
        info.getChildren().addAll(m, sub);
        HBox.setHgrow(info, Priority.ALWAYS);

        row.getChildren().addAll(h, info);
        return row;
    }

    private HBox createTeacherItem(String initials, String name, String subjects) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: #f5f6fa; -fx-background-radius: 7;");

        Circle circle = new Circle(18, Color.web("#d5e8ff"));
        Label initialsLabel = new Label(initials);
        initialsLabel.setStyle("-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 11px;");
        StackPane avatar = new StackPane(circle, initialsLabel);

        VBox info = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2d3436;");
        Label subjectLabel = new Label(subjects);
        subjectLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #b2bec3;");
        info.getChildren().addAll(nameLabel, subjectLabel);

        item.getChildren().addAll(avatar, info);
        return item;
    }

    private Label makeBadge(String text, String color) {
        Label badge = new Label(text);
        badge.setStyle("-fx-background-color: " + color + "20; -fx-text-fill: " + color + "; "
                + "-fx-padding: 2 8 2 8; -fx-background-radius: 10; -fx-font-size: 11px; -fx-font-weight: bold;");
        return badge;
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

    private String getInitials(String name) {
        if (name == null || name.isEmpty())
            return "?";
        String[] parts = name.trim().split("\\s+");
        if (parts.length >= 2) {
            return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        }
        return ("" + parts[0].charAt(0)).toUpperCase();
    }
}