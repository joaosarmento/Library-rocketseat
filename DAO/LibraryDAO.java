package DAO;

import DTOs.Author;
import DTOs.Book;
import DTOs.Library;
import DTOs.Loan;
import DTOs.User;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {

    private static final AuthorDAO authorDAO = new AuthorDAO();
    private static final BookDAO bookDAO = new BookDAO();
    private static final UserDAO userDAO = new UserDAO();
    private static final LoanDAO loanDAO = new LoanDAO();

    private LibraryDAO() {}

    public static Library fetchLibraryData() {
        List<Author> authors = new ArrayList<>(authorDAO.getAuthors());
        List<Book> books = new ArrayList<>(bookDAO.getBooks(authors));
        List<User> users = new ArrayList<>(userDAO.getUsers());
        List<Loan> loans = new ArrayList<>(loanDAO.getLoans(books, users));

        return new Library(authors, books, users, loans);
    }

    public static void saveLibraryData(List<Author> authors, List<Book> books, List<User> users, List<Loan> loans) {
        authorDAO.saveAuthors(authors);
        bookDAO.saveBooks(books);
        userDAO.saveUsers(users);
        loanDAO.saveLoans(loans);
    }

}
