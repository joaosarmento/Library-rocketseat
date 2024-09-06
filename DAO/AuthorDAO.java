package DAO;

import static utils.ConversionUtils.turnStringToDate;
import static utils.DataUtils.saveData;

import DTOs.Author;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorDAO {

    final Path authorFile = Paths.get("data/AuthorList.txt");

    public List<Author> getAuthors() {
        try{
            List<String> fileContent = Files.readAllLines(authorFile);

            return !fileContent.isEmpty() ?
                fileContent.stream().map(this::buildAuthor).toList():
                new ArrayList<>();

        } catch (IOException e) {
            throw new RuntimeException("Error fetching authors data.");
        }
    }

    public void saveAuthors(List<Author> authors) {
        List<String> authorsInfos = authors.stream().map(Author::convertToString).toList();
        saveData(authorFile, authorsInfos);
    }

    private Author buildAuthor(String author) {
        List<String> data = Arrays.stream(author.split(";")).toList();
        final Long id = Long.parseLong(data.get(0));
        final String name = data.get(1);
        final LocalDate birthDate = turnStringToDate(data.get(2));

        return new Author(id, name, birthDate);
    }

}
