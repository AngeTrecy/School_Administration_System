package com.example.school_administration_system.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    
    private static final String URL = "jdbc:postgresql://localhost:5432/school_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private static Connection connection = null;

    private DatabaseConnection() {
        // Constructeur privé pour empêcher l'instanciation 
    }

  
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base de données réussie !");
            } catch (ClassNotFoundException e) {
                System.err.println("Erreur: Driver JDBC PostgreSQL introuvable.");
                e.printStackTrace();
                throw new SQLException("Impossible de charger le driver JDBC.", e);
            }
        }
        return connection;
    }

    
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Connexion à la base de données fermée.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion.");
                e.printStackTrace();
            }
        }
    }
}
