package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Author;

public class PublicationAuthorDAO {

    public boolean addAuthorToPublication(int publicationId, int authorId) {
        String sql = "INSERT INTO Publication_Author (publication_id, author_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            stmt.setInt(2, authorId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAuthorFromPublication(int publicationId, int authorId) {
        String sql = "DELETE FROM Publication_Author WHERE publication_id = ? AND author_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            stmt.setInt(2, authorId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Author> getAuthorsByPublicationId(int publicationId) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT a.* FROM Author a " +
                     "JOIN Publication_Author pa ON a.author_id = pa.author_id " +
                     "WHERE pa.publication_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setFirstName(rs.getString("first_name"));
                author.setLastName(rs.getString("last_name"));
                author.setEmail(rs.getString("email"));
                author.setAffiliation(rs.getString("affiliation"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    public List<Integer> getPublicationsByAuthorId(int authorId) {
        List<Integer> publicationIds = new ArrayList<>();
        String sql = "SELECT publication_id FROM Publication_Author WHERE author_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                publicationIds.add(rs.getInt("publication_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicationIds;
    }
}
