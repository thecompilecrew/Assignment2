package com.textbookmarket.repository;

import com.textbookmarket.model.Textbook;
import com.textbookmarket.model.TextbookExceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Singleton Repository managing all textbook data.
 * Demonstrates efficient data management.
 */
public class TextbookRepository {

    private static TextbookRepository instance;
    private final List<Textbook> textbooks;
    private final Map<String, Textbook> duplicateGuard; // key -> textbook

    private TextbookRepository() {
        textbooks = new ArrayList<>();
        duplicateGuard = new HashMap<>();
        loadPrebuiltBooks();
    }

    public static TextbookRepository getInstance() {
        if (instance == null) {
            instance = new TextbookRepository();
        }
        return instance;
    }

    // ─── Pre-loaded Books ───────────────────────────────────────────────────

    private void loadPrebuiltBooks() {
        addPreloaded(new Textbook("pre_001",
                "Introduction to Java Programming",
                "Y. Daniel Liang", "978-0-13-657556-3",
                "Computer Science", "11th Edition", "Good",
                350.00, 5, "Campus Bookstore"));

        addPreloaded(new Textbook("pre_002",
                "Object-Oriented Software Engineering",
                "Bernd Bruegge & Allen Dutoit", "978-0-13-606125-0",
                "Software Engineering", "3rd Edition", "Very Good",
                420.00, 3, "Campus Bookstore"));

        addPreloaded(new Textbook("pre_003",
                "Data Structures and Algorithm Analysis in Java",
                "Mark Allen Weiss", "978-0-13-257627-7",
                "Computer Science", "3rd Edition", "Good",
                390.00, 4, "Campus Bookstore"));

        addPreloaded(new Textbook("pre_004",
                "Database System Concepts",
                "Abraham Silberschatz", "978-0-07-802215-9",
                "Database Systems", "7th Edition", "Excellent",
                480.00, 2, "Campus Bookstore"));

        addPreloaded(new Textbook("pre_005",
                "Operating System Concepts (Dinosaur Book)",
                "Silberschatz, Galvin, Gagne", "978-1-118-06333-0",
                "Operating Systems", "9th Edition", "Good",
                460.00, 6, "Library Sale Desk"));

        addPreloaded(new Textbook("pre_006",
                "Computer Networks",
                "Andrew S. Tanenbaum", "978-0-13-212695-3",
                "Networking", "5th Edition", "Acceptable",
                310.00, 3, "Library Sale Desk"));

        addPreloaded(new Textbook("pre_007",
                "Artificial Intelligence: A Modern Approach",
                "Stuart Russell & Peter Norvig", "978-0-13-468259-0",
                "Artificial Intelligence", "4th Edition", "Very Good",
                550.00, 2, "Science Faculty Store"));

        addPreloaded(new Textbook("pre_008",
                "Discrete Mathematics and Its Applications",
                "Kenneth Rosen", "978-0-07-338309-5",
                "Mathematics", "7th Edition", "Good",
                370.00, 5, "Campus Bookstore"));

        addPreloaded(new Textbook("pre_009",
                "Software Engineering",
                "Ian Sommerville", "978-0-13-394303-0",
                "Software Engineering", "10th Edition", "Excellent",
                430.00, 4, "Science Faculty Store"));

        addPreloaded(new Textbook("pre_010",
                "Computer Organization and Architecture",
                "William Stallings", "978-0-13-293633-0",
                "Computer Architecture", "10th Edition", "Good",
                400.00, 3, "Library Sale Desk"));

        addPreloaded(new Textbook("pre_011",
                "Web Technologies: A Computer Science Perspective",
                "Jeffrey Jackson", "978-0-13-185514-1",
                "Web Development", "1st Edition", "Acceptable",
                280.00, 7, "Campus Bookstore"));

        addPreloaded(new Textbook("pre_012",
                "Introduction to Algorithms",
                "Cormen, Leiserson, Rivest, Stein", "978-0-26-204630-5",
                "Algorithms", "3rd Edition", "Very Good",
                520.00, 2, "Science Faculty Store"));
    }

    private void addPreloaded(Textbook book) {
        textbooks.add(book);
        duplicateGuard.put(book.getDuplicateKey(), book);
    }

    // ─── Public API ─────────────────────────────────────────────────────────

    /**
     * Add a new textbook listing.
     * @throws TextbookExceptions.DuplicateTextbookException if same book by same seller already exists
     * @throws TextbookExceptions.InvalidTextbookDataException if any required field is invalid
     */
    public void addTextbook(Textbook book)
            throws TextbookExceptions.DuplicateTextbookException,
                   TextbookExceptions.InvalidTextbookDataException {

        // Validate required fields
        validateTextbook(book);

        // Check for duplicates
        String key = book.getDuplicateKey();
        if (duplicateGuard.containsKey(key)) {
            throw new TextbookExceptions.DuplicateTextbookException(book.getTitle(), book.getSellerName());
        }

        String generatedId = "usr_" + UUID.randomUUID().toString().substring(0, 8);
        // We can't change the ID since it's set in constructor, but we track via map
        duplicateGuard.put(key, book);
        textbooks.add(book);
    }

    private void validateTextbook(Textbook book) throws TextbookExceptions.InvalidTextbookDataException {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new TextbookExceptions.InvalidTextbookDataException("Title", "Title cannot be empty.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new TextbookExceptions.InvalidTextbookDataException("Author", "Author cannot be empty.");
        }
        if (book.getPrice() <= 0) {
            throw new TextbookExceptions.InvalidTextbookDataException("Price", "Price must be greater than zero.");
        }
        if (book.getNumberOfCopies() <= 0) {
            throw new TextbookExceptions.InvalidTextbookDataException("Copies", "Number of copies must be at least 1.");
        }
        if (book.getSellerName() == null || book.getSellerName().trim().isEmpty()) {
            throw new TextbookExceptions.InvalidTextbookDataException("Seller Name", "Seller name cannot be empty.");
        }
        if (book.getBankName() == null || book.getBankName().trim().isEmpty()) {
            throw new TextbookExceptions.InvalidTextbookDataException("Bank Name", "Bank name cannot be empty.");
        }
        if (book.getAccountNumber() == null || book.getAccountNumber().trim().isEmpty()) {
            throw new TextbookExceptions.InvalidTextbookDataException("Account Number", "Account number cannot be empty.");
        }
    }

    /**
     * Return all textbooks.
     */
    public List<Textbook> getAllTextbooks() {
        return new ArrayList<>(textbooks);
    }

    /**
     * Search by seller name OR book title (case-insensitive).
     */
    public List<Textbook> search(String query) {
        List<Textbook> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return getAllTextbooks();
        }
        String q = query.toLowerCase().trim();
        for (Textbook book : textbooks) {
            if (book.getTitle().toLowerCase().contains(q)
                    || book.getSellerName().toLowerCase().contains(q)
                    || book.getAuthor().toLowerCase().contains(q)
                    || book.getSubject().toLowerCase().contains(q)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Get a single textbook by its ID.
     */
    public Textbook getById(String id) {
        for (Textbook book : textbooks) {
            if (book.getId().equals(id)) return book;
        }
        return null;
    }

    public int getTotalCount() { return textbooks.size(); }
}
