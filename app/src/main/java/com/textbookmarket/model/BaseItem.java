package com.textbookmarket.model;

import com.textbookmarket.interfaces.IListable;
import com.textbookmarket.interfaces.ISearchable;

/**
 * Abstract base class for all marketplace items.
 * Demonstrates Abstract Class concept as required by the assignment.
 */
public abstract class BaseItem implements IListable, ISearchable {

    protected String id;
    protected String title;
    protected double price;
    protected String sellerName;
    protected boolean available;

    public BaseItem(String id, String title, double price, String sellerName) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.sellerName = sellerName;
        this.available = true;
    }

    // Abstract method — subclasses must implement their own category logic
    public abstract String getCategory();

    // Abstract method — subclasses define how they format their details
    public abstract String getDetailedInfo();

    @Override
    public String getTitle() { return title; }

    @Override
    public double getPrice() { return price; }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public String getSearchableText() {
        return (title + " " + sellerName + " " + getCategory()).toLowerCase();
    }

    @Override
    public boolean matchesQuery(String query) {
        if (query == null || query.trim().isEmpty()) return true;
        return getSearchableText().contains(query.toLowerCase().trim());
    }

    public String getId() { return id; }
    public String getSellerName() { return sellerName; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setPrice(double price) { this.price = price; }
}
