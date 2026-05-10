# TextBook Market — CSC313 Assignment 2

**University of Fort Hare | Department of Computer Science**  
**Module:** CSC313 — Object-Oriented Programming  
**Assignment:** Assignment 2 — Implementing OOP Concepts, Exception Handling, Interfaces & Abstract Classes

---

## App Overview

**TextBook Market** is an Android application that allows university students to buy and sell used textbooks. It was built using Java in Android Studio.
<img width="90" height="165" alt="WhatsApp Image 2026-05-10 at 15 34 28" src="https://github.com/user-attachments/assets/f944dcf4-bbd5-46e9-8b35-382690d9b575" />

---

##  Features

| Feature | Description |
|---|---|
| Browse | View all 12+ pre-loaded textbooks available for sale |
| List a Book | Sellers can list textbooks with full details including banking info |
| Search | Search by book title, author, or seller name (live search) |
| Book Detail | Full detail page for each book including payment info |
| Duplicate Prevention | App blocks duplicate listings (same book + same seller) |
| Multiple Listings | Sellers can list multiple different books in one session |

---

## OOP Concepts Demonstrated

### Interfaces
- `IListable` — defines `getTitle()`, `getPrice()`, `isAvailable()`, `getSummary()`
- `ISearchable` — defines `matchesQuery()`, `getSearchableText()`

### Abstract Class
- `BaseItem` — abstract base with shared fields; subclasses must implement `getCategory()` and `getDetailedInfo()`

### Inheritance
- `Textbook extends BaseItem` — extends the abstract class and implements both interfaces

### Exception Handling (Custom Exceptions)
- `DuplicateTextbookException` — thrown when a duplicate listing is detected
- `InvalidTextbookDataException` — thrown when required fields are missing or invalid
- `TextbookNotFoundException` — thrown when a search returns no matching book

### Encapsulation
- All model fields are `private`/`protected` with controlled access via getters/setters

### Singleton Pattern
- `TextbookRepository` uses the Singleton pattern for centralised data management

---

## Project Structure

```
TextbookMarket/
├── app/src/main/
│   ├── java/com/textbookmarket/
│   │   ├── interfaces/
│   │   │   ├── IListable.java
│   │   │   └── ISearchable.java
│   │   ├── model/
│   │   │   ├── BaseItem.java          ← Abstract Class
│   │   │   ├── Textbook.java          ← Concrete Model
│   │   │   └── TextbookExceptions.java ← Custom Exceptions
│   │   ├── repository/
│   │   │   └── TextbookRepository.java ← Singleton Data Layer
│   │   ├── adapter/
│   │   │   └── TextbookAdapter.java   ← RecyclerView Adapter
│   │   └── ui/
│   │       ├── SplashActivity.java
│   │       ├── MainActivity.java      ← Dashboard
│   │       ├── BrowseActivity.java    ← Browse all books
│   │       ├── ListBookActivity.java  ← Seller form
│   │       ├── SearchActivity.java    ← Search screen
│   │       └── BookDetailActivity.java ← Book detail view
│   └── res/
│       ├── layout/                    ← All XML layouts
│       ├── values/                    ← Colors, strings, themes
│       └── drawable/                  ← Custom backgrounds
```

---

##  Group Members

1. Songezo Gwabavu
2. Wandile Gobongo
3. Mbasa Nyivana
4. Sisipho Tom
5. Sinoyolo Mgandela
6. Enathi Nomda

---

##  Notes

- Built with Java (no Kotlin)
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- Uses Material Components library
- No external database — data is managed in-memory via Singleton Repository
