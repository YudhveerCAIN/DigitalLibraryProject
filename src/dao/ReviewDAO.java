package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Review;

public class ReviewDAO {
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM Review";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("Review_ID"));
                review.setReviewDate(rs.getDate("Review_Date"));
                review.setRating(rs.getInt("Rating"));
                review.setComments(rs.getString("Comments"));
                review.setUserId(rs.getInt("User_ID"));
                review.setPublicationId(rs.getInt("Publication_ID"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean createReview(Review review) {
        String query = "INSERT INTO Review (Review_Date, Rating, Comments, User_ID, Publication_ID) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(review.getReviewDate().getTime()));
            pstmt.setInt(2, review.getRating());
            pstmt.setString(3, review.getComments());
            pstmt.setInt(4, review.getUserId());
            pstmt.setInt(5, review.getPublicationId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReview(Review review) {
        String query = "UPDATE Review SET Review_Date = ?, Rating = ?, Comments = ? WHERE Review_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(review.getReviewDate().getTime()));
            pstmt.setInt(2, review.getRating());
            pstmt.setString(3, review.getComments());
            pstmt.setInt(4, review.getReviewId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReview(int reviewId) {
        String query = "DELETE FROM Review WHERE Review_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reviewId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Review getReviewById(int reviewId) {
        String query = "SELECT * FROM Review WHERE Review_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("Review_ID"));
                review.setReviewDate(rs.getDate("Review_Date"));
                review.setRating(rs.getInt("Rating"));
                review.setComments(rs.getString("Comments"));
                review.setUserId(rs.getInt("User_ID"));
                review.setPublicationId(rs.getInt("Publication_ID"));
                return review;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Review> getReviewsByPublicationId(int publicationId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE Publication_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("Review_ID"));
                review.setReviewDate(rs.getDate("Review_Date"));
                review.setRating(rs.getInt("Rating"));
                review.setComments(rs.getString("Comments"));
                review.setUserId(rs.getInt("User_ID"));
                review.setPublicationId(rs.getInt("Publication_ID"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public double getAverageRatingUsingFunction(int publicationId) {
        String sql = "SELECT GetAverageRating(?) AS average";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("average");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
