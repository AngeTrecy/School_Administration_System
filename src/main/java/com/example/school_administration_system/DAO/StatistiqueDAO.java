package com.example.school_administration_system.DAO;
import com.example.school_administration_system.model.Statistique;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class StatistiqueDAO implements GenericDAO<Statistique> {

        @Override
        public void create(Statistique statistique) {
            String sql = "INSERT INTO statistique (type, periode, donnees_json) VALUES (?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, statistique.getType());
                stmt.setDate(2, Date.valueOf(statistique.getPeriode()));
                stmt.setString(3, convertMapToJson(statistique.getDonnees()));

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    statistique.setId(rs.getInt(1));
                }

                System.out.println("Statistique créée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur création statistique: " + e.getMessage());
            }
        }

        @Override
        public Statistique findById(int id) {
            String sql = "SELECT * FROM statistique WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche statistique: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<Statistique> findAll() {
            List<Statistique> statistiques = new ArrayList<>();
            String sql = "SELECT * FROM statistique ORDER BY periode DESC";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    statistiques.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste statistiques: " + e.getMessage());
            }
            return statistiques;
        }

        @Override
        public void update(Statistique statistique) {
            String sql = "UPDATE statistique SET type=?, periode=?, donnees_json=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, statistique.getType());
                stmt.setDate(2, Date.valueOf(statistique.getPeriode()));
                stmt.setString(3, convertMapToJson(statistique.getDonnees()));
                stmt.setInt(4, statistique.getId());

                stmt.executeUpdate();
                System.out.println("Statistique mise à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour statistique: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM statistique WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Statistique supprimée.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression statistique: " + e.getMessage());
            }
        }

        public Statistique getStatistiquesGenerales() {
            Statistique stat = new Statistique("GENERAL", java.time.LocalDate.now());

            try (Connection conn = DatabaseConnection.getConnection()) {
                // Nombre total d'étudiants
                String sqlEtudiants = "SELECT COUNT(*) as total FROM etudiant";
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery(sqlEtudiants);
                if (rs1.next()) {
                    stat.ajouterDonnee("total_etudiants", rs1.getInt("total"));
                }

                // Nombre total de classes
                String sqlClasses = "SELECT COUNT(*) as total FROM classe";
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sqlClasses);
                if (rs2.next()) {
                    stat.ajouterDonnee("total_classes", rs2.getInt("total"));
                }

                // Nombre total d'enseignants
                String sqlEnseignants = "SELECT COUNT(*) as total FROM enseignant";
                Statement stmt3 = conn.createStatement();
                ResultSet rs3 = stmt3.executeQuery(sqlEnseignants);
                if (rs3.next()) {
                    stat.ajouterDonnee("total_enseignants", rs3.getInt("total"));
                }

                // Emplois du temps en attente
                String sqlEmplois = "SELECT COUNT(*) as total FROM emploi_du_temps WHERE valide = false";
                Statement stmt4 = conn.createStatement();
                ResultSet rs4 = stmt4.executeQuery(sqlEmplois);
                if (rs4.next()) {
                    stat.ajouterDonnee("emplois_en_attente", rs4.getInt("total"));
                }

            } catch (SQLException e) {
                System.err.println("Erreur génération statistiques: " + e.getMessage());
            }

            return stat;
        }

        public Map<String, Integer> getStatistiquesParClasse() {
            Map<String, Integer> stats = new HashMap<>();
            String sql = "SELECT c.nom, COUNT(ce.etudiant_id) as nb_etudiants " +
                    "FROM classe c " +
                    "LEFT JOIN classe_etudiant ce ON c.id = ce.classe_id " +
                    "GROUP BY c.id, c.nom " +
                    "ORDER BY c.nom";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    stats.put(rs.getString("nom"), rs.getInt("nb_etudiants"));
                }
            } catch (SQLException e) {
                System.err.println("Erreur statistiques par classe: " + e.getMessage());
            }

            return stats;
        }

        public Map<String, Double> getTauxPresenceParClasse(int classeId) {
            Map<String, Double> stats = new HashMap<>();
            String sql = "SELECT " +
                    "COUNT(*) as total, " +
                    "SUM(CASE WHEN p.statut = 'Présent' THEN 1 ELSE 0 END) as presents " +
                    "FROM presence p " +
                    "JOIN etudiant e ON p.etudiant_id = e.id " +
                    "JOIN classe_etudiant ce ON e.id = ce.etudiant_id " +
                    "WHERE ce.classe_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, classeId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int total = rs.getInt("total");
                    int presents = rs.getInt("presents");

                    if (total > 0) {
                        double taux = (presents * 100.0) / total;
                        stats.put("taux_presence", taux);
                        stats.put("total_seances", (double) total);
                        stats.put("presences", (double) presents);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erreur taux présence: " + e.getMessage());
            }

            return stats;
        }

        private String convertMapToJson(Map<String, Object> map) {
            // Conversion simple - pour une version production, utiliser une bibliothèque JSON
            StringBuilder json = new StringBuilder("{");
            int count = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (count > 0) json.append(",");
                json.append("\"").append(entry.getKey()).append("\":\"")
                        .append(entry.getValue()).append("\"");
                count++;
            }
            json.append("}");
            return json.toString();
        }

        private Map<String, Object> convertJsonToMap(String json) {
            // Conversion simple - pour une version production, utiliser une bibliothèque JSON
            Map<String, Object> map = new HashMap<>();
            if (json != null && !json.isEmpty()) {
                json = json.replace("{", "").replace("}", "");
                String[] pairs = json.split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].replace("\"", "").trim();
                        String value = keyValue[1].replace("\"", "").trim();
                        map.put(key, value);
                    }
                }
            }
            return map;
        }

        private Statistique mapResultSet(ResultSet rs) throws SQLException {
            Statistique stat = new Statistique();
            stat.setId(rs.getInt("id"));
            stat.setType(rs.getString("type"));
            stat.setPeriode(rs.getDate("periode").toLocalDate());

            String donneesJson = rs.getString("donnees_json");
            stat.setDonnees(convertJsonToMap(donneesJson));

            return stat;
        }
    }

