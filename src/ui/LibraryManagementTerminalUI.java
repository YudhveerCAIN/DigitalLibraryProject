package ui;
import config.DatabaseConnection;
import java.sql.Connection;
import java.util.*;
import model.*;
import util.TableFormatter;

public class LibraryManagementTerminalUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static Connection connection;
    
    // Services
    private static service.UserService userService;
    private static service.BookService bookService;
    private static service.CategoryService categoryService;
    private static service.AuthorService authorService;
    private static service.LibraryTransactionService transactionService;
    private static service.ReservationService reservationService;
    private static service.ReviewService reviewService;
    private static service.ResearchPaperService researchPaperService;
    private static service.PublicationAccessService publicationAccessService;
    private static service.CitationService citationService;
    private static service.LibrarianService librarianService;
    private static service.ResearcherService researcherService;
    private static service.PublicationService publicationService;
    private static service.PublicationAuthorService pubAuthorService;
    private static service.PublicationCategoryService pubCategoryService;

    static {
        initializeServices();
    }

    private static void initializeServices() {
        try {
            connection = DatabaseConnection.getConnection();
            if (connection != null) {
                userService = new service.UserService();
                bookService = new service.BookService();
                categoryService = new service.CategoryService();
                authorService = new service.AuthorService();
                transactionService = new service.LibraryTransactionService();
                reservationService = new service.ReservationService();
                reviewService = new service.ReviewService();
                researchPaperService = new service.ResearchPaperService();
                publicationAccessService = new service.PublicationAccessService();
                citationService = new service.CitationService();
                librarianService = new service.LibrarianService();
                researcherService = new service.ResearcherService();
                publicationService = new service.PublicationService();
                pubAuthorService = new service.PublicationAuthorService();
                pubCategoryService = new service.PublicationCategoryService();
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize services: " + e.getMessage());
        }
    }

    public LibraryManagementTerminalUI() {
        initializeServices();
    }

    public static void main(String[] args) {
        loginMenu();
    }

    private static void loginMenu() {
        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM LOGIN ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    
                    currentUser = userService.authenticateUser(email, password);
                    if (currentUser != null) {
                        System.out.println("Login successful!");
                        userService.updateLastLogin(currentUser.getUserId());
                        showRoleBasedMenu();
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                }
                case 2 -> {
                    User newUser = new User();
                    System.out.print("Name: "); newUser.setName(scanner.nextLine());
                    System.out.print("Email: "); newUser.setEmail(scanner.nextLine());
                    System.out.print("Password: "); newUser.setPassword(scanner.nextLine());
                    System.out.print("Role (Student/Researcher/Librarian/Admin): "); 
                    String role = scanner.nextLine();
                    newUser.setRole(role);
                    
                    // Set privilege level based on role
                    switch (role.toLowerCase()) {
                        case "admin" -> newUser.setPrivilegeLevel(2);
                        case "librarian" -> newUser.setPrivilegeLevel(1);
                        default -> newUser.setPrivilegeLevel(0);
                    }
                    
                    newUser.setMembershipStatus("Active");
                    newUser.setDateJoined(new java.util.Date());
                    newUser.setLastLogin(new java.util.Date());
                    System.out.print("Contact Number: "); newUser.setContactNumber(scanner.nextLine());
                    System.out.print("Address: "); newUser.setAddress(scanner.nextLine());
                    
                    if (userService.registerUser(newUser)) {
                        System.out.println("Registration successful! Please login with your credentials.");
                    } else {
                        System.out.println("Registration failed. Please try again.");
                    }
                }
                case 0 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showRoleBasedMenu() {
        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("Logged in as: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
            
            if (currentUser.getPrivilegeLevel() == 2) { // Admin
                showAdminMenu();
            } else if (currentUser.getPrivilegeLevel() == 1) { // Librarian
                showLibrarianMenu();
            } else { // Regular User
                showUserMenu();
            }
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. User Management");
        System.out.println("2. Librarian Management");
        System.out.println("3. View All Transactions");
        System.out.println("4. View All Access Logs");
        System.out.println("0. Logout");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> userMenu();
            case 2 -> librarianMenu();
            case 3 -> viewAllTransactions();
            case 4 -> viewAllAccessLogs();
            case 0 -> {
                currentUser = null;
                loginMenu();
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void showLibrarianMenu() {
        System.out.println("\n=== LIBRARIAN MENU ===");
        System.out.println("1. Book Management");
        System.out.println("2. Publication Management");
        System.out.println("3. Transaction Management");
        System.out.println("4. View My Profile");
        System.out.println("5. Update My Profile");
        System.out.println("0. Logout");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> bookMenu();
            case 2 -> publicationMenu();
            case 3 -> transactionMenu();
            case 4 -> viewMyProfile();
            case 5 -> updateMyProfile();
            case 0 -> {
                currentUser = null;
                loginMenu();
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n=== USER MENU ===");
        System.out.println("1. View Available Books");
        System.out.println("2. View My Transactions");
        System.out.println("3. View My Profile");
        System.out.println("4. Update My Profile");
        System.out.println("5. Make a Transaction");
        System.out.println("0. Logout");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> viewAvailableBooks();
            case 2 -> viewMyTransactions();
            case 3 -> viewMyProfile();
            case 4 -> updateMyProfile();
            case 5 -> transactionMenu();
            case 0 -> {
                currentUser = null;
                loginMenu();
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void viewMyProfile() {
        System.out.println("\n=== MY PROFILE ===");
        TableFormatter formatter = new TableFormatter();
        formatter.setHeaders("Field", "Value");
        formatter.addRow("Name", currentUser.getName());
        formatter.addRow("Email", currentUser.getEmail());
        formatter.addRow("Role", currentUser.getRole());
        formatter.addRow("Membership Status", currentUser.getMembershipStatus());
        formatter.addRow("Date Joined", currentUser.getDateJoined().toString());
        formatter.addRow("Last Login", currentUser.getLastLogin().toString());
        formatter.addRow("Contact Number", currentUser.getContactNumber());
        formatter.addRow("Address", currentUser.getAddress());
        System.out.println(formatter.format());
    }

    private static void updateMyProfile() {
        System.out.println("\n=== UPDATE PROFILE ===");
        System.out.print("New Name (" + currentUser.getName() + "): ");
        currentUser.setName(scanner.nextLine());
        System.out.print("New Contact Number (" + currentUser.getContactNumber() + "): ");
        currentUser.setContactNumber(scanner.nextLine());
        System.out.print("New Address (" + currentUser.getAddress() + "): ");
        currentUser.setAddress(scanner.nextLine());
        
        boolean success = userService.updateUser(currentUser);
        System.out.println(success ? "Profile updated successfully." : "Failed to update profile.");
    }

    private static void systemSettingsMenu() {
        if (currentUser.getPrivilegeLevel() != 2) {
            System.out.println("Access denied. Admin privileges required.");
            return;
        }

        System.out.println("\n=== SYSTEM SETTINGS ===");
        System.out.println("1. Configure Database");
        System.out.println("2. Manage System Logs");
        System.out.println("3. Backup/Restore");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> configureDatabase();
            case 2 -> manageSystemLogs();
            case 3 -> backupRestore();
            case 0 -> { return; }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void viewAllTransactions() {
        if (currentUser.getPrivilegeLevel() != 2) {
            System.out.println("Access denied. Admin privileges required.");
            return;
        }
        
        List<LibraryTransaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions found.");
        } else {
            System.out.println("\n=== ALL TRANSACTIONS ===");
            TableFormatter formatter = new TableFormatter();
            formatter.setHeaders("Transaction ID", "User", "Title", "Type", "Date", "Due Date", "Status");
            
            for (LibraryTransaction tx : transactions) {
                Publication pub = publicationService.getPublicationById(tx.getPublicationId());
                User user = userService.getUserById(tx.getUserId());
                formatter.addRow(
                    String.valueOf(tx.getTransactionId()),
                    user.getName(),
                    pub.getTitle(),
                    tx.getTransactionType(),
                    tx.getTransactionDate().toString(),
                    tx.getDueDate().toString(),
                    tx.getLateFee() > 0 ? "Overdue" : "Active"
                );
            }
            System.out.println(formatter.format());
        }
    }

    private static void viewAllAccessLogs() {
        if (currentUser.getPrivilegeLevel() != 2) {
            System.out.println("Access denied. Admin privileges required.");
            return;
        }
        // Implementation for viewing all access logs
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Add User");
            System.out.println("2. View User by ID");
            System.out.println("3. View User by Email");
            System.out.println("4. View All Users");
            System.out.println("5. Update User");
            System.out.println("6. Delete User");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    User u = new User();
                    System.out.print("Name: "); u.setName(scanner.nextLine());
                    System.out.print("Email: "); u.setEmail(scanner.nextLine());
                    System.out.print("Password: "); u.setPassword(scanner.nextLine());
                    System.out.print("Role: "); u.setRole(scanner.nextLine());
                    System.out.print("Membership Status: "); u.setMembershipStatus(scanner.nextLine());
                    u.setDateJoined(new java.util.Date());
                    u.setLastLogin(new java.util.Date());
                    System.out.print("Contact Number: "); u.setContactNumber(scanner.nextLine());
                    System.out.print("Address: "); u.setAddress(scanner.nextLine());
                    boolean success = userService.registerUser(u);
                    System.out.println(success ? "User added successfully." : "Failed to add user.");
                }
                case 2 -> {
                    System.out.print("User ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    User u = userService.getUserById(id);
                    System.out.println(u != null ? u : "User not found.");
                }
                case 3 -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    User u = userService.getUserByEmail(email);
                    System.out.println(u != null ? u : "User not found.");
                }
                case 4 -> {
                    List<User> users = userService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("\nNo users found.");
                    } else {
                        System.out.println("\n=== ALL USERS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("ID", "Name", "Email", "Role", "Status", "Contact", "Address");
                        
                        for (User u : users) {
                            formatter.addRow(
                                String.valueOf(u.getUserId()),
                                u.getName(),
                                u.getEmail(),
                                u.getRole(),
                                u.getMembershipStatus(),
                                u.getContactNumber(),
                                u.getAddress()
                            );
                        }
                        System.out.println(formatter.format());
                    }
                }
                case 5 -> {
                    System.out.print("User ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    User u = userService.getUserById(id);
                    if (u != null) {
                        System.out.print("New Name (" + u.getName() + "): "); u.setName(scanner.nextLine());
                        System.out.print("New Email (" + u.getEmail() + "): "); u.setEmail(scanner.nextLine());
                        System.out.print("New Password: "); u.setPassword(scanner.nextLine());
                        System.out.print("New Role (" + u.getRole() + "): "); u.setRole(scanner.nextLine());
                        System.out.print("New Membership Status (" + u.getMembershipStatus() + "): "); u.setMembershipStatus(scanner.nextLine());
                        System.out.print("New Contact Number (" + u.getContactNumber() + "): "); u.setContactNumber(scanner.nextLine());
                        System.out.print("New Address (" + u.getAddress() + "): "); u.setAddress(scanner.nextLine());
                        boolean success = userService.updateUser(u);
                        System.out.println(success ? "User updated." : "Update failed.");
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 6 -> {
                    System.out.print("User ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    boolean success = userService.deleteUser(id);
                    System.out.println(success ? "User deleted." : "Delete failed.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void authorMenu() {
        while (true) {
            System.out.println("\n--- Author Management ---");
            System.out.println("1. Add Author");
            System.out.println("2. View Author by ID");
            System.out.println("3. View All Authors");
            System.out.println("4. Update Author");
            System.out.println("5. Delete Author");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    Author a = new Author();
                    System.out.print("First Name: "); a.setFirstName(scanner.nextLine());
                    System.out.print("Last Name: "); a.setLastName(scanner.nextLine());
                    System.out.print("Email: "); a.setEmail(scanner.nextLine());
                    System.out.print("Affiliation: "); a.setAffiliation(scanner.nextLine());
                    boolean success = authorService.createAuthor(a);
                    System.out.println(success ? "Author added successfully." : "Failed to add author.");
                }
                case 2 -> {
                    System.out.print("Author ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Author a = authorService.getAuthorById(id);
                    System.out.println(a != null ? a : "Author not found.");
                }
                case 3 -> {
                    List<Author> authors = authorService.getAllAuthors();
                    if (authors.isEmpty()) {
                        System.out.println("\nNo authors found.");
                    } else {
                        System.out.println("\n=== ALL AUTHORS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("ID", "First Name", "Last Name", "Email", "Affiliation");
                        
                        for (Author a : authors) {
                            formatter.addRow(
                                String.valueOf(a.getAuthorId()),
                                a.getFirstName(),
                                a.getLastName(),
                                a.getEmail(),
                                a.getAffiliation()
                            );
                        }
                        System.out.println(formatter.format());
                    }
                }
                case 4 -> {
                    System.out.print("Author ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Author a = authorService.getAuthorById(id);
                    if (a != null) {
                        System.out.print("New First Name (" + a.getFirstName() + "): "); a.setFirstName(scanner.nextLine());
                        System.out.print("New Last Name (" + a.getLastName() + "): "); a.setLastName(scanner.nextLine());
                        System.out.print("New Email (" + a.getEmail() + "): "); a.setEmail(scanner.nextLine());
                        System.out.print("New Affiliation (" + a.getAffiliation() + "): "); a.setAffiliation(scanner.nextLine());
                        boolean success = authorService.updateAuthor(a);
                        System.out.println(success ? "Author updated." : "Update failed.");
                    } else {
                        System.out.println("Author not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Author ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    boolean success = authorService.deleteAuthor(id);
                    System.out.println(success ? "Author deleted." : "Delete failed.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void bookMenu() {
        while (true) {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Book by Publication ID");
            System.out.println("3. View All Books");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Get Available Copies using Function");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());
    
            switch (option) {
                case 1 -> {
                    System.out.println("\n=== Add New Book ===");
                    System.out.println("First, let's create a new publication for this book.");
                    
                    Publication pub = new Publication();
                    System.out.print("Title: ");
                    pub.setTitle(scanner.nextLine());
                    System.out.print("Publication Year: ");
                    pub.setPublicationYear(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Language: ");
                    pub.setLanguage(scanner.nextLine());
                    System.out.print("Status (Published/Unpublished/In-Progress): ");
                    pub.setStatus(scanner.nextLine());
                    
                    if (publicationService.createPublication(pub)) {
                        System.out.println("Publication created successfully with ID: " + pub.getPublicationId());
                        
                        Book b = new Book();
                        b.setPublicationId(pub.getPublicationId());
                        System.out.print("\nPublisher: ");
                        b.setPublisher(scanner.nextLine());
                        System.out.print("ISBN: ");
                        b.setIsbn(scanner.nextLine());
                        System.out.print("Location: ");
                        b.setLocation(scanner.nextLine());
                        System.out.print("Available Copies: ");
                        b.setAvailableCopies(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Total Copies: ");
                        b.setTotalCopies(Integer.parseInt(scanner.nextLine()));
                        
                        boolean success = bookService.createBook(b);
                        System.out.println(success ? "Book added successfully." : "Failed to add book.");
                    } else {
                        System.out.println("Failed to create publication. Cannot add book.");
                    }
                }
                case 2 -> {
                    System.out.print("Publication ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Book b = bookService.getBookByPublicationId(id);
                    System.out.println(b != null ? b : "Book not found.");
                }
                case 3 -> {
                    List<Book> books = bookService.getAllBooks();
                    if (books.isEmpty()) {
                        System.out.println("\nNo books found.");
                    } else {
                        System.out.println("\n=== ALL BOOKS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("ID", "Title", "ISBN", "Publisher", "Year", "Status", "Copies");
                        
                        for (Book b : books) {
                            Publication pub = publicationService.getPublicationById(b.getPublicationId());
                            if (pub != null) {
                                formatter.addRow(
                                    String.valueOf(b.getPublicationId()),
                                    pub.getTitle() != null ? pub.getTitle() : "Unknown",
                                    b.getIsbn() != null ? b.getIsbn() : "Unknown",
                                    b.getPublisher() != null ? b.getPublisher() : "Unknown",
                                    String.valueOf(pub.getPublicationYear()),
                                    pub.getStatus() != null ? pub.getStatus() : "Unknown",
                                    String.valueOf(b.getAvailableCopies())
                                );
                            }
                        }
                        System.out.println(formatter.format());
                    }
                }
                case 4 -> {
                    System.out.print("Publication ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Book b = bookService.getBookByPublicationId(id);
                    if (b != null) {
                        System.out.print("New Publisher (" + b.getPublisher() + "): ");
                        b.setPublisher(scanner.nextLine());
                        System.out.print("New ISBN (" + b.getIsbn() + "): ");
                        b.setIsbn(scanner.nextLine());
                        System.out.print("New Location (" + b.getLocation() + "): ");
                        b.setLocation(scanner.nextLine());
                        System.out.print("New Available Copies (" + b.getAvailableCopies() + "): ");
                        b.setAvailableCopies(Integer.parseInt(scanner.nextLine()));
                        System.out.print("New Total Copies (" + b.getTotalCopies() + "): ");
                        b.setTotalCopies(Integer.parseInt(scanner.nextLine()));
                        boolean success = bookService.updateBook(b);
                        System.out.println(success ? "Book updated." : "Update failed.");
                    } else {
                        System.out.println("Book not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Publication ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    boolean success = bookService.deleteBook(id);
                    System.out.println(success ? "Book deleted." : "Delete failed.");
                }
                case 6 -> {
                    System.out.print("Publication ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Book b = bookService.getBookByPublicationId(id);
                    if (b != null) {
                        int available = bookService.getAvailableCopies(b);
                        System.out.println("Available copies: " + available);
                    } else {
                        System.out.println("Book not found.");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void categoryMenu() {
        while (true) {
            System.out.println("\n--- Category Management ---");
            System.out.println("1. Add Category");
            System.out.println("2. View Category by ID");
            System.out.println("3. View All Categories");
            System.out.println("4. Update Category");
            System.out.println("5. Delete Category");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    Category c = new Category();
                    System.out.print("Name: "); c.setName(scanner.nextLine());
                    System.out.print("Description: "); c.setDescription(scanner.nextLine());
                    boolean success = categoryService.createCategory(c);
                    System.out.println(success ? "Category added successfully." : "Failed to add category.");
                }
                case 2 -> {
                    System.out.print("Category ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Category c = categoryService.getCategoryById(id);
                    System.out.println(c != null ? c : "Category not found.");
                }
                case 3 -> {
                    List<Category> categories = categoryService.getAllCategories();
                    categories.forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("Category ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Category c = categoryService.getCategoryById(id);
                    if (c != null) {
                        System.out.print("New Name (" + c.getName() + "): "); c.setName(scanner.nextLine());
                        System.out.print("New Description (" + c.getDescription() + "): "); c.setDescription(scanner.nextLine());
                        boolean success = categoryService.updateCategory(c);
                        System.out.println(success ? "Category updated." : "Update failed.");
                    } else {
                        System.out.println("Category not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Category ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    boolean success = categoryService.deleteCategory(id);
                    System.out.println(success ? "Category deleted." : "Delete failed.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    private static void publicationMenu() {
        while (true) {
            System.out.println("\n--- Publication Management ---");
            System.out.println("1. Add Publication");
            System.out.println("2. View Publication by ID");
            System.out.println("3. View All Publications");
            System.out.println("4. Delete Publication");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());
    
            switch (option) {
                case 1 -> {
                    Publication p = new Publication();
                    System.out.print("Title: ");
                    p.setTitle(scanner.nextLine());
                    System.out.print("Year: ");
                    p.setPublicationYear(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Language: ");
                    p.setLanguage(scanner.nextLine());
                    System.out.print("Status (Published/Unpublished/In-Progress): ");
                    p.setStatus(scanner.nextLine());
                    boolean success = publicationService.createPublication(p);
                    if (success) {
                        System.out.println("Publication added successfully. ID: " + p.getPublicationId());
                    } else {
                        System.out.println("Failed to add publication.");
                    }
                }
                case 2 -> {
                    System.out.print("Publication ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Publication p = publicationService.getPublicationById(id);
                    if (p != null) {
                        System.out.println("\n=== PUBLICATION DETAILS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("Field", "Value");
                        formatter.addRow("ID", String.valueOf(p.getPublicationId()));
                        formatter.addRow("Title", p.getTitle());
                        formatter.addRow("Year", String.valueOf(p.getPublicationYear()));
                        formatter.addRow("Language", p.getLanguage());
                        formatter.addRow("Status", p.getStatus());
                        System.out.println(formatter.format());
                    } else {
                        System.out.println("Publication not found.");
                    }
                }
                case 3 -> {
                    List<Publication> pubs = publicationService.getAllPublications();
                    if (pubs.isEmpty()) {
                        System.out.println("\nNo publications found.");
                    } else {
                        System.out.println("\n=== ALL PUBLICATIONS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("ID", "Title", "Year", "Language", "Status");
                        
                        for (Publication p : pubs) {
                            formatter.addRow(
                                String.valueOf(p.getPublicationId()),
                                p.getTitle(),
                                String.valueOf(p.getPublicationYear()),
                                p.getLanguage(),
                                p.getStatus()
                            );
                        }
                        System.out.println(formatter.format());
                    }
                }
                case 4 -> {
                    System.out.print("Publication ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    boolean success = publicationService.deletePublication(id);
                    System.out.println(success ? "Publication deleted." : "Delete failed.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private static void librarianMenu() {
        while (true) {
            System.out.println("\n=== LIBRARIAN MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Librarian");
            System.out.println("2. View Librarian by Staff ID");
            System.out.println("3. View All Librarians");
            System.out.println("4. Update Librarian Details");
            System.out.println("5. Delete Librarian");
            System.out.println("6. Update Last Login");
            System.out.println("7. Search Librarian by Name");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    // First check if user exists
                    System.out.print("User Email: ");
                    String email = scanner.nextLine();
                    User user = userService.getUserByEmail(email);
                    
                    if (user == null) {
                        System.out.println("\n=== CREATE NEW USER ===");
                        user = new User();
                        System.out.print("Name: "); user.setName(scanner.nextLine());
                        user.setEmail(email);
                        System.out.print("Password: "); user.setPassword(scanner.nextLine());
                        user.setRole("Librarian");
                        user.setMembershipStatus("Active");
                        user.setDateJoined(new java.util.Date());
                        user.setLastLogin(new java.util.Date());
                        System.out.print("Contact Number: "); user.setContactNumber(scanner.nextLine());
                        System.out.print("Address: "); user.setAddress(scanner.nextLine());
                        
                        if (!userService.registerUser(user)) {
                            System.out.println("Failed to create user. Cannot add librarian.");
                            break;
                        }
                        System.out.println("User created successfully.");
                    }
                    
                    // Get and validate Staff ID
                    int staffId;
                    while (true) {
                        System.out.print("\nStaff ID (must be a 4-digit number): ");
                        try {
                            staffId = Integer.parseInt(scanner.nextLine());
                            if (staffId <= 0 || staffId > 9999) {
                                System.out.println("Staff ID must be a 4-digit positive number. Please try again.");
                                continue;
                            }
                            // Check if Staff ID already exists
                            Librarian existingLibrarian = librarianService.getLibrarianById(staffId);
                            if (existingLibrarian != null) {
                                System.out.println("This Staff ID is already in use. Please choose a different one.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                    
                    // Get work shift with validation
                    String workShift;
                    while (true) {
                        System.out.println("\nSelect Work Shift:");
                        System.out.println("1. Morning (8:00 AM - 4:00 PM)");
                        System.out.println("2. Evening (4:00 PM - 12:00 AM)");
                        System.out.println("3. Night (12:00 AM - 8:00 AM)");
                        System.out.print("Enter shift number: ");
                        int shiftChoice = Integer.parseInt(scanner.nextLine());
                        
                        switch (shiftChoice) {
                            case 1 -> workShift = "Morning";
                            case 2 -> workShift = "Evening";
                            case 3 -> workShift = "Night";
                            default -> {
                                System.out.println("Invalid choice. Please try again.");
                                continue;
                            }
                        }
                        break;
                    }
                    
                    Librarian l = new Librarian();
                    l.setStaffId(staffId);
                    l.setUserId(user.getUserId());
                    l.setWorkShift(workShift);
                    boolean success = librarianService.createLibrarian(l);
                    System.out.println(success ? "\nLibrarian added successfully with Staff ID: " + staffId : "Failed to add librarian.");
                }
                case 2 -> {
                    System.out.print("\nEnter Staff ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Librarian l = librarianService.getLibrarianById(id);
                    if (l != null) {
                        User user = userService.getUserById(l.getUserId());
                        System.out.println("\n=== LIBRARIAN PROFILE ===");
                        System.out.println("STAFF ID: " + l.getStaffId());
                        System.out.println("WORK SHIFT: " + l.getWorkShift());
                        System.out.println("\n=== PERSONAL INFORMATION ===");
                        System.out.println("Name: " + user.getName());
                        System.out.println("Email: " + user.getEmail());
                        System.out.println("Role: " + user.getRole());
                        System.out.println("Membership Status: " + user.getMembershipStatus());
                        System.out.println("Date Joined: " + user.getDateJoined());
                        System.out.println("Last Login: " + user.getLastLogin());
                        System.out.println("Contact Number: " + user.getContactNumber());
                        System.out.println("Address: " + user.getAddress());
                    } else {
                        System.out.println("Librarian not found.");
                    }
                }
                case 3 -> {
                    List<Librarian> librarians = librarianService.getAllLibrarians();
                    if (librarians.isEmpty()) {
                        System.out.println("\nNo librarians found in the system.");
                    } else {
                        System.out.println("\n=== LIBRARIAN LIST ===");
                        for (Librarian l : librarians) {
                            User user = userService.getUserById(l.getUserId());
                            System.out.println("\n=== LIBRARIAN PROFILE ===");
                            System.out.println("STAFF ID: " + l.getStaffId());
                            System.out.println("WORK SHIFT: " + l.getWorkShift());
                            System.out.println("\n=== PERSONAL INFORMATION ===");
                            System.out.println("Name: " + user.getName());
                            System.out.println("Email: " + user.getEmail());
                            System.out.println("Role: " + user.getRole());
                            System.out.println("Membership Status: " + user.getMembershipStatus());
                            System.out.println("Date Joined: " + user.getDateJoined());
                            System.out.println("Last Login: " + user.getLastLogin());
                            System.out.println("Contact Number: " + user.getContactNumber());
                            System.out.println("Address: " + user.getAddress());
                            System.out.println("----------------------------------------");
                        }
                    }
                }
                case 4 -> {
                    System.out.print("\nEnter Staff ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Librarian l = librarianService.getLibrarianById(id);
                    if (l != null) {
                        User user = userService.getUserById(l.getUserId());
                        System.out.println("\nCurrent Details:");
                        System.out.println("STAFF ID: " + l.getStaffId());
                        System.out.println("WORK SHIFT: " + l.getWorkShift());
                        System.out.println("Name: " + user.getName());
                        
                        // Update work shift
                        System.out.println("\nSelect new Work Shift:");
                        System.out.println("1. Morning (8:00 AM - 4:00 PM)");
                        System.out.println("2. Evening (4:00 PM - 12:00 AM)");
                        System.out.println("3. Night (12:00 AM - 8:00 AM)");
                        System.out.print("Enter shift number: ");
                        int shiftChoice = Integer.parseInt(scanner.nextLine());
                        
                        String newWorkShift = l.getWorkShift(); // Initialize with current shift
                        switch (shiftChoice) {
                            case 1 -> newWorkShift = "Morning";
                            case 2 -> newWorkShift = "Evening";
                            case 3 -> newWorkShift = "Night";
                            default -> {
                                System.out.println("Invalid choice. Update cancelled.");
                                break;
                            }
                        }
                        
                        l.setWorkShift(newWorkShift);
                        boolean success = librarianService.updateLibrarian(l);
                        System.out.println(success ? "\nLibrarian updated successfully." : "Failed to update librarian.");
                    } else {
                        System.out.println("Librarian not found.");
                    }
                }
                case 5 -> {
                    System.out.print("\nEnter Staff ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Are you sure you want to delete this librarian? (yes/no): ");
                    String confirm = scanner.nextLine().toLowerCase();
                    if (confirm.equals("yes")) {
                        boolean success = librarianService.deleteLibrarian(id);
                        System.out.println(success ? "Librarian deleted successfully." : "Failed to delete librarian.");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                }
                case 6 -> {
                    System.out.print("\nEnter Staff ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Librarian l = librarianService.getLibrarianById(id);
                    if (l != null) {
                        boolean success = userService.updateLastLogin(l.getUserId());
                        System.out.println(success ? "Last login updated successfully." : "Failed to update last login.");
                    } else {
                        System.out.println("Librarian not found.");
                    }
                }
                case 7 -> {
                    System.out.print("\nEnter librarian name to search: ");
                    String searchName = scanner.nextLine().toLowerCase();
                    List<Librarian> librarians = librarianService.getAllLibrarians();
                    boolean found = false;
                    
                    for (Librarian l : librarians) {
                        User user = userService.getUserById(l.getUserId());
                        if (user.getName().toLowerCase().contains(searchName)) {
                            found = true;
                            System.out.println("\n=== LIBRARIAN PROFILE ===");
                            System.out.println("STAFF ID: " + l.getStaffId());
                            System.out.println("WORK SHIFT: " + l.getWorkShift());
                            System.out.println("\n=== PERSONAL INFORMATION ===");
                            System.out.println("Name: " + user.getName());
                            System.out.println("Email: " + user.getEmail());
                            System.out.println("Role: " + user.getRole());
                            System.out.println("Membership Status: " + user.getMembershipStatus());
                            System.out.println("Date Joined: " + user.getDateJoined());
                            System.out.println("Last Login: " + user.getLastLogin());
                            System.out.println("Contact Number: " + user.getContactNumber());
                            System.out.println("Address: " + user.getAddress());
                            System.out.println("----------------------------------------");
                        }
                    }
                    if (!found) {
                        System.out.println("No librarians found with that name.");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void researchPaperMenu() {
        while (true) {
            System.out.println("\n--- Research Paper Management ---");
            System.out.println("1. Add Research Paper");
            System.out.println("2. View Research Paper by Publication ID");
            System.out.println("3. View All Research Papers");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    ResearchPaper rp = new ResearchPaper();
                    System.out.print("Publication ID: "); rp.setPublicationId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Journal Name: "); rp.setJournalName(scanner.nextLine());
                    System.out.print("Conference Name: "); rp.setConferenceName(scanner.nextLine());
                    System.out.print("DOI: "); rp.setDoi(scanner.nextLine());
                    System.out.print("Keywords: "); rp.setKeywords(scanner.nextLine());
                    System.out.print("Abstract: "); rp.setAbstractText(scanner.nextLine());
                    System.out.print("File: "); rp.setFile(scanner.nextLine());
                    System.out.print("Citations Count: "); rp.setCitationsCount(Integer.parseInt(scanner.nextLine()));
                    boolean success = researchPaperService.createResearchPaper(rp);
                    System.out.println(success ? "Research paper added successfully." : "Failed to add research paper.");
                }
                case 2 -> {
                    System.out.print("Publication ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    ResearchPaper rp = researchPaperService.getResearchPaperByPublicationId(id);
                    System.out.println(rp != null ? rp : "Research paper not found.");
                }
                case 3 -> {
                    List<ResearchPaper> papers = researchPaperService.getAllResearchPapers();
                    papers.forEach(System.out::println);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void citationMenu() {
        while (true) {
            System.out.println("\n--- Citation Management ---");
            System.out.println("1. Add Citation");
            System.out.println("2. View Citation by ID");
            System.out.println("3. View Citations by Publication ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    Citation c = new Citation();
                    System.out.print("User ID: "); c.setUserId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Publication ID: "); c.setPublicationId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Citation Date (YYYY-MM-DD): "); c.setCitationDate(java.sql.Date.valueOf(scanner.nextLine()));
                    boolean success = citationService.createCitation(c);
                    System.out.println(success ? "Citation added successfully." : "Failed to add citation.");
                }
                case 2 -> {
                    System.out.print("Citation ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Citation c = citationService.getCitationById(id);
                    System.out.println(c != null ? c : "Citation not found.");
                }
                case 3 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    List<Citation> citations = citationService.getCitationsByCitingPublication(pubId);
                    citations.forEach(System.out::println);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void transactionMenu() {
        while (true) {
            System.out.println("\n--- Library Transaction Management ---");
            System.out.println("1. Borrow a Publication");
            System.out.println("2. Return a Publication");
            System.out.println("3. View My Transactions");
            System.out.println("4. View Transaction Details");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    // Show available publications first
                    List<Publication> availableBooks = publicationService.getAllPublications();
                    if (availableBooks.isEmpty()) {
                        System.out.println("\nNo books available.");
                        continue;
                    } else {
                        System.out.println("\n=== AVAILABLE PUBLICATIONS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("ID", "Title", "Author", "Category", "Status", "Available Copies");
                        
                        for (Publication book : availableBooks) {
                            String authorName = "Unknown";
                            List<Author> authors = pubAuthorService.getAuthorsByPublicationId(book.getPublicationId());
                            if (!authors.isEmpty()) {
                                Author author = authors.get(0);
                                authorName = author.getFirstName() + " " + author.getLastName();
                            }
                            
                            String category = "Uncategorized";
                            List<Category> categories = pubCategoryService.getCategoriesByPublicationId(book.getPublicationId());
                            if (!categories.isEmpty()) {
                                category = categories.get(0).getName();
                            }
                            
                            int availableCopies = 0;
                            Book b = bookService.getBookByPublicationId(book.getPublicationId());
                            if (b != null) {
                                availableCopies = bookService.getAvailableCopies(b);
                            }
                            
                            formatter.addRow(
                                String.valueOf(book.getPublicationId()),
                                book.getTitle(),
                                authorName,
                                category,
                                book.getStatus(),
                                String.valueOf(availableCopies)
                            );
                        }
                        System.out.println(formatter.format());
                    }

                    System.out.print("\nEnter Publication ID to borrow: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    
                    // Check if it's a book and get available copies
                    Book book = bookService.getBookByPublicationId(pubId);
                    if (book != null) {
                        int availableCopies = bookService.getAvailableCopies(pubId);
                        if (availableCopies <= 0) {
                            System.out.println("Cannot borrow. No copies available.");
                            continue;
                        }
                        System.out.println("Available copies: " + availableCopies);
                        
                        LibraryTransaction tx = new LibraryTransaction();
                        tx.setUserId(currentUser.getUserId());
                        tx.setPublicationId(pubId);
                        tx.setTransactionType("Borrow");
                        tx.setTransactionDate(new java.util.Date());
                        
                        // Set due date (14 days from now)
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, 14);
                        tx.setDueDate(cal.getTime());
                        
                        // First update available copies
                        if (!bookService.updateAvailableCopies(pubId, -1)) {
                            System.out.println("Failed to update available copies. Cannot borrow.");
                            continue;
                        }
                        
                        // Then create transaction
                        boolean success = transactionService.createTransaction(tx);
                        if (!success) {
                            // If transaction fails, revert the available copies
                            bookService.updateAvailableCopies(pubId, 1);
                            System.out.println("Failed to borrow publication.");
                        } else {
                            System.out.println("Publication borrowed successfully.");
                        }
                    } else {
                        System.out.println("This publication is not a book or does not exist.");
                    }
                }
                case 2 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    
                    // Check if it's a book
                    Book book = bookService.getBookByPublicationId(pubId);
                    if (book != null) {
                        // First update available copies
                        if (!bookService.updateAvailableCopies(pubId, 1)) {
                            System.out.println("Failed to update available copies. Cannot return publication.");
                            continue;
                        }
                        
                        LibraryTransaction tx = new LibraryTransaction();
                        tx.setUserId(currentUser.getUserId());
                        tx.setPublicationId(pubId);
                        tx.setTransactionType("Return");
                        tx.setTransactionDate(new java.util.Date());
                        tx.setDueDate(new java.util.Date());
                        
                        boolean success = transactionService.createTransaction(tx);
                        if (!success) {
                            // If transaction fails, revert the available copies
                            bookService.updateAvailableCopies(pubId, -1);
                            System.out.println("Failed to return publication.");
                        } else {
                            System.out.println("Publication returned successfully.");
                        }
                    } else {
                        System.out.println("This publication is not a book or does not exist.");
                    }
                }
                case 3 -> viewMyTransactions();
                case 4 -> {
                    // Show all available transaction IDs first
                    List<LibraryTransaction> allTransactions = transactionService.getAllTransactions();
                    if (allTransactions.isEmpty()) {
                        System.out.println("\nNo transactions found.");
                        continue;
                    }
                    
                    System.out.println("\n=== AVAILABLE TRANSACTIONS ===");
                    TableFormatter formatter = new TableFormatter();
                    formatter.setHeaders("Transaction ID", "Publication", "Type", "Date");
                    
                    for (LibraryTransaction tx : allTransactions) {
                        Publication pub = publicationService.getPublicationById(tx.getPublicationId());
                        formatter.addRow(
                            String.valueOf(tx.getTransactionId()),
                            pub.getTitle(),
                            tx.getTransactionType(),
                            tx.getTransactionDate().toString()
                        );
                    }
                    System.out.println(formatter.format());
                    
                    System.out.print("\nEnter Transaction ID to view details: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    LibraryTransaction tx = transactionService.getTransactionById(id);
                    if (tx != null) {
                        Publication pub = publicationService.getPublicationById(tx.getPublicationId());
                        System.out.println("\n=== TRANSACTION DETAILS ===");
                        TableFormatter detailsFormatter = new TableFormatter();
                        detailsFormatter.setHeaders("Field", "Value");
                        detailsFormatter.addRow("Transaction ID", String.valueOf(tx.getTransactionId()));
                        detailsFormatter.addRow("Publication", pub.getTitle());
                        detailsFormatter.addRow("Type", tx.getTransactionType());
                        detailsFormatter.addRow("Date", tx.getTransactionDate().toString());
                        detailsFormatter.addRow("Due Date", tx.getDueDate().toString());
                        detailsFormatter.addRow("Late Fee", "Rs. " + tx.getLateFee());
                        
                        // Get all transactions for this publication
                        List<LibraryTransaction> allTx = transactionService.getAllTransactions();
                        for (LibraryTransaction returnTransaction : allTx) {
                            if (returnTransaction.getPublicationId() == tx.getPublicationId() && 
                                returnTransaction.getTransactionType().equals("Return") && 
                                returnTransaction.getTransactionDate().after(tx.getTransactionDate())) {
                                detailsFormatter.addRow("Return Date", returnTransaction.getTransactionDate().toString());
                                detailsFormatter.addRow("Return Status", "Completed");
                            }
                        }
                        System.out.println(detailsFormatter.format());
                    } else {
                        System.out.println("Transaction not found.");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void accessMenu() {
        while (true) {
            System.out.println("\n--- Publication Access Management ---");
            System.out.println("1. Add Access Record");
            System.out.println("2. View Access Records by User ID");
            System.out.println("3. View Access Records by Publication ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    PublicationAccess access = new PublicationAccess();
                    System.out.print("User ID: "); access.setUserId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Publication ID: "); access.setPublicationId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Access Date (YYYY-MM-DD): "); access.setAccessDate(java.sql.Date.valueOf(scanner.nextLine()));
                    System.out.print("Access Type: "); access.setAccessType(scanner.nextLine());
                    boolean success = publicationAccessService.createAccess(access);
                    System.out.println(success ? "Access record added successfully." : "Failed to add access record.");
                }
                case 2 -> {
                    System.out.print("User ID: ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    List<PublicationAccess> accesses = publicationAccessService.getAccessByUserId(userId);
                    accesses.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    List<PublicationAccess> accesses = publicationAccessService.getAccessByPublicationId(pubId);
                    accesses.forEach(System.out::println);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    private static void publicationAuthorMenu() {
        while (true) {
            System.out.println("\n--- Publication-Author Link Management ---");
            System.out.println("1. Link Author to Publication");
            System.out.println("2. View Authors by Publication ID");
            System.out.println("3. View Publications by Author ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Author ID: ");
                    int authorId = Integer.parseInt(scanner.nextLine());
                    boolean success = pubAuthorService.addAuthorToPublication(pubId, authorId);
                    System.out.println(success ? "Author linked successfully." : "Linking failed.");
                }
                case 2 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    List<Author> authors = pubAuthorService.getAuthorsByPublicationId(pubId);
                    authors.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Author ID: ");
                    int authorId = Integer.parseInt(scanner.nextLine());
                    List<Integer> pubs = pubAuthorService.getPublicationsByAuthorId(authorId);
                    pubs.forEach(pid -> System.out.println(publicationService.getPublicationById(pid)));
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void publicationCategoryMenu() {
        while (true) {
            System.out.println("\n--- Publication-Category Link Management ---");
            System.out.println("1. Link Category to Publication");
            System.out.println("2. View Categories by Publication ID");
            System.out.println("3. View Publications by Category ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());
                    boolean success = pubCategoryService.addCategoryToPublication(pubId, categoryId);
                    System.out.println(success ? "Category linked successfully." : "Linking failed.");
                }
                case 2 -> {
                    System.out.print("Publication ID: ");
                    int pubId = Integer.parseInt(scanner.nextLine());
                    List<Category> categories = pubCategoryService.getCategoriesByPublicationId(pubId);
                    categories.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());
                    List<Integer> pubs = pubCategoryService.getPublicationsByCategoryId(categoryId);
                    pubs.forEach(pid -> System.out.println(publicationService.getPublicationById(pid)));
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void researcherMenu() {
        while (true) {
            System.out.println("\n--- Researcher Management ---");
            System.out.println("1. Add Researcher");
            System.out.println("2. View Researcher by ID");
            System.out.println("3. View All Researchers");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    Researcher r = new Researcher();
                    System.out.print("Researcher ID: "); r.setResearcherId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("User ID: "); r.setUserId(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Institution: "); r.setInstitution(scanner.nextLine());
                    System.out.print("Research Interests: "); r.setResearchInterests(scanner.nextLine());
                    System.out.print("Publications Count: "); r.setPublicationsCount(Integer.parseInt(scanner.nextLine()));
                    boolean success = researcherService.createResearcher(r);
                    System.out.println(success ? "Researcher added successfully." : "Failed to add researcher.");
                }
                case 2 -> {
                    System.out.print("Researcher ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Researcher r = researcherService.getResearcherById(id);
                    System.out.println(r != null ? r : "Researcher not found.");
                }
                case 3 -> {
                    List<Researcher> researchers = researcherService.getAllResearchers();
                    if (researchers.isEmpty()) {
                        System.out.println("\nNo researchers found.");
                    } else {
                        System.out.println("\n=== ALL RESEARCHERS ===");
                        TableFormatter formatter = new TableFormatter();
                        formatter.setHeaders("ID", "Name", "Institution", "Research Interests", "Publications");
                        
                        for (Researcher r : researchers) {
                            User user = userService.getUserById(r.getUserId());
                            formatter.addRow(
                                String.valueOf(r.getResearcherId()),
                                user != null ? user.getName() : "Unknown",
                                r.getInstitution(),
                                r.getResearchInterests(),
                                String.valueOf(r.getPublicationsCount())
                            );
                        }
                        System.out.println(formatter.format());
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void viewAvailableBooks() {
        List<Publication> availableBooks = publicationService.getAllPublications();
        if (availableBooks.isEmpty()) {
            System.out.println("\nNo books available.");
        } else {
            System.out.println("\n=== AVAILABLE BOOKS ===");
            TableFormatter formatter = new TableFormatter();
            formatter.setHeaders("ID", "Title", "Author", "Category", "Status", "Available Copies");
            
            for (Publication book : availableBooks) {
                String authorName = "Unknown";
                List<Author> authors = pubAuthorService.getAuthorsByPublicationId(book.getPublicationId());
                if (!authors.isEmpty()) {
                    Author author = authors.get(0);
                    authorName = author.getFirstName() + " " + author.getLastName();
                }
                
                String category = "Uncategorized";
                List<Category> categories = pubCategoryService.getCategoriesByPublicationId(book.getPublicationId());
                if (!categories.isEmpty()) {
                    category = categories.get(0).getName();
                }
                
                int availableCopies = 0;
                Book b = bookService.getBookByPublicationId(book.getPublicationId());
                if (b != null) {
                    availableCopies = bookService.getAvailableCopies(b);
                }
                
                formatter.addRow(
                    String.valueOf(book.getPublicationId()),
                    book.getTitle(),
                    authorName,
                    category,
                    book.getStatus(),
                    String.valueOf(availableCopies)
                );
            }
            System.out.println(formatter.format());
            
            // Add option to borrow a book
            System.out.println("\nWould you like to borrow a book? (yes/no)");
            String choice = scanner.nextLine().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Enter the ID of the book you want to borrow: ");
                int bookId = Integer.parseInt(scanner.nextLine());
                
                // Check if the book exists
                Publication pub = publicationService.getPublicationById(bookId);
                if (pub == null) {
                    System.out.println("Book not found.");
                    return;
                }
                
                // Check if it's a book and has available copies
                Book book = bookService.getBookByPublicationId(bookId);
                if (book != null) {
                    int available = bookService.getAvailableCopies(book);
                    if (available <= 0) {
                        System.out.println("No copies available for borrowing.");
                        return;
                    }
                }
                
                // Create transaction
                LibraryTransaction tx = new LibraryTransaction();
                tx.setUserId(currentUser.getUserId());
                tx.setPublicationId(bookId);
                tx.setTransactionType("Borrow");
                tx.setTransactionDate(new java.util.Date());
                
                // Set due date (14 days from now)
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.DAY_OF_MONTH, 14);
                tx.setDueDate(cal.getTime());
                tx.setLateFee(0.0);
                
                boolean success = transactionService.createTransaction(tx);
                System.out.println(success ? "Book borrowed successfully!" : "Failed to borrow book.");
            }
        }
    }

    private static void viewMyTransactions() {
        List<LibraryTransaction> transactions = transactionService.getTransactionsByUserId(currentUser.getUserId());
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions found.");
        } else {
            System.out.println("\n=== MY TRANSACTIONS ===");
            TableFormatter formatter = new TableFormatter();
            formatter.setHeaders("Title", "Type", "Date", "Due Date", "Status");
            
            for (LibraryTransaction tx : transactions) {
                Publication pub = publicationService.getPublicationById(tx.getPublicationId());
                formatter.addRow(
                    pub.getTitle(),
                    tx.getTransactionType(),
                    tx.getTransactionDate().toString(),
                    tx.getDueDate().toString(),
                    tx.getLateFee() > 0 ? "Overdue" : "Active"
                );
            }
            System.out.println(formatter.format());
        }
    }

    private static void configureDatabase() {
        System.out.println("\n=== DATABASE CONFIGURATION ===");
        System.out.println("1. Test Connection");
        System.out.println("2. Reset Database");
        System.out.println("3. Optimize Database");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> {
                boolean connected = userService.testDatabaseConnection();
                System.out.println(connected ? "Database connection successful." : "Database connection failed.");
            }
            case 2 -> {
                System.out.print("Are you sure you want to reset the database? (yes/no): ");
                String confirm = scanner.nextLine().toLowerCase();
                if (confirm.equals("yes")) {
                    boolean success = userService.resetDatabase();
                    System.out.println(success ? "Database reset successful." : "Database reset failed.");
                }
            }
            case 3 -> {
                boolean success = userService.optimizeDatabase();
                System.out.println(success ? "Database optimization successful." : "Database optimization failed.");
            }
            case 0 -> { return; }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void manageSystemLogs() {
        System.out.println("\n=== SYSTEM LOGS MANAGEMENT ===");
        System.out.println("1. View All Logs");
        System.out.println("2. Clear Logs");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> {
                List<String> logs = userService.getAllLogs();
                logs.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Are you sure you want to clear all logs? (yes/no): ");
                String confirm = scanner.nextLine().toLowerCase();
                if (confirm.equals("yes")) {
                    boolean success = userService.clearLogs();
                    System.out.println(success ? "Logs cleared successfully." : "Failed to clear logs.");
                }
            }
            case 0 -> { return; }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void backupRestore() {
        System.out.println("\n=== DATABASE BACKUP/RESTORE ===");
        System.out.println("1. Create Backup");
        System.out.println("2. Restore from Backup");
        System.out.println("0. Back");
        System.out.print("Select an option: ");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> {
                boolean success = userService.createBackup();
                System.out.println(success ? "Backup created successfully." : "Backup failed.");
            }
            case 2 -> {
                boolean success = userService.restoreFromBackup();
                System.out.println(success ? "Restore completed successfully." : "Restore failed.");
            }
            case 0 -> { return; }
            default -> System.out.println("Invalid option. Try again.");
        }
    }
}
