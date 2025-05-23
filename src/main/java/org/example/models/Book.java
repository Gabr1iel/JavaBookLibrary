package org.example.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String Id = UUID.randomUUID().toString();
    private String title;
    private String author;
    private String releaseDate;
    private List<Genre> bookGenres;
    private boolean isLoaned = false;

    public Book(String title, String author, String releaseDate, List<Genre> bookGenres) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.bookGenres = bookGenres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    public String getId() {
        return Id;
    }

    public List<Genre> getBookGenres() {
        return bookGenres;
    }

    public void setBookGenres(List<Genre> bookGenres) {
        this.bookGenres = bookGenres;
    }

    public void borrowBook() {
        if (!isLoaned) {
            isLoaned = true;
        } else {
            System.out.println("Kniha je již zapůjčená");
        }
    }

    public void returnBook() {
        if (isLoaned) {
            isLoaned = false;
        } else {
            System.out.println("Knihu momentálně nemá nikdo zapůjčenou");
        }
    }
}
