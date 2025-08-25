package org.example.models;

import java.io.Serializable;
import java.util.*;

public class Reader implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String Id = UUID.randomUUID().toString();
    private String name;
    private String email;
    private String address;
    private final HashSet<String> borrowedBooks;

    public Reader(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.borrowedBooks = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashSet<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public String getId() {
        return Id;
    }

    public void addBook(Book book) {
        borrowedBooks.add(book.getTitle());
    }

    public void removeBook(Book book) {
        borrowedBooks.remove(book.getTitle());
    }
}
