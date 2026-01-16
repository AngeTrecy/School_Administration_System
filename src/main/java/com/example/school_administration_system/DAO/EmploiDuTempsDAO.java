package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.EmploiDuTemps;
import com.example.school_administration_system.model.Seance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class EmploiDuTempsDAO implements GenericDAO<EmploiDuTemps> {

        @Override
        public void create(EmploiDuTemps emploi) {
            String sql = "INSERT INTO emploi_du_temps (date_debut, date_fin, statut, valide, classe_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setDate(1, Date.valueOf(emploi.getDateDebut()));
                stmt.setDate(2, Date.valueOf(emploi.getDateFin()));
                stmt.setString(3, emploi.getStatut());
                stmt.setBoolean(4, emploi.isValide());
                stmt.setInt(5, 0); // classe_id - à adapter selon votre logique

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    emploi.setId(rs.getInt(1));
                }

                System.out.println("Emploi du temps cree avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation emploi du temps: " + e.getMessage());
            }
        }

        @Override
        public EmploiDuTemps findById(int id) {
            String sql = "SELECT * FROM emploi_du_temps WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    EmploiDuTemps emploi = mapResultSet(rs);
                    // Charger les séances associées
                    emploi.setSeances(findSeancesByEmploiId(id));
                    return emploi;
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche emploi du temps: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<EmploiDuTemps> findAll() {
            List<EmploiDuTemps> emplois = new ArrayList<>();
            String sql = "SELECT * FROM emploi_du_temps";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    EmploiDuTemps emploi = mapResultSet(rs);
                    emploi.setSeances(findSeancesByEmploiId(emploi.getId()));
                    emplois.add(emploi);
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste emplois du temps: " + e.getMessage());
            }
            return emplois;
        }

        @Override
        public void update(EmploiDuTemps emploi) {
            String sql = "UPDATE emploi_du_temps SET date_debut=?, date_fin=?, statut=?, valide=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDate(1, Date.valueOf(emploi.getDateDebut()));
                stmt.setDate(2, Date.valueOf(emploi.getDateFin()));
                stmt.setString(3, emploi.getStatut());
                stmt.setBoolean(4, emploi.isValide());
                stmt.setInt(5, emploi.getId());

                stmt.executeUpdate();
                System.out.println("Emploi du temps mis à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour emploi du temps: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM emploi_du_temps WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Emploi du temps supprime.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression emploi du temps: " + e.getMessage());
            }
        }

        public List<EmploiDuTemps> findByClasse(int classeId) {
            List<EmploiDuTemps> emplois = new ArrayList<>();
            String sql = "SELECT * FROM emploi_du_temps WHERE classe_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, classeId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    EmploiDuTemps emploi = mapResultSet(rs);
                    emploi.setSeances(findSeancesByEmploiId(emploi.getId()));
                    emplois.add(emploi);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par classe: " + e.getMessage());
            }
            return emplois;
        }

        public List<EmploiDuTemps> findEnAttente() {
            List<EmploiDuTemps> emplois = new ArrayList<>();
            String sql = "SELECT * FROM emploi_du_temps WHERE valide = false";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    EmploiDuTemps emploi = mapResultSet(rs);
                    emploi.setSeances(findSeancesByEmploiId(emploi.getId()));
                    emplois.add(emploi);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche emplois en attente: " + e.getMessage());
            }
            return emplois;
        }

        private List<Seance> findSeancesByEmploiId(int emploiId) {
            SeanceDAO seanceDAO = new SeanceDAO();
            return seanceDAO.findByEmploiDuTemps(emploiId);
        }

        private EmploiDuTemps mapResultSet(ResultSet rs) throws SQLException {
            EmploiDuTemps emploi = new EmploiDuTemps();
            emploi.setId(rs.getInt("id"));
            emploi.setDateDebut(rs.getDate("date_debut").toLocalDate());
            emploi.setDateFin(rs.getDate("date_fin").toLocalDate());
            emploi.setStatut(rs.getString("statut"));
            emploi.setValide(rs.getBoolean("valide"));
            return emploi;
        }
    }


