package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.LibraryController;
import org.example.models.Book;
import org.example.models.Library;
import org.example.models.Reader;
import org.example.services.LibraryService;
import org.example.utils.FileHandler;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);
        FileHandler fileHandler = new FileHandler(library);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/org/example/views/library-view.fxml"));
        Parent root = fxmlloader.load();
        System.out.println("FXML načteno: " + (fxmlloader.getLocation() != null));

        LibraryController controller = fxmlloader.getController();
        System.out.println("Library: " + library);
        System.out.println("controller: " + controller);
        controller.setLibrary(library);

        Scene scene = new Scene(root);
        stage.setTitle("Library Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        /*Reader reader = new Reader("Emil Debil", "19.4.2001", "U Prdele, Praha");

        // Testování základních operací
        libraryService.loanBook(libraryService.findBookByTitle("Farma Zvířat"), reader);
        libraryService.loanBook(libraryService.findBookByTitle("Farma Zvířat"), reader);
        libraryService.loanBook(libraryService.findBookByTitle("Pán prstenů"), reader);
        reader.getBorrowedBooks().forEach(book -> System.out.println("Knížka byla zapůjčena " + book.getTitle()));
        libraryService.returnBook(libraryService.findBookByTitle("Farma Zvířat"), reader);


        library.getBooks().forEach(book -> System.out.println(book.getTitle()));

        // Filtrování knížek podle názvu/autora
        libraryService.findBookByTitle("Farma Zvířat");
        libraryService.findBookByAuthor("George Orwell");

        // Výpis knih seřazených podle názvu
        System.out.println("Knihy seřazené podle názvu:");
        for (Book book : libraryService.sortByTitle()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }

        // Výpis knih seřazených podle autora
        System.out.println("Knihy seřazené podle autora:");
        for (Book book : libraryService.sortByAuthor()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }

        // Ukládání do souboru
        fileHandler.saveLibraryToFile();
        library.setBooks(null);

        fileHandler.loadLibraryFromFile();

        System.out.println("Načtené knihy: ");
        for (Book book : library.getBooks()) {
            System.out.println(book.getTitle() + " - " + book.getAuthor());
        }*/

    }
}