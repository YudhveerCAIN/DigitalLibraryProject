package dao;

import config.DatabaseConnection;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationCategoryDAO {

    public boolean addCategoryToPublication(int publicationId, int categoryId) {
        String sql = "INSERT INTO Publication_Category (publication_id, category_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, publicationId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCategoryFromPublication(int publicationId, int categoryId) {
        String sql = "DELETE FROM Publication_Category WHERE publication_id = ? AND category_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, publicationId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> getCategoriesByPublicationId(int publicationId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.* FROM Category c " +
                     "JOIN Publication_Category pc ON c.category_id = pc.category_id " +
                     "WHERE pc.publication_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Integer> getPublicationsByCategoryId(int categoryId) {
        List<Integer> publicationIds = new ArrayList<>();
        String sql = "SELECT publication_id FROM Publication_Category WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
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
