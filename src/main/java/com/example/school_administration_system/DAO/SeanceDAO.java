package com.example.school_administration_system.DAO;

import com.example.school_administration_system.service.DataStore.SeanceEntry;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO {

    public void addSeanceEntry(SeanceEntry entry) {
        String sql = "INSERT INTO seances (classe_nom, matiere_nom, enseignant_nom, jour, heure_debut, heure_fin, salle) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, entry.getClasseNom());
            pstmt.setString(2, entry.getMatiereNom());
            pstmt.setString(3, entry.getEnseignantNom());
            pstmt.setString(4, entry.getJour());
            pstmt.setString(5, entry.getHeureDebut());
            pstmt.setString(6, entry.getHeureFin());
            pstmt.setString(7, entry.getSalle());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entry.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public List<SeanceEntry> getAllSeanceEntries() {
        List<SeanceEntry> seances = new ArrayList<>();
        String sql = "SELECT * FROM seances";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                seances.add(mapResultSetToSeanceEntry(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return seances;
    }

    public List<SeanceEntry> getSeanceEntriesByClasse(String classeNom) {
        List<SeanceEntry> seances = new ArrayList<>();
        String sql = "SELECT * FROM seances WHERE classe_nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classeNom);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    seances.add(mapResultSetToSeanceEntry(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return seances;
    }

    public void updateSeanceEntry(SeanceEntry entry) {
        String sql = "UPDATE seances SET classe_nom=?, matiere_nom=?, enseignant_nom=?, jour=?, heure_debut=?, heure_fin=?, salle=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getClasseNom());
            pstmt.setString(2, entry.getMatiereNom());
            pstmt.setString(3, entry.getEnseignantNom());
            pstmt.setString(4, entry.getJour());
            pstmt.setString(5, entry.getHeureDebut());
            pstmt.setString(6, entry.getHeureFin());
            pstmt.setString(7, entry.getSalle());
            pstmt.setInt(8, entry.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deleteSeanceEntry(int id) {
        String sql = "DELETE FROM seances WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private SeanceEntry mapResultSetToSeanceEntry(ResultSet rs) throws SQLException {
        SeanceEntry entry = new SeanceEntry(
                rs.getString("classe_nom"),
                rs.getString("matiere_nom"),
                rs.getString("enseignant_nom"),
                rs.getString("jour"),
                rs.getString("heure_debut"),
                rs.getString("heure_fin"),
                rs.getString("salle"));
        entry.setId(rs.getInt("id"));
        return entry;
    }
}
