package service;

import dao.UserDAO;
import java.util.List;
import model.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            System.out.println("Email and password are required");
            return false;
        }
        return userDAO.createUser(user);
    }

    public User loginUser(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public boolean updateLastLogin(int userId) {
        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setLastLogin(new java.util.Date());
            return userDAO.updateUser(user);
        }
        return false;
    }

    public User authenticateUser(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean testDatabaseConnection() {
        return userDAO.testConnection();
    }

    public boolean resetDatabase() {
        return userDAO.resetDatabase();
    }

    public boolean optimizeDatabase() {
        return userDAO.optimizeDatabase();
    }

    public List<String> getAllLogs() {
        return userDAO.getAllLogs();
    }

    public boolean clearLogs() {
        return userDAO.clearLogs();
    }

    public boolean createBackup() {
        return userDAO.createBackup();
    }

    public boolean restoreFromBackup() {
        return userDAO.restoreFromBackup();
    }
} 
