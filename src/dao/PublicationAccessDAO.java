package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PublicationAccess;

public class PublicationAccessDAO {

    public boolean createAccess(PublicationAccess access) {
        String sql = "INSERT INTO Publication_Access (User_ID, Publication_ID, Access_Date, Access_Type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, access.getUserId());
            stmt.setInt(2, access.getPublicationId());
            stmt.setDate(3, new java.sql.Date(access.getAccessDate().getTime()));
            stmt.setString(4, access.getAccessType());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                access.setAccessId(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PublicationAccess> getAccessByUserId(int userId) {
        List<PublicationAccess> list = new ArrayList<>();
        String sql = "SELECT * FROM Publication_Access WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PublicationAccess access = new PublicationAccess();
                access.setAccessId(rs.getInt("Access_ID"));
                access.setUserId(rs.getInt("User_ID"));
                access.setPublicationId(rs.getInt("Publication_ID"));
                access.setAccessDate(rs.getDate("Access_Date"));
                access.setAccessType(rs.getString("Access_Type"));
                list.add(access);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<PublicationAccess> getAccessByPublicationId(int publicationId) {
        List<PublicationAccess> list = new ArrayList<>();
        String sql = "SELECT * FROM Publication_Access WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PublicationAccess access = new PublicationAccess();
                access.setAccessId(rs.getInt("Access_ID"));
                access.setUserId(rs.getInt("User_ID"));
                access.setPublicationId(rs.getInt("Publication_ID"));
                access.setAccessDate(rs.getDate("Access_Date"));
                access.setAccessType(rs.getString("Access_Type"));
                list.add(access);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public int getTotalPublicationsAccessedUsingFunction(int userId) {
        String sql = "SELECT GetTotalPublicationsAccessed(?) AS total";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
}
