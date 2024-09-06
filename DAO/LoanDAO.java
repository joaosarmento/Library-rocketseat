package DAO;

import static utils.ConversionUtils.turnStringToDate;
import static utils.DataUtils.saveData;

import DTOs.Book;
import DTOs.Loan;
import DTOs.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoanDAO {

    final Path loanFile = Paths.get("data/LoanList.txt");

    public List<Loan> getLoans(List<Book> books, List<User> users) {
        try{
            List<String> fileContent = Files.readAllLines(loanFile);

            return !fileContent.isEmpty() ?
                fileContent.stream().map(loan -> buildLoan(loan, books, users)).toList() :
                new ArrayList<>();

        } catch (IOException e) {
            throw new RuntimeException("Error fetching users data.");
        }
    }

    public void saveLoans(List<Loan> loans) {
        List<String> loansInfos = loans.stream().map(Loan::convertToString).toList();

        saveData(loanFile, loansInfos);
    }

    private Loan buildLoan(String loan, List<Book> books, List<User> users) {
        List<String> data = Arrays.stream(loan.split(";")).toList();
        final Book book = findBook(data.get(0), books);
        final User user = findUser(data.get(1), users);
        final LocalDate loanDate = turnStringToDate(data.get(2));
        final LocalDate returnDate = turnStringToDate(data.get(3));

        final Loan newLoan = new Loan(book, user, loanDate, returnDate);
        user.getLoans().add(newLoan);
        return newLoan;
    }

    private Book findBook(String title, List<Book> books) {
        return books.stream().filter(book -> book.getTitle().equals(title)).findFirst().orElse(null);
    }

    private User findUser(String name, List<User> users) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

}
