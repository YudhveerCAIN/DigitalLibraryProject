package service;

import dao.PublicationAuthorDAO;
import model.Author;

import java.util.List;

public class PublicationAuthorService {
    private PublicationAuthorDAO publicationAuthorDAO;

    public PublicationAuthorService() {
        this.publicationAuthorDAO = new PublicationAuthorDAO();
    }

    public boolean addAuthorToPublication(int publicationId, int authorId) {
        return publicationAuthorDAO.addAuthorToPublication(publicationId, authorId);
    }

    public boolean removeAuthorFromPublication(int publicationId, int authorId) {
        return publicationAuthorDAO.removeAuthorFromPublication(publicationId, authorId);
    }

    public List<Author> getAuthorsByPublicationId(int publicationId) {
        return publicationAuthorDAO.getAuthorsByPublicationId(publicationId);
    }

    public List<Integer> getPublicationsByAuthorId(int authorId) {
        return publicationAuthorDAO.getPublicationsByAuthorId(authorId);
    }
}
