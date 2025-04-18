package service;

import dao.ReviewDAO;
import java.util.List;
import model.Review;

public class ReviewService {
    private final ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }

    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }

    public boolean createReview(Review review) {
        return reviewDAO.createReview(review);
    }

    public boolean updateReview(Review review) {
        return reviewDAO.updateReview(review);
    }

    public boolean deleteReview(int reviewId) {
        return reviewDAO.deleteReview(reviewId);
    }

    public Review getReviewById(int reviewId) {
        return reviewDAO.getReviewById(reviewId);
    }

    public List<Review> getReviewsByPublicationId(int publicationId) {
        return reviewDAO.getReviewsByPublicationId(publicationId);
    }
}
