package service;

import dao.AuthorDAO;
import model.Author;

import java.util.List;

public class AuthorService {
    private AuthorDAO authorDAO;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    public boolean createAuthor(Author author) {
        return authorDAO.createAuthor(author);
    }

    public Author getAuthorById(int authorId) {
        return authorDAO.getAuthorById(authorId);
    }

    public boolean updateAuthor(Author author) {
        return authorDAO.updateAuthor(author);
    }

    public boolean deleteAuthor(int authorId) {
        return authorDAO.deleteAuthor(authorId);
    }

    public List<Author> getAllAuthors() {
        return authorDAO.getAllAuthors();
    }
}
