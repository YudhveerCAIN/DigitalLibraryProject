package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {
    public boolean createUser(User user) {
        String sql = "{CALL RegisterUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
    
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getMembershipStatus());
            stmt.setDate(6, new java.sql.Date(user.getDateJoined().getTime()));
            stmt.setTimestamp(7, user.getLastLogin() != null ? new Timestamp(user.getLastLogin().getTime()) : null);
            stmt.setString(8, user.getContactNumber());
            stmt.setString(9, user.getAddress());
            stmt.setInt(10, user.getPrivilegeLevel());
    
            stmt.execute();
    
            // Optional: fetch generated user ID manually
            try (PreparedStatement idStmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
    
            return true;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // public boolean createUser(User user) {
    //     String sql = "INSERT INTO User (Name, Email, Password, Role, Membership_Status, Date_Joined, Last_Login, Contact_Number, Address) " +
    //                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

    //         stmt.setString(1, user.getName());
    //         stmt.setString(2, user.getEmail());
    //         stmt.setString(3, user.getPassword());
    //         stmt.setString(4, user.getRole());
    //         stmt.setString(5, user.getMembershipStatus());
    //         stmt.setDate(6, new java.sql.Date(user.getDateJoined().getTime()));
    //         stmt.setTimestamp(7, user.getLastLogin() != null ? new Timestamp(user.getLastLogin().getTime()) : null);
    //         stmt.setString(8, user.getContactNumber());
    //         stmt.setString(9, user.getAddress());

    //         stmt.executeUpdate();

    //         ResultSet keys = stmt.getGeneratedKeys();
    //         if (keys.next()) {
    //             user.setUserId(keys.getInt(1));
    //         }
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM User WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE Email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE User SET Name = ?, Email = ?, Password = ?, Role = ?, Membership_Status = ?, " +
                     "Date_Joined = ?, Last_Login = ?, Contact_Number = ?, Address = ?, Privilege_Level = ? WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getMembershipStatus());
            stmt.setDate(6, new java.sql.Date(user.getDateJoined().getTime()));
            stmt.setTimestamp(7, user.getLastLogin() != null ? new Timestamp(user.getLastLogin().getTime()) : null);
            stmt.setString(8, user.getContactNumber());
            stmt.setString(9, user.getAddress());
            stmt.setInt(10, user.getPrivilegeLevel());
            stmt.setInt(11, user.getUserId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM User WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("User_ID"));
        user.setName(rs.getString("Name"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setMembershipStatus(rs.getString("Membership_Status"));
        user.setDateJoined(rs.getDate("Date_Joined"));
        user.setLastLogin(rs.getTimestamp("Last_Login"));
        user.setContactNumber(rs.getString("Contact_Number"));
        user.setAddress(rs.getString("Address"));
        user.setPrivilegeLevel(rs.getInt("Privilege_Level"));
        return user;
    }

    public boolean testConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return conn.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean resetDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP SCHEMA public CASCADE");
            stmt.execute("CREATE SCHEMA public");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean optimizeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("VACUUM ANALYZE");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> getAllLogs() {
        List<String> logs = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM system_logs ORDER BY timestamp DESC")) {
            while (rs.next()) {
                logs.add(String.format("[%s] %s", 
                    rs.getTimestamp("timestamp").toString(),
                    rs.getString("message")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public boolean clearLogs() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM system_logs");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createBackup() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("BACKUP DATABASE TO 'backup.sql'");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreFromBackup() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("RESTORE DATABASE FROM 'backup.sql'");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}