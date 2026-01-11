package com.example.school_administration_system.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.example.school_administration_system.model.Utilisateur;
import com.example.school_administration_system.model.Administration;
import com.example.school_administration_system.model.Enseignant;
import com.example.school_administration_system.model.Etudiant;
import com.example.school_administration_system.model.Directeur;


    public class EtudiantDAO implements GenericDAO<Etudiant> {

        @Override
        public void create(Etudiant etudiant) {
            String sql = "INSERT INTO etudiant (nom, prenom, email, mot_de_passe, telephone, " +
                    "matricule, date_naissance, adresse) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, etudiant.getNom());
                stmt.setString(2, etudiant.getPrenom());
                stmt.setString(3, etudiant.getEmail());
                stmt.setString(4, etudiant.getMotDePasse());
                stmt.setString(5, etudiant.getTelephone());
                stmt.setString(6, etudiant.getMatricule());
                stmt.setDate(7, Date.valueOf(etudiant.getDateNaissance()));
                stmt.setString(8, etudiant.getAdresse());

                stmt.executeUpdate();
                System.out.println("etudiant cree avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation etudiant: " + e.getMessage());
            }
        }
        public Etudiant findById(int id) {
            String sql = "SELECT * FROM etudiant WHERE id = ?";

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


        public List<Etudiant> findAll() {
            List<Etudiant> etudiants = new ArrayList<>();
            String sql = "SELECT * FROM etudiant";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    etudiants.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste: " + e.getMessage());
            }
            return etudiants;
        }


        public void update(Etudiant etudiant) {
            String sql = "UPDATE etudiant SET nom=?, prenom=?, email=?, telephone=?, " +
                    "matricule=?, date_naissance=?, adresse=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, etudiant.getNom());
                stmt.setString(2, etudiant.getPrenom());
                stmt.setString(3, etudiant.getEmail());
                stmt.setString(4, etudiant.getTelephone());
                stmt.setString(5, etudiant.getMatricule());
                stmt.setDate(6, Date.valueOf(etudiant.getDateNaissance()));
                stmt.setString(7, etudiant.getAdresse());
                stmt.setInt(8, etudiant.getId());

                stmt.executeUpdate();
                System.out.println("etudiant mis à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour: " + e.getMessage());
            }
        }


        public void delete(int id) {
            String sql = "DELETE FROM etudiant WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("etudiant supprime.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression: " + e.getMessage());
            }
        }

        public List<Etudiant> findByClasse(int classeId) {
            List<Etudiant> etudiants = new ArrayList<>();
            String sql = "SELECT e.* FROM etudiant e " +
                    "JOIN classe_etudiant ce ON e.id = ce.etudiant_id " +
                    "WHERE ce.classe_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, classeId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    etudiants.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par classe: " + e.getMessage());
            }
            return etudiants;
        }

        private Etudiant mapResultSet(ResultSet rs) throws SQLException {
            Etudiant etudiant = new Etudiant();
            etudiant.setId(rs.getInt("id"));
            etudiant.setNom(rs.getString("nom"));
            etudiant.setPrenom(rs.getString("prenom"));
            etudiant.setEmail(rs.getString("email"));
            etudiant.setMotDePasse(rs.getString("mot_de_passe"));
            etudiant.setTelephone(rs.getString("telephone"));
            etudiant.setMatricule(rs.getString("matricule"));
            etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            etudiant.setAdresse(rs.getString("adresse"));
            return etudiant;
        }
    }

