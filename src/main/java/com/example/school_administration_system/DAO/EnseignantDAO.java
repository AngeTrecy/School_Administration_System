package com.example.school_administration_system.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.school_administration_system.model.Utilisateur;
import com.example.school_administration_system.model.Administration;
import com.example.school_administration_system.model.Enseignant;
import com.example.school_administration_system.model.Etudiant;
import com.example.school_administration_system.model.Directeur;


public class EnseignantDAO implements GenericDAO<Enseignant> {

        @Override
        public void create(Enseignant enseignant) {
            String sql = "INSERT INTO enseignant (nom, prenom, email, mot_de_passe, " +
                    "telephone, specialites, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, enseignant.getNom());
                stmt.setString(2, enseignant.getPrenom());
                stmt.setString(3, enseignant.getEmail());
                stmt.setString(4, enseignant.getMotDePasse());
                stmt.setString(5, enseignant.getTelephone());
                stmt.setString(6, String.join(",", enseignant.getSpecialites()));
                stmt.setString(7, enseignant.getGrade());

                stmt.executeUpdate();
                System.out.println("Enseignant cree avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation enseignant: " + e.getMessage());
            }
        }

        @Override
        public Enseignant findById(int id) {
            String sql = "SELECT * FROM enseignant WHERE id = ?";

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
        public List<Enseignant> findAll() {
            List<Enseignant> enseignants = new ArrayList<>();
            String sql = "SELECT * FROM enseignant";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    enseignants.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste: " + e.getMessage());
            }
            return enseignants;
        }

        @Override
        public void update(Enseignant enseignant) {
            String sql = "UPDATE enseignant SET nom=?, prenom=?, email=?, telephone=?, " +
                    "specialites=?, grade=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, enseignant.getNom());
                stmt.setString(2, enseignant.getPrenom());
                stmt.setString(3, enseignant.getEmail());
                stmt.setString(4, enseignant.getTelephone());
                stmt.setString(5, String.join(",", enseignant.getSpecialites()));
                stmt.setString(6, enseignant.getGrade());
                stmt.setInt(7, enseignant.getId());

                stmt.executeUpdate();
                System.out.println("Enseignant mis à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM enseignant WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Enseignant supprime.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression: " + e.getMessage());
            }
        }

        private Enseignant mapResultSet(ResultSet rs) throws SQLException {
            Enseignant enseignant = new Enseignant();
            enseignant.setId(rs.getInt("id"));
            enseignant.setNom(rs.getString("nom"));
            enseignant.setPrenom(rs.getString("prenom"));
            enseignant.setEmail(rs.getString("email"));
            enseignant.setMotDePasse(rs.getString("mot_de_passe"));
            enseignant.setTelephone(rs.getString("telephone"));

            String specialites = rs.getString("specialites");
            if (specialites != null && !specialites.isEmpty()) {
                enseignant.setSpecialites(Arrays.asList(specialites.split(",")));
            }

            enseignant.setGrade(rs.getString("grade"));
            return enseignant;
        }
    }

