package service;

import dao.PublicationAccessDAO;
import model.PublicationAccess;

import java.util.List;

public class PublicationAccessService {
    private PublicationAccessDAO accessDAO;

    public PublicationAccessService() {
        this.accessDAO = new PublicationAccessDAO();
    }

    public boolean createAccess(PublicationAccess access) {
        return accessDAO.createAccess(access);
    }

    public List<PublicationAccess> getAccessByUserId(int userId) {
        return accessDAO.getAccessByUserId(userId);
    }

    public List<PublicationAccess> getAccessByPublicationId(int publicationId) {
        return accessDAO.getAccessByPublicationId(publicationId);
    }
}
