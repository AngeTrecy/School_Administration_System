package com.example.school_administration_system.DAO;
import com.example.school_administration_system.model.RapportSeance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class RapportSeanceDAO implements GenericDAO<RapportSeance> {

        @Override
        public void create(RapportSeance rapport) {
            String sql = "INSERT INTO rapport_seance (date, contenu, objectifs, observations, seance_id, enseignant_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setDate(1, Date.valueOf(rapport.getDate()));
                stmt.setString(2, rapport.getContenu());
                stmt.setString(3, rapport.getObjectifs());
                stmt.setString(4, rapport.getObservations());
                stmt.setInt(5, rapport.getSeance().getId());
                stmt.setInt(6, 0); // enseignant_id - à adapter

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    rapport.setId(rs.getInt(1));
                }

                System.out.println("Rapport de séance créé avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur création rapport: " + e.getMessage());
            }
        }

        @Override
        public RapportSeance findById(int id) {
            String sql = "SELECT * FROM rapport_seance WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche rapport: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<RapportSeance> findAll() {
            List<RapportSeance> rapports = new ArrayList<>();
            String sql = "SELECT * FROM rapport_seance ORDER BY date DESC";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    rapports.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste rapports: " + e.getMessage());
            }
            return rapports;
        }

        @Override
        public void update(RapportSeance rapport) {
            String sql = "UPDATE rapport_seance SET date=?, contenu=?, objectifs=?, observations=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDate(1, Date.valueOf(rapport.getDate()));
                stmt.setString(2, rapport.getContenu());
                stmt.setString(3, rapport.getObjectifs());
                stmt.setString(4, rapport.getObservations());
                stmt.setInt(5, rapport.getId());

                stmt.executeUpdate();
                System.out.println("Rapport de séance mis à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour rapport: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM rapport_seance WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Rapport supprimé.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression rapport: " + e.getMessage());
            }
        }

        public List<RapportSeance> findByEnseignant(int enseignantId) {
            List<RapportSeance> rapports = new ArrayList<>();
            String sql = "SELECT * FROM rapport_seance WHERE enseignant_id = ? ORDER BY date DESC";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, enseignantId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    rapports.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par enseignant: " + e.getMessage());
            }
            return rapports;
        }

        private RapportSeance mapResultSet(ResultSet rs) throws SQLException {
            RapportSeance rapport = new RapportSeance();
            rapport.setId(rs.getInt("id"));
            rapport.setDate(rs.getDate("date").toLocalDate());
            rapport.setContenu(rs.getString("contenu"));
            rapport.setObjectifs(rs.getString("objectifs"));
            rapport.setObservations(rs.getString("observations"));

            // Charger la séance associée
            int seanceId = rs.getInt("seance_id");
            SeanceDAO seanceDAO = new SeanceDAO();
            rapport.setSeance(seanceDAO.findById(seanceId));

            return rapport;
        }
    }


