package dao;

import config.DatabaseConnection;
import model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public boolean createAuthor(Author author) {
        String sql = "INSERT INTO Author (First_Name, Last_Name, Email, Affiliation) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getEmail());
            stmt.setString(4, author.getAffiliation());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                author.setAuthorId(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Author getAuthorById(int authorId) {
        String sql = "SELECT * FROM Author WHERE Author_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("Author_ID"));
                author.setFirstName(rs.getString("First_Name"));
                author.setLastName(rs.getString("Last_Name"));
                author.setEmail(rs.getString("Email"));
                author.setAffiliation(rs.getString("Affiliation"));
                return author;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAuthor(Author author) {
        String sql = "UPDATE Author SET First_Name = ?, Last_Name = ?, Email = ?, Affiliation = ? WHERE Author_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getEmail());
            stmt.setString(4, author.getAffiliation());
            stmt.setInt(5, author.getAuthorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAuthor(int authorId) {
        String sql = "DELETE FROM Author WHERE Author_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, authorId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Author";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("Author_ID"));
                author.setFirstName(rs.getString("First_Name"));
                author.setLastName(rs.getString("Last_Name"));
                author.setEmail(rs.getString("Email"));
                author.setAffiliation(rs.getString("Affiliation"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
} 
