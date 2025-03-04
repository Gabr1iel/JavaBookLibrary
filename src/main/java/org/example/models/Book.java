package org.example.models;

public class Book {
    private String title;
    private String author;
    private String releaseDate;
    private boolean isLoaned = false;

    public Book(String title, String author, String releaseDate) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
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
