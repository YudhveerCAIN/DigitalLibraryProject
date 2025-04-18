package service;

import dao.PublicationCategoryDAO;
import model.Category;

import java.util.List;

public class PublicationCategoryService {
    private PublicationCategoryDAO publicationCategoryDAO;

    public PublicationCategoryService() {
        this.publicationCategoryDAO = new PublicationCategoryDAO();
    }

    public boolean addCategoryToPublication(int publicationId, int categoryId) {
        return publicationCategoryDAO.addCategoryToPublication(publicationId, categoryId);
    }

    public boolean removeCategoryFromPublication(int publicationId, int categoryId) {
        return publicationCategoryDAO.removeCategoryFromPublication(publicationId, categoryId);
    }

    public List<Category> getCategoriesByPublicationId(int publicationId) {
        return publicationCategoryDAO.getCategoriesByPublicationId(publicationId);
    }

    public List<Integer> getPublicationsByCategoryId(int categoryId) {
        return publicationCategoryDAO.getPublicationsByCategoryId(categoryId);
    }
}
