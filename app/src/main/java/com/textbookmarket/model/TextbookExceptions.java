package com.textbookmarket.model;

/**
 * Custom exception classes — demonstrates Exception Handling as required.
 */
public class TextbookExceptions {

    /**
     * Thrown when a duplicate textbook listing is detected.
     */
    public static class DuplicateTextbookException extends Exception {
        private final String duplicateKey;

        public DuplicateTextbookException(String title, String sellerName) {
            super("The textbook \"" + title + "\" is already listed by " + sellerName
                    + ". Duplicate entries are not allowed.");
            this.duplicateKey = title + "_" + sellerName;
        }

        public String getDuplicateKey() { return duplicateKey; }
    }

    /**
     * Thrown when required textbook fields are invalid or missing.
     */
    public static class InvalidTextbookDataException extends Exception {
        private final String fieldName;

        public InvalidTextbookDataException(String fieldName, String reason) {
            super("Invalid data for field \"" + fieldName + "\": " + reason);
            this.fieldName = fieldName;
        }

        public String getFieldName() { return fieldName; }
    }

    /**
     * Thrown when a textbook listing cannot be found.
     */
    public static class TextbookNotFoundException extends Exception {
        public TextbookNotFoundException(String query) {
            super("No textbook found matching: \"" + query + "\"");
        }
    }
}
