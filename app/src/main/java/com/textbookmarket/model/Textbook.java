package com.textbookmarket.model;

/**
 * Textbook model — extends the abstract BaseItem.
 * Demonstrates Inheritance and OOP concepts.
 */
public class Textbook extends BaseItem {

    private String author;
    private String isbn;
    private String subject;
    private String condition;
    private int numberOfCopies;
    private String bankName;
    private String accountNumber;
    private String accountHolder;
    private String edition;
    private String description;
    private boolean isPreloaded; // true for built-in books

    // Constructor for pre-loaded (built-in) books
    public Textbook(String id, String title, String author, String isbn,
                    String subject, String edition, String condition,
                    double price, int numberOfCopies, String sellerName) {
        super(id, title, price, sellerName);
        this.author = author;
        this.isbn = isbn;
        this.subject = subject;
        this.edition = edition;
        this.condition = condition;
        this.numberOfCopies = numberOfCopies;
        this.bankName = "Campus Bookstore";
        this.accountNumber = "N/A";
        this.accountHolder = sellerName;
        this.description = "";
        this.isPreloaded = true;
    }

    // Constructor for user-listed books
    public Textbook(String id, String title, String author, String isbn,
                    String subject, String edition, String condition,
                    double price, int numberOfCopies, String sellerName,
                    String bankName, String accountNumber, String accountHolder,
                    String description) {
        super(id, title, price, sellerName);
        this.author = author;
        this.isbn = isbn;
        this.subject = subject;
        this.edition = edition;
        this.condition = condition;
        this.numberOfCopies = numberOfCopies;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.description = description;
        this.isPreloaded = false;
    }

    @Override
    public String getCategory() {
        return subject;
    }

    @Override
    public String getDetailedInfo() {
        return "Title: " + title + "\n"
                + "Author: " + author + "\n"
                + "ISBN: " + isbn + "\n"
                + "Edition: " + edition + "\n"
                + "Subject: " + subject + "\n"
                + "Condition: " + condition + "\n"
                + "Copies Available: " + numberOfCopies + "\n"
                + "Price: R" + String.format("%.2f", price) + "\n"
                + "Seller: " + sellerName + "\n"
                + "Bank: " + bankName + "\n"
                + "Account Holder: " + accountHolder + "\n"
                + "Account Number: " + accountNumber;
    }

    @Override
    public String getSummary() {
        return title + " by " + author + " — R" + String.format("%.2f", price);
    }

    // Getters
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getSubject() { return subject; }
    public String getCondition() { return condition; }
    public int getNumberOfCopies() { return numberOfCopies; }
    public String getBankName() { return bankName; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public String getEdition() { return edition; }
    public String getDescription() { return description; }
    public boolean isPreloaded() { return isPreloaded; }

    public void setNumberOfCopies(int numberOfCopies) { this.numberOfCopies = numberOfCopies; }

    /**
     * Unique key used to detect duplicate listings.
     * Two books are considered duplicates if same ISBN + sellerName.
     */
    public String getDuplicateKey() {
        return (isbn + "_" + sellerName).toLowerCase().trim();
    }

    @Override
    public String getSearchableText() {
        return (title + " " + author + " " + sellerName + " " + subject + " " + isbn).toLowerCase();
    }
}
