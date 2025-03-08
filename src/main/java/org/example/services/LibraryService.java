package org.example.services;

import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class LibraryService {
    Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public void loanBook(Book book, Reader reader) {
        if (!book.isLoaned()) {
            book.setLoaned(true);
            reader.addBook(book);
            System.out.println(reader.getName() + " si zapůjčil " + book.getTitle());
        } else {
            System.out.println("Kniha momentálně není dostupná");
        }
    }

    public void returnBook(Book book, Reader reader) {
        if (book.isLoaned() && reader.getBorrowedBooks().contains(book)) {
            book.setLoaned(false);
            reader.removeBook(book);
            System.out.println(reader.getName() + " vrátil knihu " + book.getTitle());
        } else {
            System.out.println("Čtenář neměl danou knihu zapůjčenou!");
        }
    }

    public Book findBookByTitle(String bookTitle) {
        for (Book book : library.getBooks()) {
            if (book.getTitle().equals(bookTitle)) {
                System.out.println("Knížka: " + book.getTitle());
                return book;
            }
        }
        return null;
    }

    public List<Book> findBookByAuthor(String bookAuthor) {
        return library.getBooks().stream()
                .filter(book -> book.getAuthor().equals(bookAuthor)).toList();
    }

    public List<Book> sortByTitle() {
        return library.getBooks().stream().sorted(Comparator.comparing(Book::getTitle)).toList();
    }

    public List<Book> sortByAuthor() {
        return library.getBooks().stream().sorted(Comparator.comparing(Book::getAuthor)).toList();
    }

    public void saveLibraryToFile() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir(); // Vytvoří složku data pokud neexistuje
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/library_data.ser"))) {
            oos.writeObject(library.getBooks());
            System.out.println("Knihovna byla v pořádku uložena");
        } catch (IOException e) {
            System.out.println("Nastala chyba při ukládání knihovny! " + e.getMessage());
        }
    }

    public void loadLibraryFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/library_data.ser"))) {
            List<Book> books = (List<Book>) ois.readObject();
            library.setBooks(books);
            System.out.println("Knížky byly úspěšně načteny!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nastala chyba při načítání knihovny! " + e.getMessage());
        }
    }
}
