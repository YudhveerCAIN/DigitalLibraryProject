package model;

public class PublicationCategory {
    private int publicationId;
    private int categoryId;

    public PublicationCategory() {}

    public PublicationCategory(int publicationId, int categoryId) {
        this.publicationId = publicationId;
        this.categoryId = categoryId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "PublicationCategory{" +
                "publicationId=" + publicationId +
                ", categoryId=" + categoryId +
                '}';
    }
}
