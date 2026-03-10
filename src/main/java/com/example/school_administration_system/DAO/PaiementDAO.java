package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.Paiement;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO {

    public void addPaiement(Paiement paiement) {
        String sql = "INSERT INTO paiements (motif, detail, montant, mode_paiement, annee_scolaire, etudiant_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, paiement.getTypePaiement()); // mapped to motif
            pstmt.setString(2, paiement.getTranche()); // mapped to detail
            pstmt.setDouble(3, paiement.getMontant());
            pstmt.setString(4, paiement.getModePaiement());
            pstmt.setString(5, paiement.getAnneeAcademique());
            pstmt.setInt(6, paiement.getEtudiantId());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paiement.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                paiements.add(mapResultSetToPaiement(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return paiements;
    }

    public Paiement getPaiementById(int id) {
        String sql = "SELECT * FROM paiements WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaiement(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return null;
    }

    public List<Paiement> getPaiementsByEtudiant(int etudiantId) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements WHERE etudiant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, etudiantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapResultSetToPaiement(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return paiements;
    }

    public void updatePaiement(Paiement paiement) {
        String sql = "UPDATE paiements SET motif=?, detail=?, montant=?, mode_paiement=?, annee_scolaire=?, etudiant_id=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paiement.getTypePaiement());
            pstmt.setString(2, paiement.getTranche());
            pstmt.setDouble(3, paiement.getMontant());
            pstmt.setString(4, paiement.getModePaiement());
            pstmt.setString(5, paiement.getAnneeAcademique());
            pstmt.setInt(6, paiement.getEtudiantId());
            pstmt.setInt(7, paiement.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deletePaiement(int id) {
        String sql = "DELETE FROM paiements WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private Paiement mapResultSetToPaiement(ResultSet rs) throws SQLException {
        Paiement p = new Paiement();
        p.setId(rs.getInt("id"));
        p.setEtudiantId(rs.getInt("etudiant_id"));
        p.setTypePaiement(rs.getString("motif"));
        p.setTranche(rs.getString("detail"));
        p.setMontant(rs.getDouble("montant"));
        p.setModePaiement(rs.getString("mode_paiement"));
        p.setAnneeAcademique(rs.getString("annee_scolaire"));
        // we omit reference and status as they are generated locally or default
        // to "Payé" inside model unless schema is updated to add them
        return p;
    }
}
