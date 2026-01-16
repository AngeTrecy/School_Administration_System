package com.example.school_administration_system.DAO;
import com.example.school_administration_system.model.Matiere;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class MatiereDAO implements GenericDAO<Matiere> {

        @Override
        public void create(Matiere matiere) {
            String sql = "INSERT INTO matiere (code, nom, coefficient, nb_heures) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, matiere.getCode());
                stmt.setString(2, matiere.getNom());
                stmt.setFloat(3, matiere.getCoefficient());
                stmt.setInt(4, matiere.getNbHeures());

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    matiere.setId(rs.getInt(1));
                }

                System.out.println("Matière créée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur création matière: " + e.getMessage());
            }
        }

        @Override
        public Matiere findById(int id) {
            String sql = "SELECT * FROM matiere WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche matière: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<Matiere> findAll() {
            List<Matiere> matieres = new ArrayList<>();
            String sql = "SELECT * FROM matiere ORDER BY nom";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    matieres.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur liste matières: " + e.getMessage());
            }
            return matieres;
        }

        @Override
        public void update(Matiere matiere) {
            String sql = "UPDATE matiere SET code=?, nom=?, coefficient=?, nb_heures=? WHERE id=?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, matiere.getCode());
                stmt.setString(2, matiere.getNom());
                stmt.setFloat(3, matiere.getCoefficient());
                stmt.setInt(4, matiere.getNbHeures());
                stmt.setInt(5, matiere.getId());

                stmt.executeUpdate();
                System.out.println("Matière mise à jour.");
            } catch (SQLException e) {
                System.err.println("Erreur mise à jour matière: " + e.getMessage());
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM matiere WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Matière supprimée.");
            } catch (SQLException e) {
                System.err.println("Erreur suppression matière: " + e.getMessage());
            }
        }

        public Matiere findByCode(String code) {
            String sql = "SELECT * FROM matiere WHERE code = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, code);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par code: " + e.getMessage());
            }
            return null;
        }

        public List<Matiere> findByNiveau(String niveau) {
            List<Matiere> matieres = new ArrayList<>();
            String sql = "SELECT * FROM matiere WHERE niveau = ? ORDER BY nom";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, niveau);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    matieres.add(mapResultSet(rs));
                }
            } catch (SQLException e) {
                System.err.println("Erreur recherche par niveau: " + e.getMessage());
            }
            return matieres;
        }

        private Matiere mapResultSet(ResultSet rs) throws SQLException {
            Matiere matiere = new Matiere();
            matiere.setId(rs.getInt("id"));
            matiere.setCode(rs.getString("code"));
            matiere.setNom(rs.getString("nom"));
            matiere.setCoefficient(rs.getFloat("coefficient"));
            matiere.setNbHeures(rs.getInt("nb_heures"));
            return matiere;
        }
    }

