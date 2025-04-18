package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.LibraryTransaction;

public class LibraryTransactionDAO {


        public boolean createTransaction(LibraryTransaction tx) {
            String sql;
            if (tx.getTransactionType().equals("Borrow")) {
                sql = "{CALL BorrowPublication(?, ?, ?, ?)}";
            } else {
                sql = "{CALL ReturnPublication(?, ?, ?, ?)}";
            }
            
            try (Connection conn = DatabaseConnection.getConnection();
                 CallableStatement stmt = conn.prepareCall(sql)) {

                stmt.setInt(1, tx.getUserId());
                stmt.setInt(2, tx.getPublicationId());
                stmt.setDate(3, new java.sql.Date(tx.getTransactionDate().getTime()));
                stmt.setDate(4, new java.sql.Date(tx.getDueDate().getTime()));

                stmt.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    // Function call
    public int getTotalBorrowsByUserUsingFunction(LibraryTransaction tx) {
        String sql = "SELECT GetTotalBorrowsByUser(?) AS total";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, tx.getUserId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // public boolean createTransaction(LibraryTransaction tx) {
    //     String sql = "INSERT INTO Library_Transaction (User_ID, Publication_ID, Transaction_Type, Transaction_Date, Due_Date, Late_Fee) " +
    //                  "VALUES (?, ?, ?, ?, ?, ?)";
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

    //         stmt.setInt(1, tx.getUserId());
    //         stmt.setInt(2, tx.getPublicationId());
    //         stmt.setString(3, tx.getTransactionType());
    //         stmt.setDate(4, new java.sql.Date(tx.getTransactionDate().getTime()));
    //         stmt.setDate(5, new java.sql.Date(tx.getDueDate().getTime()));
    //         stmt.setDouble(6, tx.getLateFee());

    //         stmt.executeUpdate();

    //         ResultSet keys = stmt.getGeneratedKeys();
    //         if (keys.next()) {
    //             tx.setTransactionId(keys.getInt(1));
    //         }

    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public LibraryTransaction getTransactionById(int transactionId) {
        String sql = "SELECT * FROM Library_Transaction WHERE Transaction_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTransaction(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTransaction(LibraryTransaction tx) {
        String sql = "UPDATE Library_Transaction SET User_ID = ?, Publication_ID = ?, Transaction_Type = ?, " +
                     "Transaction_Date = ?, Due_Date = ?, Late_Fee = ? WHERE Transaction_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tx.getUserId());
            stmt.setInt(2, tx.getPublicationId());
            stmt.setString(3, tx.getTransactionType());
            stmt.setDate(4, new java.sql.Date(tx.getTransactionDate().getTime()));
            stmt.setDate(5, new java.sql.Date(tx.getDueDate().getTime()));
            stmt.setDouble(6, tx.getLateFee());
            stmt.setInt(7, tx.getTransactionId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTransaction(int transactionId) {
        String sql = "DELETE FROM Library_Transaction WHERE Transaction_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LibraryTransaction> getTransactionsByUserId(int userId) {
        List<LibraryTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Library_Transaction WHERE User_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<LibraryTransaction> getAllTransactions() {
        List<LibraryTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Library_Transaction";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private LibraryTransaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        LibraryTransaction tx = new LibraryTransaction();
        tx.setTransactionId(rs.getInt("Transaction_ID"));
        tx.setUserId(rs.getInt("User_ID"));
        tx.setPublicationId(rs.getInt("Publication_ID"));
        tx.setTransactionType(rs.getString("Transaction_Type"));
        tx.setTransactionDate(rs.getDate("Transaction_Date"));
        tx.setDueDate(rs.getDate("Due_Date"));
        tx.setLateFee(rs.getDouble("Late_Fee"));
        return tx;
    }
}
