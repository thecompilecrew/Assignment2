package com.textbookmarket.interfaces;

/**
 * Interface for any object that supports search functionality.
 * Demonstrates OOP Interface concept.
 */
public interface ISearchable {
    boolean matchesQuery(String query);
    String getSearchableText();
}
