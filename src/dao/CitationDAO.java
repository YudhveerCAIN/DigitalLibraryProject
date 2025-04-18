package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Citation;

public class CitationDAO {
    public boolean createCitation(Citation citation) {
        String sql = "{CALL CreateCitation(?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
    
            stmt.setInt(1, citation.getUserId());
            stmt.setInt(2, citation.getPublicationId());
            stmt.setDate(3, new java.sql.Date(citation.getCitationDate().getTime()));
            stmt.execute();
    
            try (PreparedStatement idStmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet keys = idStmt.executeQuery()) {
                if (keys.next()) {
                    citation.setCitationId(keys.getInt(1));
                }
            }
    
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Function calling
    public int getCitationCountUsingFunction(Citation citation) {
        String sql = "SELECT GetCitationCount(?) AS count";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, citation.getPublicationId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    
    // public boolean createCitation(Citation citation) {
    //     String sql = "INSERT INTO Citation (User_ID, Publication_ID, Citation_Date) VALUES (?, ?, ?)";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

    //         stmt.setInt(1, citation.getUserId());
    //         stmt.setInt(2, citation.getPublicationId());
    //         stmt.setDate(3, new java.sql.Date(citation.getCitationDate().getTime()));
    //         stmt.executeUpdate();

    //         ResultSet keys = stmt.getGeneratedKeys();
    //         if (keys.next()) {
    //             citation.setCitationId(keys.getInt(1));
    //         }

    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public Citation getCitationById(int citationId) {
        String sql = "SELECT * FROM Citation WHERE Citation_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, citationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCitation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Citation> getCitationsByUserId(int userId) {
        List<Citation> citations = new ArrayList<>();
        String sql = "SELECT * FROM Citation WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citations.add(mapResultSetToCitation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citations;
    }

    public boolean deleteCitation(int citationId) {
        String sql = "DELETE FROM Citation WHERE Citation_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, citationId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Citation mapResultSetToCitation(ResultSet rs) throws SQLException {
        Citation citation = new Citation();
        citation.setCitationId(rs.getInt("Citation_ID"));
        citation.setUserId(rs.getInt("User_ID"));
        citation.setPublicationId(rs.getInt("Publication_ID"));
        citation.setCitationDate(rs.getDate("Citation_Date"));
        return citation;
    }
    public List<Citation> getCitationsByCitingPublication(int publicationId) {
        List<Citation> citations = new ArrayList<>();
        String sql = "SELECT * FROM Citation WHERE Publication_ID = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                citations.add(mapResultSetToCitation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citations;
    }
    
    public List<Citation> getAllCitations() {
        List<Citation> citations = new ArrayList<>();
        String sql = "SELECT * FROM Citation";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                citations.add(mapResultSetToCitation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citations;
    }

    public boolean updateCitation(Citation citation) {
        String sql = "UPDATE Citation SET User_ID = ?, Publication_ID = ?, Citation_Date = ? WHERE Citation_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, citation.getUserId());
            stmt.setInt(2, citation.getPublicationId());
            stmt.setDate(3, new java.sql.Date(citation.getCitationDate().getTime()));
            stmt.setInt(4, citation.getCitationId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Citation> getCitationsByDateRange(Date startDate, Date endDate) {
        List<Citation> citations = new ArrayList<>();
        String sql = "SELECT * FROM Citation WHERE Citation_Date BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                citations.add(mapResultSetToCitation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citations;
    }
}
