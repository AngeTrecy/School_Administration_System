package com.example.school_administration_system.DAO;

import com.example.school_administration_system.model.Classe;
import com.example.school_administration_system.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClasseDAO {


    public void addClasse(Classe classe) {
        // La requête SQL avec des '?' pour éviter les failles de sécurité (injections
        // SQL).
        String sql = "INSERT INTO classes (nom, niveau, capacite, annee_scolaire) VALUES (?, ?, ?, ?)";

        // On demande une connexion via notre classe DatabaseConnection.
        // Le bloc try-with-resources garantit que le PreparedStatement sera fermé après
        // usage.
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // On remplace les '?' par les vraies valeurs de la classe
            pstmt.setString(1, classe.getNom());
            pstmt.setString(2, classe.getNiveau());
            pstmt.setInt(3, classe.getCapaciteMax()); // capaciteMax dans le modèle, capacite en base
            pstmt.setString(4, classe.getAnneeAcademique()); // anneeAcademique dans le modèle

            // On exécute la requête d'insertion
            pstmt.executeUpdate();

            // On récupère l'ID généré automatiquement par PostgreSQL
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // On met à jour l'ID de notre objet Java avec l'ID de la base de données
                    classe.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la classe : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Récupère la liste de toutes les classes enregistrées dans la base de données.
     * 
     * @return Une liste d'objets Classe.
     */
    public List<Classe> getAllClasses() {
        List<Classe> classes = new ArrayList<>();
        String sql = "SELECT * FROM classes";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // On parcourt chaque ligne retournée par la base de données
            while (rs.next()) {
                Classe c = new Classe();
                // On transforme les données de la table en un objet Classe
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setNiveau(rs.getString("niveau"));
                c.setCapaciteMax(rs.getInt("capacite"));
                c.setAnneeAcademique(rs.getString("annee_scolaire"));

                classes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des classes : " + e.getMessage());
        }
        return classes;
    }

    /**
     * Récupère une classe spécifique via son identifiant (ID).
     * 
     * @param id L'identifiant de la classe.
     * @return L'objet Classe trouvé, ou null si non trouvé.
     */
    public Classe getClasseById(int id) {
        String sql = "SELECT * FROM classes WHERE id = ?";
        Classe classe = null;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    classe = new Classe();
                    classe.setId(rs.getInt("id"));
                    classe.setNom(rs.getString("nom"));
                    classe.setNiveau(rs.getString("niveau"));
                    classe.setCapaciteMax(rs.getInt("capacite"));
                    classe.setAnneeAcademique(rs.getString("annee_scolaire"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la classe : " + e.getMessage());
        }
        return classe;
    }

    /**
     * Met à jour les informations d'une classe existante.
     * 
     * @param classe L'objet Classe contenant les modifications.
     */
    public void updateClasse(Classe classe) {
        String sql = "UPDATE classes SET nom = ?, niveau = ?, capacite = ?, annee_scolaire = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, classe.getNom());
            pstmt.setString(2, classe.getNiveau());
            pstmt.setInt(3, classe.getCapaciteMax());
            pstmt.setString(4, classe.getAnneeAcademique());
            pstmt.setInt(5, classe.getId()); // La condition WHERE porte sur l'ID

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la classe : " + e.getMessage());
        }
    }

    /**
     * Supprime une classe definitivement via son identifiant.
     * 
     * @param id L'identifiant de la classe à supprimer.
     */
    public void deleteClasse(int id) {
        String sql = "DELETE FROM classes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la classe : " + e.getMessage());
        }
    }
}
