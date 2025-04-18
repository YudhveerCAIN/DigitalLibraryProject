package model;

import java.util.Date;

public class Citation {
    private int citationId;         // Citation_ID
    private int userId;             // User_ID
    private int publicationId;      // Publication_ID
    private Date citationDate;      // Citation_Date

    // Constructors
    public Citation() {}

    public Citation(int citationId, int userId, int publicationId, Date citationDate) {
        this.citationId = citationId;
        this.userId = userId;
        this.publicationId = publicationId;
        this.citationDate = citationDate;
    }

    // Getters and Setters
    public int getCitationId() {
        return citationId;
    }

    public void setCitationId(int citationId) {
        this.citationId = citationId;
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

    public Date getCitationDate() {
        return citationDate;
    }

    public void setCitationDate(Date citationDate) {
        this.citationDate = citationDate;
    }

    // toString
    @Override
    public String toString() {
        return "Citation{" +
                "citationId=" + citationId +
                ", userId=" + userId +
                ", publicationId=" + publicationId +
                ", citationDate=" + citationDate +
                '}';
    }
}
