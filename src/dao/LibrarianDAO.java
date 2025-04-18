package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Librarian;

public class LibrarianDAO {

    public boolean createLibrarian(Librarian librarian) {
        String sql = "INSERT INTO Librarian (Staff_ID, User_ID, Work_Shift) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, librarian.getStaffId());
            stmt.setInt(2, librarian.getUserId());
            stmt.setString(3, librarian.getWorkShift());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Librarian getLibrarianById(int staffId) {
        String sql = "SELECT * FROM Librarian WHERE Staff_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLibrarian(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateLibrarian(Librarian librarian) {
        String sql = "UPDATE Librarian SET User_ID = ?, Work_Shift = ? WHERE Staff_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, librarian.getUserId());
            stmt.setString(2, librarian.getWorkShift());
            stmt.setInt(3, librarian.getStaffId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLibrarian(int staffId) {
        String sql = "DELETE FROM Librarian WHERE Staff_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Librarian> getAllLibrarians() {
        List<Librarian> librarians = new ArrayList<>();
        String sql = "SELECT * FROM Librarian";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                librarians.add(mapResultSetToLibrarian(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarians;
    }

    private Librarian mapResultSetToLibrarian(ResultSet rs) throws SQLException {
        Librarian librarian = new Librarian();
        librarian.setStaffId(rs.getInt("Staff_ID"));
        librarian.setUserId(rs.getInt("User_ID"));
        librarian.setWorkShift(rs.getString("Work_Shift"));
        return librarian;
    }
}
