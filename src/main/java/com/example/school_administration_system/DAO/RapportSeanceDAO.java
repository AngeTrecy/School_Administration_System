package com.example.school_administration_system.DAO;

import com.example.school_administration_system.service.DataStore.RapportSeanceEntry;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RapportSeanceDAO {

    public void addRapport(RapportSeanceEntry rapport) {
        String sql = "INSERT INTO rapports_seance (enseignant_nom, matiere_nom, classe_nom, date_rapport, contenu, objectifs, observations, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, rapport.getEnseignantNom());
            pstmt.setString(2, rapport.getMatiereNom());
            pstmt.setString(3, rapport.getClasseNom());
            pstmt.setDate(4, rapport.getDate() != null ? Date.valueOf(rapport.getDate()) : null);
            pstmt.setString(5, rapport.getContenu());
            pstmt.setString(6, rapport.getObjectifs());
            pstmt.setString(7, rapport.getObservations());
            pstmt.setString(8, rapport.getStatut());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rapport.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public List<RapportSeanceEntry> getAllRapports() {
        List<RapportSeanceEntry> rapports = new ArrayList<>();
        String sql = "SELECT * FROM rapports_seance";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rapports.add(mapResultSetToRapportSeanceEntry(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return rapports;
    }

    public List<RapportSeanceEntry> getRapportsByEnseignant(String enseignantNom) {
        List<RapportSeanceEntry> rapports = new ArrayList<>();
        String sql = "SELECT * FROM rapports_seance WHERE enseignant_nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enseignantNom);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    rapports.add(mapResultSetToRapportSeanceEntry(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return rapports;
    }

    public void updateRapport(RapportSeanceEntry rapport) {
        String sql = "UPDATE rapports_seance SET enseignant_nom=?, matiere_nom=?, classe_nom=?, date_rapport=?, contenu=?, objectifs=?, observations=?, statut=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rapport.getEnseignantNom());
            pstmt.setString(2, rapport.getMatiereNom());
            pstmt.setString(3, rapport.getClasseNom());
            pstmt.setDate(4, rapport.getDate() != null ? Date.valueOf(rapport.getDate()) : null);
            pstmt.setString(5, rapport.getContenu());
            pstmt.setString(6, rapport.getObjectifs());
            pstmt.setString(7, rapport.getObservations());
            pstmt.setString(8, rapport.getStatut());
            pstmt.setInt(9, rapport.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deleteRapport(int id) {
        String sql = "DELETE FROM rapports_seance WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private RapportSeanceEntry mapResultSetToRapportSeanceEntry(ResultSet rs) throws SQLException {
        RapportSeanceEntry entry = new RapportSeanceEntry(
                rs.getString("enseignant_nom"),
                rs.getString("matiere_nom"),
                rs.getString("classe_nom"),
                rs.getDate("date_rapport") != null ? rs.getDate("date_rapport").toLocalDate() : null,
                rs.getString("contenu"),
                rs.getString("objectifs"),
                rs.getString("observations"),
                rs.getString("statut"));
        entry.setId(rs.getInt("id"));
        return entry;
    }
}
