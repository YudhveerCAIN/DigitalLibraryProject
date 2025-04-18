package model;

public class Book {
    private int publicationId;
    private String publisher;
    private String isbn;
    private String location;
    private int availableCopies;
    private int totalCopies;

    // Default constructor
    public Book() {}

    // Parameterized constructor
    public Book(int publicationId, String publisher, String isbn, String location, int availableCopies, int totalCopies) {
        this.publicationId = publicationId;
        this.publisher = publisher;
        this.isbn = isbn;
        this.location = location;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
    }

    // Getters and setters
    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    @Override
    public String toString() {
        return "Book{" +
                "publicationId=" + publicationId +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", location='" + location + '\'' +
                ", availableCopies=" + availableCopies +
                ", totalCopies=" + totalCopies +
                '}';
    }
}
