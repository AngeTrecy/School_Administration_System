package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.Matiere;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereDAO {

    public void addMatiere(Matiere matiere) {
        String sql = "INSERT INTO matieres (code, nom, coefficient, credits) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, matiere.getCode());
            pstmt.setString(2, matiere.getNom());
            // Matiere in model uses float for coefficient, we use it
            pstmt.setFloat(3, matiere.getCoefficient());
            // Mapping nbHeures in model to credits in db
            pstmt.setInt(4, matiere.getNbHeures());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    matiere.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public List<Matiere> getAllMatieres() {
        List<Matiere> matieres = new ArrayList<>();
        String sql = "SELECT * FROM matieres";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Matiere m = new Matiere();
                m.setId(rs.getInt("id"));
                m.setCode(rs.getString("code"));
                m.setNom(rs.getString("nom"));
                m.setCoefficient(rs.getFloat("coefficient"));
                m.setNbHeures(rs.getInt("credits"));
                matieres.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return matieres;
    }

    public Matiere getMatiereById(int id) {
        String sql = "SELECT * FROM matieres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Matiere m = new Matiere();
                    m.setId(rs.getInt("id"));
                    m.setCode(rs.getString("code"));
                    m.setNom(rs.getString("nom"));
                    m.setCoefficient(rs.getFloat("coefficient"));
                    m.setNbHeures(rs.getInt("credits"));
                    return m;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return null;
    }

    public void updateMatiere(Matiere matiere) {
        String sql = "UPDATE matieres SET code=?, nom=?, coefficient=?, credits=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, matiere.getCode());
            pstmt.setString(2, matiere.getNom());
            pstmt.setFloat(3, matiere.getCoefficient());
            pstmt.setInt(4, matiere.getNbHeures());
            pstmt.setInt(5, matiere.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    public void deleteMatiere(int id) {
        String sql = "DELETE FROM matieres WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
