package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Publication;

public class PublicationDAO {
    public boolean createPublication(Publication publication) {
        String sql = "{CALL CreatePublication(?, ?, ?, ?)}";  // Using stored procedure
    
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
    
            // Set parameters for the stored procedure
            stmt.setString(1, publication.getTitle());
            stmt.setInt(2, publication.getPublicationYear());
            stmt.setString(3, publication.getLanguage());
            stmt.setString(4, publication.getStatus());
    
            // Execute the stored procedure and get the result set
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                publication.setPublicationId(rs.getInt("Publication_ID"));
            }
            return true;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // public boolean createPublication(Publication publication) {
    //     String sql = "INSERT INTO Publication (Title, Publication_Year, Language, Status) VALUES (?, ?, ?, ?)";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

    //         stmt.setString(1, publication.getTitle());
    //         stmt.setInt(2, publication.getPublicationYear());
    //         stmt.setString(3, publication.getLanguage());
    //         stmt.setString(4, publication.getStatus());
    //         stmt.executeUpdate();

    //         ResultSet generatedKeys = stmt.getGeneratedKeys();
    //         if (generatedKeys.next()) {
    //             publication.setPublicationId(generatedKeys.getInt(1));
    //         }
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public Publication getPublicationById(int id) {
        String sql = "SELECT * FROM Publication WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Publication pub = new Publication();
                pub.setPublicationId(rs.getInt("Publication_ID"));
                pub.setTitle(rs.getString("Title"));
                pub.setPublicationYear(rs.getInt("Publication_Year"));
                pub.setLanguage(rs.getString("Language"));
                pub.setStatus(rs.getString("Status"));
                return pub;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePublication(Publication publication) {
        String sql = "UPDATE Publication SET Title=?, Publication_Year=?, Language=?, Status=? WHERE Publication_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, publication.getTitle());
            stmt.setInt(2, publication.getPublicationYear());
            stmt.setString(3, publication.getLanguage());
            stmt.setString(4, publication.getStatus());
            stmt.setInt(5, publication.getPublicationId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePublication(int publicationId) {
        String sql = "DELETE FROM Publication WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Publication> getAllPublications() {
        List<Publication> publications = new ArrayList<>();
        String sql = "SELECT * FROM Publication";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Publication pub = new Publication();
                pub.setPublicationId(rs.getInt("Publication_ID"));
                pub.setTitle(rs.getString("Title"));
                pub.setPublicationYear(rs.getInt("Publication_Year"));
                pub.setLanguage(rs.getString("Language"));
                pub.setStatus(rs.getString("Status"));
                publications.add(pub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publications;
    }
} 
