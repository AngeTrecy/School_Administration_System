package com.example.school_administration_system.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.example.school_administration_system.model.Utilisateur;
import com.example.school_administration_system.model.Administration;
import com.example.school_administration_system.model.Enseignant;
import com.example.school_administration_system.model.Etudiant;
import com.example.school_administration_system.model.Directeur;

    public class DirecteurDAO implements GenericDAO<Directeur> {

        @Override
        public void create(Directeur directeur) {
            String sql = "INSERT INTO directeur (nom, prenom, email, mot_de_passe, telephone) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, directeur.getNom());
                stmt.setString(2, directeur.getPrenom());
                stmt.setString(3, directeur.getEmail());
                stmt.setString(4, directeur.getMotDePasse());
                stmt.setString(5, directeur.getTelephone());

                stmt.executeUpdate();
                System.out.println("Directeur cree avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation directeur: " + e.getMessage());
            }
        }


        public Directeur findById(int id) {
            String sql = "SELECT * FROM directeur WHERE id = ?";

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


        public List<Directeur> findAll() {
            List<Directeur> directeurs = new ArrayList<>();
            String sql = "SELECT * FROM directeur";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    directeurs.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste: " + e.getMessage());
            }
            return directeurs;
        }


        public void update(Directeur directeur) {
            String sql = "UPDATE directeur SET nom=?, prenom=?, email=?, telephone=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, directeur.getNom());
                stmt.setString(2, directeur.getPrenom());
                stmt.setString(3, directeur.getEmail());
                stmt.setString(4, directeur.getTelephone());
                stmt.setInt(5, directeur.getId());

                stmt.executeUpdate();
                System.out.println("Directeur mis à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour: " + e.getMessage());
            }
        }


        public void delete(int id) {
            String sql = "DELETE FROM directeur WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Directeur supprime.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression: " + e.getMessage());
            }
        }

        private Directeur mapResultSet(ResultSet rs) throws SQLException {
            Directeur directeur = new Directeur();
            directeur.setId(rs.getInt("id"));
            directeur.setNom(rs.getString("nom"));
            directeur.setPrenom(rs.getString("prenom"));
            directeur.setEmail(rs.getString("email"));
            directeur.setMotDePasse(rs.getString("mot_de_passe"));
            directeur.setTelephone(rs.getString("telephone"));
            return directeur;
        }
    }

