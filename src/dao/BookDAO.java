package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookDAO {

    public boolean createBook(Book book) {
        String sql = "INSERT INTO Book (Publication_ID, Publisher, ISBN, Location, Available_Copies, Total_Copies) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getPublicationId());
            stmt.setString(2, book.getPublisher());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getLocation());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setInt(6, book.getTotalCopies());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Book getBookByPublicationId(int publicationId) {
        String sql = "SELECT * FROM Book WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setPublicationId(rs.getInt("Publication_ID"));
                book.setPublisher(rs.getString("Publisher"));
                book.setIsbn(rs.getString("ISBN"));
                book.setLocation(rs.getString("Location"));
                book.setAvailableCopies(rs.getInt("Available_Copies"));
                book.setTotalCopies(rs.getInt("Total_Copies"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE Book SET Publisher=?, ISBN=?, Location=?, Available_Copies=?, Total_Copies=? WHERE Publication_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getPublisher());
            stmt.setString(2, book.getIsbn());
            stmt.setString(3, book.getLocation());
            stmt.setInt(4, book.getAvailableCopies());
            stmt.setInt(5, book.getTotalCopies());
            stmt.setInt(6, book.getPublicationId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int publicationId) {
        String sql = "DELETE FROM Book WHERE Publication_ID = ?";
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

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book();
                book.setPublicationId(rs.getInt("Publication_ID"));
                book.setPublisher(rs.getString("Publisher"));
                book.setIsbn(rs.getString("ISBN"));
                book.setLocation(rs.getString("Location"));
                book.setAvailableCopies(rs.getInt("Available_Copies"));
                book.setTotalCopies(rs.getInt("Total_Copies"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public int getAvailableCopiesUsingFunction(Book book) {
        String sql = "SELECT GetAvailableCopies(?) AS available";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, book.getPublicationId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("available");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
} 
