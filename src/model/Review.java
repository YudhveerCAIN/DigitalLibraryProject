package model;

import java.util.Date;

public class Review {
    private int reviewId;
    private Date reviewDate;
    private int rating;
    private String comments;
    private int userId;
    private int publicationId;

    // Default constructor
    public Review() {}

    // Parameterized constructor
    public Review(int reviewId, Date reviewDate, int rating, String comments, int userId, int publicationId) {
        this.reviewId = reviewId;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.comments = comments;
        this.userId = userId;
        this.publicationId = publicationId;
    }

    // Getters and setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", reviewDate=" + reviewDate +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", userId=" + userId +
                ", publicationId=" + publicationId +
                '}';
    }
} 