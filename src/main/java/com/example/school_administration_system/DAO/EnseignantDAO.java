package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.Enseignant;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {

    public void addEnseignant(Enseignant enseignant) {
        String sql = "INSERT INTO enseignants (nom, prenom, email, mot_de_passe, telephone, titre) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getPrenom());
            pstmt.setString(3, enseignant.getEmail());
            pstmt.setString(4, enseignant.getMotDePasse());
            pstmt.setString(5, enseignant.getTelephone());
            pstmt.setString(6, enseignant.getGrade());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    enseignant.setId(generatedKeys.getInt(1));
                    saveSpecialites(enseignant.getId(), enseignant.getSpecialites(), conn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public List<Enseignant> getAllEnseignants() {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM enseignants";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                enseignants.add(mapResultSetToEnseignant(rs, conn));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return enseignants;
    }

    public Enseignant getEnseignantById(int id) {
        String sql = "SELECT * FROM enseignants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEnseignant(rs, conn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return null;
    }

    public void updateEnseignant(Enseignant enseignant) {
        String sql = "UPDATE enseignants SET nom=?, prenom=?, email=?, mot_de_passe=?, telephone=?, titre=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getPrenom());
            pstmt.setString(3, enseignant.getEmail());
            pstmt.setString(4, enseignant.getMotDePasse());
            pstmt.setString(5, enseignant.getTelephone());
            pstmt.setString(6, enseignant.getGrade());
            pstmt.setInt(7, enseignant.getId());

            pstmt.executeUpdate();

            deleteSpecialites(enseignant.getId(), conn);
            saveSpecialites(enseignant.getId(), enseignant.getSpecialites(), conn);

        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deleteEnseignant(int id) {
        String sql = "DELETE FROM enseignants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private Enseignant mapResultSetToEnseignant(ResultSet rs, Connection conn) throws SQLException {
        Enseignant e = new Enseignant();
        e.setId(rs.getInt("id"));
        e.setNom(rs.getString("nom"));
        e.setPrenom(rs.getString("prenom"));
        e.setEmail(rs.getString("email"));
        e.setMotDePasse(rs.getString("mot_de_passe"));
        e.setTelephone(rs.getString("telephone"));
        e.setGrade(rs.getString("titre"));

        List<String> specialites = getSpecialites(e.getId(), conn);
        for (String spec : specialites) {
            e.ajouterSpecialite(spec);
        }
        return e;
    }

    private void saveSpecialites(int enseignantId, List<String> specialites, Connection conn) throws SQLException {
        if (specialites == null || specialites.isEmpty())
            return;
        String sql = "INSERT INTO enseignant_specialites (enseignant_id, specialite) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (String spec : specialites) {
                pstmt.setInt(1, enseignantId);
                pstmt.setString(2, spec);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private void deleteSpecialites(int enseignantId, Connection conn) throws SQLException {
        String sql = "DELETE FROM enseignant_specialites WHERE enseignant_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enseignantId);
            pstmt.executeUpdate();
        }
    }

    private List<String> getSpecialites(int enseignantId, Connection conn) throws SQLException {
        List<String> specialites = new ArrayList<>();
        String sql = "SELECT specialite FROM enseignant_specialites WHERE enseignant_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enseignantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    specialites.add(rs.getString("specialite"));
                }
            }
        }
        return specialites;
    }
}
