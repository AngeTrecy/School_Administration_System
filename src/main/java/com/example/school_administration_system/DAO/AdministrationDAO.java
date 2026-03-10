package com.example.school_administration_system.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.example.school_administration_system.model.Utilisateur;
import com.example.school_administration_system.model.Administration;
import com.example.school_administration_system.model.Enseignant;
import com.example.school_administration_system.model.Etudiant;
import com.example.school_administration_system.model.Directeur;


    public class AdministrationDAO implements GenericDAO<Administration> {

        @Override
        public void create(Administration admin) {
            String sql = "INSERT INTO Administration (nom, prenom, email, mot_de_passe, " +
                    "telephone, date_autorisation, niveau_acces) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, admin.getNom());
                stmt.setString(2, admin.getPrenom());
                stmt.setString(3, admin.getEmail());
                stmt.setString(4, admin.getMotDePasse());
                stmt.setString(5, admin.getTelephone());
                stmt.setDate(6, Date.valueOf(admin.getDateAutorisation()));
                stmt.setString(7, admin.getNiveauAcces());

                stmt.executeUpdate();
                System.out.println("Administration cree avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation Administration: " + e.getMessage());
            }
        }

        @Override
        public Administration findById(int id) {
            String sql = "SELECT * FROM Administration WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<Administration> findAll() {
            List<Administration> admins = new ArrayList<>();
            String sql = "SELECT * FROM Administration";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    admins.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste: " + e.getMessage());
            }
            return admins;
        }

        @Override
        public void update(Administration admin) {
            String sql = "UPDATE Administration SET nom=?, prenom=?, email=?, telephone=?, " +
                    "niveau_acces=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, admin.getNom());
                stmt.setString(2, admin.getPrenom());
                stmt.setString(3, admin.getEmail());
                stmt.setString(4, admin.getTelephone());
                stmt.setString(5, admin.getNiveauAcces());
                stmt.setInt(6, admin.getId());

                stmt.executeUpdate();
                System.out.println("Administration mis à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM Administration WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Administration supprime.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression: " + e.getMessage());
            }
        }

        private Administration mapResultSet(ResultSet rs) throws SQLException {
            Administration admin = new Administration();
            admin.setId(rs.getInt("id"));
            admin.setNom(rs.getString("nom"));
            admin.setPrenom(rs.getString("prenom"));
            admin.setEmail(rs.getString("email"));
            admin.setMotDePasse(rs.getString("mot_de_passe"));
            admin.setTelephone(rs.getString("telephone"));
            admin.setDateAutorisation(rs.getDate("date_autorisation").toLocalDate());
            admin.setNiveauAcces(rs.getString("niveau_acces"));
            return admin;
        }
    }

