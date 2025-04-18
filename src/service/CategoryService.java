package service;

import dao.CategoryDAO;
import model.Category;

import java.util.List;

public class CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public boolean createCategory(Category category) {
        return categoryDAO.createCategory(category);
    }

    public Category getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }

    public boolean updateCategory(Category category) {
        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) {
        return categoryDAO.deleteCategory(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
}
