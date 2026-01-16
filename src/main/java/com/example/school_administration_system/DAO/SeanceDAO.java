package com.example.school_administration_system.DAO;
import com.example.school_administration_system.model.Seance;
import com.example.school_administration_system.model.Matiere;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class SeanceDAO implements GenericDAO<Seance> {

        @Override
        public void create(Seance seance) {
            String sql = "INSERT INTO seance (jour, heure_debut, heure_fin, salle, matiere_id, emploi_du_temps_id, enseignant_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, seance.getJour());
                stmt.setTime(2, Time.valueOf(seance.getHeureDebut()));
                stmt.setTime(3, Time.valueOf(seance.getHeureFin()));
                stmt.setString(4, seance.getSalle());
                stmt.setInt(5, seance.getMatiere().getId());
                stmt.setInt(6, 0); // emploi_du_temps_id - a adapter
                stmt.setInt(7, 0); // enseignant_id - a adapter

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    seance.setId(rs.getInt(1));
                }

                System.out.println("Seance creee avec succes.");
            } catch (SQLException e) {
                System.err.println("Erreur creation seance: " + e.getMessage());
            }
        }

        @Override
        public Seance findById(int id) {
            String sql = "SELECT s.*, m.* FROM seance s " +
                    "JOIN matiere m ON s.matiere_id = m.id " +
                    "WHERE s.id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche seance: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<Seance> findAll() {
            List<Seance> seances = new ArrayList<>();
            String sql = "SELECT s.*, m.* FROM seance s " +
                    "JOIN matiere m ON s.matiere_id = m.id " +
                    "ORDER BY s.jour, s.heure_debut";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    seances.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste seances: " + e.getMessage());
            }
            return seances;
        }

        @Override
        public void update(Seance seance) {
            String sql = "UPDATE seance SET jour=?, heure_debut=?, heure_fin=?, salle=?, matiere_id=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, seance.getJour());
                stmt.setTime(2, Time.valueOf(seance.getHeureDebut()));
                stmt.setTime(3, Time.valueOf(seance.getHeureFin()));
                stmt.setString(4, seance.getSalle());
                stmt.setInt(5, seance.getMatiere().getId());
                stmt.setInt(6, seance.getId());

                stmt.executeUpdate();
                System.out.println("Seance mise a jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour seance: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM seance WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Seance supprimee.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression seance: " + e.getMessage());
            }
        }

        public List<Seance> findByEmploiDuTemps(int emploiDuTempsId) {
            List<Seance> seances = new ArrayList<>();
            String sql = "SELECT s.*, m.* FROM seance s " +
                    "JOIN matiere m ON s.matiere_id = m.id " +
                    "WHERE s.emploi_du_temps_id = ? " +
                    "ORDER BY s.jour, s.heure_debut";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, emploiDuTempsId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    seances.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche seances par emploi: " + e.getMessage());
            }
            return seances;
        }

        public List<Seance> findByEnseignant(int enseignantId) {
            List<Seance> seances = new ArrayList<>();
            String sql = "SELECT s.*, m.* FROM seance s " +
                    "JOIN matiere m ON s.matiere_id = m.id " +
                    "WHERE s.enseignant_id = ? " +
                    "ORDER BY s.jour, s.heure_debut";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, enseignantId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    seances.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche seances par enseignant: " + e.getMessage());
            }
            return seances;
        }

        public List<Seance> findByJour(String jour) {
            List<Seance> seances = new ArrayList<>();
            String sql = "SELECT s.*, m.* FROM seance s " +
                    "JOIN matiere m ON s.matiere_id = m.id " +
                    "WHERE s.jour = ? " +
                    "ORDER BY s.heure_debut";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, jour);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    seances.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche seances par jour: " + e.getMessage());
            }
            return seances;
        }

        private Seance mapResultSet(ResultSet rs) throws SQLException {
            Seance seance = new Seance();
            seance.setId(rs.getInt("s.id"));
            seance.setJour(rs.getString("jour"));
            seance.setHeureDebut(rs.getTime("heure_debut").toLocalTime());
            seance.setHeureFin(rs.getTime("heure_fin").toLocalTime());
            seance.setSalle(rs.getString("salle"));

            // Mapper la matière
            Matiere matiere = new Matiere();
            matiere.setId(rs.getInt("m.id"));
            matiere.setCode(rs.getString("code"));
            matiere.setNom(rs.getString("nom"));
            matiere.setCoefficient(rs.getFloat("coefficient"));
            matiere.setNbHeures(rs.getInt("nb_heures"));

            seance.setMatiere(matiere);

            return seance;
        }
    }

