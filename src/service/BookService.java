package service;

import dao.BookDAO;
import java.util.List;
import model.Book;

public class BookService {
    private BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public boolean createBook(Book book) {
        return bookDAO.createBook(book);
    }

    public Book getBookByPublicationId(int publicationId) {
        return bookDAO.getBookByPublicationId(publicationId);
    }

    public boolean updateBook(Book book) {
        return bookDAO.updateBook(book);
    }

    public boolean deleteBook(int publicationId) {
        return bookDAO.deleteBook(publicationId);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    public int getAvailableCopies(Book book) {
        return bookDAO.getAvailableCopiesUsingFunction(book);
    }
    
    public int getAvailableCopies(int publicationId) {
        Book book = getBookByPublicationId(publicationId);
        if (book != null) {
            return book.getAvailableCopies();
        }
        return 0;
    }

    public boolean updateAvailableCopies(int publicationId, int change) {
        Book book = getBookByPublicationId(publicationId);
        if (book != null) {
            int newAvailable = book.getAvailableCopies() + change;
            if (newAvailable < 0 || newAvailable > book.getTotalCopies()) {
                return false;
            }
            book.setAvailableCopies(newAvailable);
            return updateBook(book);
        }
        return false;
    }
}
