package com.example.school_administration_system.DAO;

import com.example.school_administration_system.service.DataStore.CahierEntry;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CahierTexteDAO {

    public void addCahierEntry(CahierEntry entry) {
        String sql = "INSERT INTO cahier_texte (enseignant_nom, matiere_nom, classe_nom, date_cahier, titre, contenu, devoirs) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, entry.getEnseignantNom());
            pstmt.setString(2, entry.getMatiereNom());
            pstmt.setString(3, entry.getClasseNom());
            pstmt.setDate(4, entry.getDate() != null ? Date.valueOf(entry.getDate()) : null);
            pstmt.setString(5, entry.getTitre());
            pstmt.setString(6, entry.getContenu());
            pstmt.setString(7, entry.getDevoirs());

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

    public List<CahierEntry> getAllCahierEntries() {
        List<CahierEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM cahier_texte";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entries.add(mapResultSetToCahierEntry(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return entries;
    }

    public List<CahierEntry> getCahierEntriesByEnseignant(String enseignantNom) {
        List<CahierEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM cahier_texte WHERE enseignant_nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enseignantNom);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    entries.add(mapResultSetToCahierEntry(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return entries;
    }

    public void updateCahierEntry(CahierEntry entry) {
        String sql = "UPDATE cahier_texte SET enseignant_nom=?, matiere_nom=?, classe_nom=?, date_cahier=?, titre=?, contenu=?, devoirs=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getEnseignantNom());
            pstmt.setString(2, entry.getMatiereNom());
            pstmt.setString(3, entry.getClasseNom());
            pstmt.setDate(4, entry.getDate() != null ? Date.valueOf(entry.getDate()) : null);
            pstmt.setString(5, entry.getTitre());
            pstmt.setString(6, entry.getContenu());
            pstmt.setString(7, entry.getDevoirs());
            pstmt.setInt(8, entry.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deleteCahierEntry(int id) {
        String sql = "DELETE FROM cahier_texte WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private CahierEntry mapResultSetToCahierEntry(ResultSet rs) throws SQLException {
        CahierEntry entry = new CahierEntry(
                rs.getString("enseignant_nom"),
                rs.getString("matiere_nom"),
                rs.getString("classe_nom"),
                rs.getDate("date_cahier") != null ? rs.getDate("date_cahier").toLocalDate() : null,
                rs.getString("titre"),
                rs.getString("contenu"),
                rs.getString("devoirs"));
        entry.setId(rs.getInt("id"));
        return entry;
    }
}
