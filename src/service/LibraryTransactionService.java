package service;

import dao.LibraryTransactionDAO;
import java.util.List;
import model.LibraryTransaction;

public class LibraryTransactionService {
    private LibraryTransactionDAO transactionDAO;

    public LibraryTransactionService() {
        this.transactionDAO = new LibraryTransactionDAO();
    }

    public boolean createTransaction(LibraryTransaction transaction) {
        return transactionDAO.createTransaction(transaction);
    }

    public LibraryTransaction getTransactionById(int transactionId) {
        return transactionDAO.getTransactionById(transactionId);
    }

    public boolean updateTransaction(LibraryTransaction transaction) {
        return transactionDAO.updateTransaction(transaction);
    }

    public boolean deleteTransaction(int transactionId) {
        return transactionDAO.deleteTransaction(transactionId);
    }

    public List<LibraryTransaction> getTransactionsByUserId(int userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }

    public List<LibraryTransaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public int getTotalBorrowsByUser(LibraryTransaction transaction) {
        return transactionDAO.getTotalBorrowsByUserUsingFunction(transaction);
    }
}
