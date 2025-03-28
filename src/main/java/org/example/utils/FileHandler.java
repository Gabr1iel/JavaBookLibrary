package org.example.utils;

import org.example.models.Book;
import org.example.models.Genre;
import org.example.models.Reader;
import org.example.models.Library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public void saveBooksToFile(List<Book> books) {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir(); // Vytvoří složku data pokud neexistuje
        }
        if (books == null || books.isEmpty()) {
            System.out.println("Seznam knih je prázdný!");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/books_data.ser"))) {
            oos.writeObject(books);
            System.out.println("Knihy byly v pořádku uloženy");
        } catch (IOException e) {
            System.out.println("Nastala chyba při ukládání knížek! " + e.getMessage());
        }
    }

    public List<Book> loadBooksFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/books_data.ser"))) {
            List<Book> books = (List<Book>) ois.readObject();
            System.out.println("Knihy byly úspěšně načteny!");
            return books;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nastala chyba při načítání knih! " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveReadersToFile(List<Reader> readers) {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (readers == null || readers.isEmpty()) {
            System.out.println("Žádní čtenáři nejsou v seznamu!");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/readers_data.ser"))) {
            oos.writeObject(readers);
            System.out.println("Čtenáři byli úspěšně uloženi");
        } catch (IOException e) {
            System.out.println("Nastla chyba při ukládání čtenářů" + e.getMessage());
        }
    }

    public List<Reader> loadReadersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/readers_data.ser"))) {
            List<Reader> readers = (List<Reader>) ois.readObject();
            System.out.println("Čtenáři byli úspěšně načteni!");
            return readers;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nastala chyba při načítání čtenářů! " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveGenresToFile(List<Genre> genres) {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (genres == null || genres.isEmpty()) {
            System.out.print("Žádné žánry nejsou k dispozici pro uložení!");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/genres_data.ser"))) {
            oos.writeObject(genres);
            System.out.println("Žánry byly úspěšně uloženy!");
        } catch (IOException e) {
            System.out.println("Error during saving genres!" + e.getMessage());
        }
    }

    public List<Genre> loadGenresFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/genres_data.ser"))) {
            List<Genre> genres = (List<Genre>) ois.readObject();
            System.out.println("Genres were loaded successfully!");
            return genres;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during loading genres!" + e.getMessage());
            return new ArrayList<>();
        }
    }
}
