package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

public class ReservationDAO {
    public boolean createReservation(Reservation reservation) {
        String sql = "{CALL CreateReservation(?, ?, ?, ?)}"; // Call the stored procedure
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
    
            // Extract values from the Reservation object
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getPublicationId());
            stmt.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.setDate(4, new java.sql.Date(reservation.getExpiryDate().getTime()));
    
            stmt.executeUpdate(); // Execute the stored procedure
    
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // public boolean createReservation(Reservation reservation) {
    //     String sql = "INSERT INTO Reservation (User_ID, Publication_ID, Reservation_Date, Expiry_Date, Status) VALUES (?, ?, ?, ?, ?)";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

    //         stmt.setInt(1, reservation.getUserId());
    //         stmt.setInt(2, reservation.getPublicationId());
    //         stmt.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
    //         stmt.setDate(4, new java.sql.Date(reservation.getExpiryDate().getTime()));
    //         stmt.setString(5, reservation.getStatus());
    //         stmt.executeUpdate();

    //         ResultSet keys = stmt.getGeneratedKeys();
    //         if (keys.next()) {
    //             reservation.setReservationId(keys.getInt(1));
    //         }
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public Reservation getReservationById(int reservationId) {
        String sql = "SELECT * FROM Reservation WHERE Reservation_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationId(rs.getInt("Reservation_ID"));
                res.setUserId(rs.getInt("User_ID"));
                res.setPublicationId(rs.getInt("Publication_ID"));
                res.setReservationDate(rs.getDate("Reservation_Date"));
                res.setExpiryDate(rs.getDate("Expiry_Date"));
                res.setStatus(rs.getString("Status"));
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE Reservation SET User_ID = ?, Publication_ID = ?, Reservation_Date = ?, Expiry_Date = ?, Status = ? WHERE Reservation_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getPublicationId());
            stmt.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
            stmt.setDate(4, new java.sql.Date(reservation.getExpiryDate().getTime()));
            stmt.setString(5, reservation.getStatus());
            stmt.setInt(6, reservation.getReservationId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReservation(int reservationId) {
        String sql = "DELETE FROM Reservation WHERE Reservation_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservation WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationId(rs.getInt("Reservation_ID"));
                res.setUserId(rs.getInt("User_ID"));
                res.setPublicationId(rs.getInt("Publication_ID"));
                res.setReservationDate(rs.getDate("Reservation_Date"));
                res.setExpiryDate(rs.getDate("Expiry_Date"));
                res.setStatus(rs.getString("Status"));
                list.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
