package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Researcher;

public class ResearcherDAO {

    public boolean createResearcher(Researcher researcher) {
        String sql = "INSERT INTO Researcher (Researcher_ID, User_ID, Institution, Research_Interests, Publications_Count) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, researcher.getResearcherId());
            stmt.setInt(2, researcher.getUserId());
            stmt.setString(3, researcher.getInstitution());
            stmt.setString(4, researcher.getResearchInterests());
            stmt.setInt(5, researcher.getPublicationsCount());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Researcher getResearcherById(int researcherId) {
        String sql = "SELECT * FROM Researcher WHERE Researcher_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, researcherId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToResearcher(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateResearcher(Researcher researcher) {
        String sql = "UPDATE Researcher SET User_ID = ?, Institution = ?, Research_Interests = ?, Publications_Count = ? WHERE Researcher_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, researcher.getUserId());
            stmt.setString(2, researcher.getInstitution());
            stmt.setString(3, researcher.getResearchInterests());
            stmt.setInt(4, researcher.getPublicationsCount());
            stmt.setInt(5, researcher.getResearcherId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteResearcher(int researcherId) {
        String sql = "DELETE FROM Researcher WHERE Researcher_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, researcherId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Researcher> getAllResearchers() {
        List<Researcher> researchers = new ArrayList<>();
        String sql = "SELECT * FROM Researcher";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                researchers.add(mapResultSetToResearcher(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return researchers;
    }

    private Researcher mapResultSetToResearcher(ResultSet rs) throws SQLException {
        Researcher researcher = new Researcher();
        researcher.setResearcherId(rs.getInt("Researcher_ID"));
        researcher.setUserId(rs.getInt("User_ID"));
        researcher.setInstitution(rs.getString("Institution"));
        researcher.setResearchInterests(rs.getString("Research_Interests"));
        researcher.setPublicationsCount(rs.getInt("Publications_Count"));
        return researcher;
    }
}
