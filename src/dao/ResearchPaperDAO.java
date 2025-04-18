package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ResearchPaper;

public class ResearchPaperDAO {
    public boolean createResearchPaper(ResearchPaper paper) {
        String sql = "INSERT INTO Research_Paper (Publication_ID, Journal_Name, Conference_Name, DOI, Keywords, Abstract, File, Citations_Count) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, paper.getPublicationId());
            stmt.setString(2, paper.getJournalName());
            stmt.setString(3, paper.getConferenceName());
            stmt.setString(4, paper.getDoi());
            stmt.setString(5, paper.getKeywords());
            stmt.setString(6, paper.getAbstractText());
            stmt.setString(7, paper.getFile());
            stmt.setInt(8, paper.getCitationsCount());
    
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // public boolean createResearchPaper(ResearchPaper paper) {
    //     String sql = "INSERT INTO Research_Paper (Publication_ID, Journal_Name, Conference_Name, DOI, Keywords, Abstract, File, Citations_Count) " +
    //                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql)) {

    //         stmt.setInt(1, paper.getPublicationId());
    //         stmt.setString(2, paper.getJournalName());
    //         stmt.setString(3, paper.getConferenceName());
    //         stmt.setString(4, paper.getDoi());
    //         stmt.setString(5, paper.getKeywords());
    //         stmt.setString(6, paper.getAbstractText());
    //         stmt.setString(7, paper.getFile());
    //         stmt.setInt(8, paper.getCitationsCount());

    //         stmt.executeUpdate();
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public ResearchPaper getResearchPaperByPublicationId(int publicationId) {
        String sql = "SELECT * FROM Research_Paper WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToResearchPaper(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateResearchPaper(ResearchPaper paper) {
        String sql = "UPDATE Research_Paper SET Journal_Name = ?, Conference_Name = ?, DOI = ?, Keywords = ?, Abstract = ?, File = ?, Citations_Count = ? " +
                     "WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paper.getJournalName());
            stmt.setString(2, paper.getConferenceName());
            stmt.setString(3, paper.getDoi());
            stmt.setString(4, paper.getKeywords());
            stmt.setString(5, paper.getAbstractText());
            stmt.setString(6, paper.getFile());
            stmt.setInt(7, paper.getCitationsCount());
            stmt.setInt(8, paper.getPublicationId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteResearchPaper(int publicationId) {
        String sql = "DELETE FROM Research_Paper WHERE Publication_ID = ?";
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

    public List<ResearchPaper> getAllResearchPapers() {
        List<ResearchPaper> papers = new ArrayList<>();
        String sql = "SELECT * FROM Research_Paper";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                papers.add(mapResultSetToResearchPaper(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return papers;
    }

    private ResearchPaper mapResultSetToResearchPaper(ResultSet rs) throws SQLException {
        ResearchPaper paper = new ResearchPaper();
        paper.setPublicationId(rs.getInt("Publication_ID"));
        paper.setJournalName(rs.getString("Journal_Name"));
        paper.setConferenceName(rs.getString("Conference_Name"));
        paper.setDoi(rs.getString("DOI"));
        paper.setKeywords(rs.getString("Keywords"));
        paper.setAbstractText(rs.getString("Abstract"));
        paper.setFile(rs.getString("File"));
        paper.setCitationsCount(rs.getInt("Citations_Count"));
        return paper;
    }
}
