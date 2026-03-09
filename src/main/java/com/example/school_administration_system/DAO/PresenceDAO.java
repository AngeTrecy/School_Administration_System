package com.example.school_administration_system.DAO;

import com.example.school_administration_system.service.DataStore.PresenceEntry;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresenceDAO {

    public void addPresence(PresenceEntry p) {
        String sql = "INSERT INTO presences (enseignant_nom, matiere_nom, classe_nom, date_presence, etudiant_nom, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, p.getEnseignantNom());
            pstmt.setString(2, p.getMatiereNom());
            pstmt.setString(3, p.getClasseNom());
            pstmt.setDate(4, p.getDate() != null ? Date.valueOf(p.getDate()) : null);
            pstmt.setString(5, p.getEtudiantNom());
            pstmt.setString(6, p.getStatut());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public List<PresenceEntry> getAllPresences() {
        List<PresenceEntry> presences = new ArrayList<>();
        String sql = "SELECT * FROM presences";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                presences.add(mapResultSetToPresenceEntry(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return presences;
    }

    public List<PresenceEntry> getPresencesByEnseignant(String enseignantNom) {
        List<PresenceEntry> presences = new ArrayList<>();
        String sql = "SELECT * FROM presences WHERE enseignant_nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enseignantNom);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    presences.add(mapResultSetToPresenceEntry(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return presences;
    }

    public List<PresenceEntry> getPresencesBySession(String enseignantNom, String matiereNom, String classeNom,
            LocalDate date) {
        List<PresenceEntry> presences = new ArrayList<>();
        String sql = "SELECT * FROM presences WHERE enseignant_nom=? AND matiere_nom=? AND classe_nom=? AND date_presence=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, enseignantNom);
            pstmt.setString(2, matiereNom);
            pstmt.setString(3, classeNom);
            pstmt.setDate(4, Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    presences.add(mapResultSetToPresenceEntry(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return presences;
    }

    public void updatePresence(PresenceEntry p) {
        String sql = "UPDATE presences SET enseignant_nom=?, matiere_nom=?, classe_nom=?, date_presence=?, etudiant_nom=?, statut=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getEnseignantNom());
            pstmt.setString(2, p.getMatiereNom());
            pstmt.setString(3, p.getClasseNom());
            pstmt.setDate(4, p.getDate() != null ? Date.valueOf(p.getDate()) : null);
            pstmt.setString(5, p.getEtudiantNom());
            pstmt.setString(6, p.getStatut());
            pstmt.setInt(7, p.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deletePresencesBySession(String enseignantNom, String matiereNom, String classeNom, LocalDate date) {
        String sql = "DELETE FROM presences WHERE enseignant_nom=? AND matiere_nom=? AND classe_nom=? AND date_presence=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, enseignantNom);
            pstmt.setString(2, matiereNom);
            pstmt.setString(3, classeNom);
            pstmt.setDate(4, Date.valueOf(date));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private PresenceEntry mapResultSetToPresenceEntry(ResultSet rs) throws SQLException {
        PresenceEntry entry = new PresenceEntry(
                rs.getString("enseignant_nom"),
                rs.getString("matiere_nom"),
                rs.getString("classe_nom"),
                rs.getDate("date_presence") != null ? rs.getDate("date_presence").toLocalDate() : null,
                rs.getString("etudiant_nom"),
                rs.getString("statut"));
        entry.setId(rs.getInt("id"));
        return entry;
    }
}
