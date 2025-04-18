package model;

import java.util.Date;

public class PublicationAccess {
    private int accessId;
    private int userId;
    private int publicationId;
    private Date accessDate;
    private String accessType; // View, Download

    public PublicationAccess() {}

    public PublicationAccess(int accessId, int userId, int publicationId, Date accessDate, String accessType) {
        this.accessId = accessId;
        this.userId = userId;
        this.publicationId = publicationId;
        this.accessDate = accessDate;
        this.accessType = accessType;
    }

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
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

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "PublicationAccess{" +
                "accessId=" + accessId +
                ", userId=" + userId +
                ", publicationId=" + publicationId +
                ", accessDate=" + accessDate +
                ", accessType='" + accessType + '\'' +
                '}';
    }
}
