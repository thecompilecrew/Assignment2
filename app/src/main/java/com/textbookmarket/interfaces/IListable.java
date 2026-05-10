package com.textbookmarket.interfaces;

/**
 * Interface representing any item that can be listed for sale.
 * Demonstrates OOP Interface concept as required by the assignment.
 */
public interface IListable {
    String getTitle();
    double getPrice();
    boolean isAvailable();
    String getSummary();
}
