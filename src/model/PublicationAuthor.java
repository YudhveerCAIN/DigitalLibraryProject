package model;

public class PublicationAuthor {
    private int publicationId;
    private int authorId;

    // Default constructor
    public PublicationAuthor() {}

    // Parameterized constructor
    public PublicationAuthor(int publicationId, int authorId) {
        this.publicationId = publicationId;
        this.authorId = authorId;
    }

    // Getters and setters
    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "PublicationAuthor{" +
                "publicationId=" + publicationId +
                ", authorId=" + authorId +
                '}';
    }
} 
