module com.example.school_administration_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;
    requires java.sql;

    // Ouvrir les packages pour JavaFX
    opens com.example.school_administration_system to javafx.fxml;
    opens com.example.school_administration_system.controller to javafx.fxml;
    opens com.example.school_administration_system.model to javafx.base;

    // Exporter les packages
    exports com.example.school_administration_system;
    exports com.example.school_administration_system.controller;
    exports com.example.school_administration_system.model;
    exports com.example.school_administration_system.service;
    exports com.example.school_administration_system.DAO;
}