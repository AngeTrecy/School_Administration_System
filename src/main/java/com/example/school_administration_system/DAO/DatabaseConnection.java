package com.example.school_administration_system.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/gestion_scolaire";
        private static final String USER = "admin_scolaire";
        private static final String PASSWORD = "votre_mot_de_passe";

        private static Connection connection = null;

        private DatabaseConnection() {}

        public static Connection getConnection() {
            try {
                if (connection == null || connection.isClosed()) {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Connexion à la base de donnees reussie.");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Driver MySQL introuvable: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Erreur de connexion: " + e.getMessage());
            }
            return connection;
        }

        public static void closeConnection() {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Connexion fermee.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture: " + e.getMessage());
            }
        }
    }

