package DAO;

import static utils.ConversionUtils.turnStringToDate;
import static utils.DataUtils.saveData;

import DTOs.Author;
import DTOs.Book;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDAO {

    final Path bookFile = Paths.get("data/BookList.txt");

    public List<Book> getBooks(List<Author> authors) {
        try{
            List<String> fileContent = Files.readAllLines(bookFile);

            return !fileContent.isEmpty() ?
                fileContent.stream().map(book -> buildBook(book, authors)).toList():
                new ArrayList<>();

        } catch (IOException e) {
            throw new RuntimeException("Error fetching books data.");
        }
    }

    public void saveBooks(List<Book> books) {
        List<String> booksInfos = books.stream().map(Book::convertToString).toList();

        saveData(bookFile, booksInfos);
    }

    private Book buildBook(String book, List<Author> authors) {
        List<String> data = Arrays.stream(book.split(";")).toList();
        final Long id = Long.parseLong(data.get(0));
        final String title = data.get(1);
        final Author author = findAuthor(data.get(2), authors);
        final boolean available = data.get(3).equals("true");
        final LocalDate registrationDate = turnStringToDate(data.get(4));
        final LocalDate lastUpdateDate = turnStringToDate(data.get(5));

        return new Book(id, title, author, available, registrationDate, lastUpdateDate);
    }

    private Author findAuthor(String name, List<Author> authors) {
        return authors.stream().filter(author -> author.name().equals(name)).findFirst().orElse(null);
    }

}
