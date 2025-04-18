package service;

import dao.LibrarianDAO;
import model.Librarian;

import java.util.List;

public class LibrarianService {
    private LibrarianDAO librarianDAO;

    public LibrarianService() {
        this.librarianDAO = new LibrarianDAO();
    }

    public boolean createLibrarian(Librarian librarian) {
        return librarianDAO.createLibrarian(librarian);
    }

    public Librarian getLibrarianById(int librarianId) {
        return librarianDAO.getLibrarianById(librarianId);
    }

    public boolean updateLibrarian(Librarian librarian) {
        return librarianDAO.updateLibrarian(librarian);
    }

    public boolean deleteLibrarian(int librarianId) {
        return librarianDAO.deleteLibrarian(librarianId);
    }

    public List<Librarian> getAllLibrarians() {
        return librarianDAO.getAllLibrarians();
    }
}
