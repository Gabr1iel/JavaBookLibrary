package org.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reader implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String birthDate;
    private String address;
    private List<Book> borrowedBooks;

    public Reader(String name, String birthDate, String address) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBook(Book book) {
        borrowedBooks.remove(book);
    }
}
