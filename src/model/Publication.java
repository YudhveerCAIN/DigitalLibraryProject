package model;

public class Publication {
    private int publicationId;
    private String title;
    private int publicationYear;
    private String language;
    private String status; // Available, Reserved, Checked-out, etc.

    // Default constructor
    public Publication() {}

    // Parameterized constructor
    public Publication(int publicationId, String title, int publicationYear, String language, String status) {
        this.publicationId = publicationId;
        this.title = title;
        this.publicationYear = publicationYear;
        this.language = language;
        this.status = status;
    }

    // Getters and setters

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "publicationId=" + publicationId +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", language='" + language + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
