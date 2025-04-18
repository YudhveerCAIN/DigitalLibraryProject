package service;

import dao.PublicationDAO;
import model.Publication;

import java.util.List;

public class PublicationService {
    private PublicationDAO publicationDAO;

    public PublicationService() {
        publicationDAO = new PublicationDAO();
    }

    public boolean createPublication(Publication publication) {
        return publicationDAO.createPublication(publication);
    }

    public Publication getPublicationById(int publicationId) {
        return publicationDAO.getPublicationById(publicationId);
    }

    public boolean updatePublication(Publication publication) {
        return publicationDAO.updatePublication(publication);
    }

    public boolean deletePublication(int publicationId) {
        return publicationDAO.deletePublication(publicationId);
    }

    public List<Publication> getAllPublications() {
        return publicationDAO.getAllPublications();
    }
}
