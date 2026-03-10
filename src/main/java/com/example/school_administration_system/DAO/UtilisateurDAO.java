package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.*;
import java.sql.*;

public class UtilisateurDAO {

    public Utilisateur authenticate(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'authentification: " + e.getMessage());
        }
        return null;
    }

    public String getUserRole(int userId) {
        String sql = "SELECT type_utilisateur FROM utilisateur WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("type_utilisateur");
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return null;
    }

    private Utilisateur mapResultSetToUser(ResultSet rs) throws SQLException {
        String typeUtilisateur = rs.getString("type_utilisateur");

        switch (typeUtilisateur) {
            case "ADMINISTRATION":
                Administration admin = new Administration();
                admin.setId(rs.getInt("id"));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setEmail(rs.getString("email"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));
                admin.setTelephone(rs.getString("telephone"));
                admin.setNiveauAcces(rs.getString("niveau_acces"));
                if (rs.getDate("date_autorisation") != null) {
                    admin.setDateAutorisation(rs.getDate("date_autorisation").toLocalDate());
                }
                return admin;

            case "ENSEIGNANT":
                Enseignant enseignant = new Enseignant();
                enseignant.setId(rs.getInt("id"));
                enseignant.setNom(rs.getString("nom"));
                enseignant.setPrenom(rs.getString("prenom"));
                enseignant.setEmail(rs.getString("email"));
                enseignant.setMotDePasse(rs.getString("mot_de_passe"));
                enseignant.setTelephone(rs.getString("telephone"));
                enseignant.setGrade(rs.getString("grade"));
                return enseignant;

            case "ETUDIANT":
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setEmail(rs.getString("email"));
                etudiant.setMotDePasse(rs.getString("mot_de_passe"));
                etudiant.setTelephone(rs.getString("telephone"));
                etudiant.setMatricule(rs.getString("matricule"));
                if (rs.getDate("date_naissance") != null) {
                    etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                }
                etudiant.setAdresse(rs.getString("adresse"));
                return etudiant;

            case "DIRECTEUR":
                Directeur directeur = new Directeur();
                directeur.setId(rs.getInt("id"));
                directeur.setNom(rs.getString("nom"));
                directeur.setPrenom(rs.getString("prenom"));
                directeur.setEmail(rs.getString("email"));
                directeur.setMotDePasse(rs.getString("mot_de_passe"));
                directeur.setTelephone(rs.getString("telephone"));
                return directeur;

            default:
                return null;
        }
    }
}


