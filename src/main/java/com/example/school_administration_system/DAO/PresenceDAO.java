package com.example.school_administration_system.DAO;
import com.example.school_administration_system.model.Presence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
    public class PresenceDAO implements GenericDAO<Presence> {

        @Override
        public void create(Presence presence) {
            String sql = "INSERT INTO presence (date, statut, remarque, etudiant_id, seance_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setDate(1, Date.valueOf(presence.getDate()));
                stmt.setString(2, presence.getStatut());
                stmt.setString(3, presence.getRemarque());
                stmt.setInt(4, presence.getEtudiant().getId());
                stmt.setInt(5, presence.getSeance().getId());

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    presence.setId(rs.getInt(1));
                }

                System.out.println("Présence enregistrée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur enregistrement présence: " + e.getMessage());
            }
        }

        @Override
        public Presence findById(int id) {
            String sql = "SELECT * FROM presence WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche présence: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<Presence> findAll() {
            List<Presence> presences = new ArrayList<>();
            String sql = "SELECT * FROM presence ORDER BY date DESC";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    presences.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste présences: " + e.getMessage());
            }
            return presences;
        }

        @Override
        public void update(Presence presence) {
            String sql = "UPDATE presence SET date=?, statut=?, remarque=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDate(1, Date.valueOf(presence.getDate()));
                stmt.setString(2, presence.getStatut());
                stmt.setString(3, presence.getRemarque());
                stmt.setInt(4, presence.getId());

                stmt.executeUpdate();
                System.out.println("Présence mise à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour présence: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM presence WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Présence supprimée.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression présence: " + e.getMessage());
            }
        }

        public List<Presence> findBySeance(int seanceId) {
            List<Presence> presences = new ArrayList<>();
            String sql = "SELECT * FROM presence WHERE seance_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, seanceId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    presences.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par séance: " + e.getMessage());
            }
            return presences;
        }

        public List<Presence> findByEtudiant(int etudiantId) {
            List<Presence> presences = new ArrayList<>();
            String sql = "SELECT * FROM presence WHERE etudiant_id = ? ORDER BY date DESC";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, etudiantId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    presences.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par étudiant: " + e.getMessage());
            }
            return presences;
        }

        public List<Presence> findByDate(java.time.LocalDate date) {
            List<Presence> presences = new ArrayList<>();
            String sql = "SELECT * FROM presence WHERE date = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDate(1, Date.valueOf(date));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    presences.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par date: " + e.getMessage());
            }
            return presences;
        }

        public double getTauxPresenceEtudiant(int etudiantId) {
            String sql = "SELECT " +
                    "COUNT(*) as total, " +
                    "SUM(CASE WHEN statut = 'Présent' THEN 1 ELSE 0 END) as presents " +
                    "FROM presence WHERE etudiant_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, etudiantId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int total = rs.getInt("total");
                    int presents = rs.getInt("presents");

                    if (total > 0) {
                        return (presents * 100.0) / total;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erreur calcul taux présence: " + e.getMessage());
            }
            return 0.0;
        }

        private Presence mapResultSet(ResultSet rs) throws SQLException {
            Presence presence = new Presence();
            presence.setId(rs.getInt("id"));
            presence.setDate(rs.getDate("date").toLocalDate());
            presence.setStatut(rs.getString("statut"));
            presence.setRemarque(rs.getString("remarque"));

            // Charger l'étudiant et la séance
            int etudiantId = rs.getInt("etudiant_id");
            int seanceId = rs.getInt("seance_id");

            EtudiantDAO etudiantDAO = new EtudiantDAO();
            SeanceDAO seanceDAO = new SeanceDAO();

            presence.setEtudiant(etudiantDAO.findById(etudiantId));
            presence.setSeance(seanceDAO.findById(seanceId));

            return presence;
        }
}
