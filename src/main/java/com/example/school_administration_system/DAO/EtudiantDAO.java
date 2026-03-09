package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.Etudiant;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    public void addEtudiant(Etudiant etudiant, int classeId) {
        String sql = "INSERT INTO etudiants (nom, prenom, email, mot_de_passe, telephone, matricule, date_naissance, lieu_naissance, nom_pere, nom_mere, telephone_parent, classe_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, etudiant.getNom());
            pstmt.setString(2, etudiant.getPrenom());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getMotDePasse());
            pstmt.setString(5, etudiant.getTelephone());
            pstmt.setString(6, etudiant.getMatricule());
            pstmt.setDate(7, etudiant.getDateNaissance() != null ? Date.valueOf(etudiant.getDateNaissance()) : null);
            pstmt.setString(8, etudiant.getAdresse());
            pstmt.setString(9, etudiant.getNomPere());
            pstmt.setString(10, etudiant.getNomMere());
            pstmt.setString(11, etudiant.getTelephoneParent());
            if (classeId > 0) {
                pstmt.setInt(12, classeId);
            } else {
                pstmt.setNull(12, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    etudiant.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur EtudiantDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiants";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                etudiants.add(mapResultSetToEtudiant(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return etudiants;
    }

    public Etudiant getEtudiantById(int id) {
        String sql = "SELECT * FROM etudiants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEtudiant(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return null;
    }

    public List<Etudiant> getEtudiantsByClasse(int classeId) {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiants WHERE classe_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    etudiants.add(mapResultSetToEtudiant(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return etudiants;
    }

    public void updateEtudiant(Etudiant etudiant, int classeId) {
        String sql = "UPDATE etudiants SET nom=?, prenom=?, email=?, mot_de_passe=?, telephone=?, matricule=?, date_naissance=?, lieu_naissance=?, nom_pere=?, nom_mere=?, telephone_parent=?, classe_id=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, etudiant.getNom());
            pstmt.setString(2, etudiant.getPrenom());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getMotDePasse());
            pstmt.setString(5, etudiant.getTelephone());
            pstmt.setString(6, etudiant.getMatricule());
            pstmt.setDate(7, etudiant.getDateNaissance() != null ? Date.valueOf(etudiant.getDateNaissance()) : null);
            pstmt.setString(8, etudiant.getAdresse());
            pstmt.setString(9, etudiant.getNomPere());
            pstmt.setString(10, etudiant.getNomMere());
            pstmt.setString(11, etudiant.getTelephoneParent());
            if (classeId > 0) {
                pstmt.setInt(12, classeId);
            } else {
                pstmt.setNull(12, java.sql.Types.INTEGER);
            }
            pstmt.setInt(13, etudiant.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deleteEtudiant(int id) {
        String sql = "DELETE FROM etudiants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public String getClasseNameForEtudiant(int etudiantId) {
        String sql = "SELECT c.nom FROM classes c JOIN etudiants e ON c.id = e.classe_id WHERE e.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, etudiantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nom");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return "";
    }

    public int getClasseIdForEtudiant(int etudiantId) {
        String sql = "SELECT classe_id FROM etudiants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, etudiantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("classe_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return 0;
    }

    private Etudiant mapResultSetToEtudiant(ResultSet rs) throws SQLException {
        Etudiant e = new Etudiant();
        e.setId(rs.getInt("id"));
        e.setNom(rs.getString("nom"));
        e.setPrenom(rs.getString("prenom"));
        e.setEmail(rs.getString("email"));
        e.setMotDePasse(rs.getString("mot_de_passe"));
        e.setTelephone(rs.getString("telephone"));
        e.setMatricule(rs.getString("matricule"));
        Date dateObj = rs.getDate("date_naissance");
        if (dateObj != null) {
            e.setDateNaissance(dateObj.toLocalDate());
        }
        e.setAdresse(rs.getString("lieu_naissance"));
        e.setNomPere(rs.getString("nom_pere"));
        e.setNomMere(rs.getString("nom_mere"));
        e.setTelephoneParent(rs.getString("telephone_parent"));
        return e;
    }
}
