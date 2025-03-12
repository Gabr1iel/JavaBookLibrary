package org.example.utils;

import org.example.models.Book;
import org.example.models.Library;

import java.io.*;
import java.util.List;

public class FileHandler {
    Library library;

    public FileHandler(Library library){
        this.library = library;
    }

    public void saveLibraryToFile() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir(); // Vytvoří složku data pokud neexistuje
        }
        if (library.getBooks() == null || library.getBooks().isEmpty()) {
            System.out.println("Knihovna je prázdná!");
            return;
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
