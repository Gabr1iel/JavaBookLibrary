package org.example.utils;

import org.example.models.Book;
import org.example.models.Reader;
import org.example.models.Library;

import java.io.*;
import java.util.List;

public class FileHandler {
    Library library;

    public FileHandler(Library library){
        this.library = library;
    }

    public void saveBooksToFile() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir(); // Vytvoří složku data pokud neexistuje
        }
        if (library.getBookServices().getBooks() == null || library.getBookServices().getBooks().isEmpty()) {
            System.out.println("Seznam knih je prázdný!");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/books_data.ser"))) {
            oos.writeObject(library.getBookServices().getBooks());
            System.out.println("Knihy byly v pořádku uloženy");
        } catch (IOException e) {
            System.out.println("Nastala chyba při ukládání knížek! " + e.getMessage());
        }
    }

    public void loadBooksFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/books_data.ser"))) {
            List<Book> books = (List<Book>) ois.readObject();
            library.getBookServices().setBooks(books);
            System.out.println("Knihy byly úspěšně načteny!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nastala chyba při načítání knih! " + e.getMessage());
        }
    }

    public void saveReadersToFile() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (library.getReaderServices().getReaders() == null || library.getReaderServices().getReaders().isEmpty()) {
            System.out.println("Žádní čtenáři nejsou v seznamu!");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/readers_data.ser"))) {
            oos.writeObject(library.getReaderServices().getReaders());
            System.out.println("Čtenáři byli úspěšně uloženi");
        } catch (IOException e) {
            System.out.println("Nastla chyba při ukládání čtenářů" + e.getMessage());
        }
    }

    public void loadReadersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/readers_data.ser"))) {
            List<Reader> readers = (List<Reader>) ois.readObject();
            library.getReaderServices().setReaders(readers);
            System.out.println("Čtenáři byli úspěšně načteni!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nastala chyba při načítání čtenářů! " + e.getMessage());
        }
    }
}
