package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.Classe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


    public class ClasseDAO implements GenericDAO<Classe> {

        @Override
        public void create(Classe classe) {
            String sql = "INSERT INTO classe (nom, niveau, capacite_max, annee_academique) " +
                    "VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, classe.getNom());
                stmt.setString(2, classe.getNiveau());
                stmt.setInt(3, classe.getCapaciteMax());
                stmt.setString(4, classe.getAnneeAcademique());

                stmt.executeUpdate();
                System.out.println("Classe creee avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation classe: " + e.getMessage());
            }
        }

        @Override
        public Classe findById(int id) {
            String sql = "SELECT * FROM classe WHERE id = ?";

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
        public List<Classe> findAll() {
            List<Classe> classes = new ArrayList<>();
            String sql = "SELECT * FROM classe";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    classes.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste: " + e.getMessage());
            }
            return classes;
        }

        @Override
        public void update(Classe classe) {
            String sql = "UPDATE classe SET nom=?, niveau=?, capacite_max=?, " +
                    "annee_academique=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, classe.getNom());
                stmt.setString(2, classe.getNiveau());
                stmt.setInt(3, classe.getCapaciteMax());
                stmt.setString(4, classe.getAnneeAcademique());
                stmt.setInt(5, classe.getId());

                stmt.executeUpdate();
                System.out.println("Classe mise à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM classe WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Classe supprimee.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression: " + e.getMessage());
            }
        }

        private Classe mapResultSet(ResultSet rs) throws SQLException {
            Classe classe = new Classe();
            classe.setId(rs.getInt("id"));
            classe.setNom(rs.getString("nom"));
            classe.setNiveau(rs.getString("niveau"));
            classe.setCapaciteMax(rs.getInt("capacite_max"));
            classe.setAnneeAcademique(rs.getString("annee_academique"));
            return classe;
        }
    }

