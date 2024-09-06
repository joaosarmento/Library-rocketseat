package DTOs;

import static DAO.LibraryDAO.saveLibraryData;
import static utils.ConversionUtils.turnStringToDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Library {

    private final List<Author> authors;
    private final List<Book> books;
    private final List<User> users;
    private final List<Loan> loans;

    private Long bookCont;
    private Long authorCount;
    private Long userCont;

    public Library(final List<Author> authors, final List<Book> books, final List<User> users, final List<Loan> loans) {
        this.authors = authors;
        this.books = books;
        this.users = users;
        this.loans = loans;
        authorCount = (long) authors.size() + 1;
        bookCont = (long) books.size() + 1;
        userCont = (long) users.size() + 1;

    }

    public User logIn(Scanner scanner) {
        User user = null;

        while (user == null) {
            System.out.println("Insert your id number:");
            Long userId = scanner.nextLong();
            scanner.nextLine();
            try {
                user = findUser(userId);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }

        System.out.println("Insert your password:");
        String password = scanner.nextLine();

        if (verifyPassword(user, password)) {
            System.out.println("Log-in complete.");
            return user;
        }

        System.err.printf("Wrong password for this user %d.", user.getId());
        return null;
    }

    public User registerNewUser(Scanner scanner) {
        System.out.println("Insert your name:");
        String userName = scanner.nextLine();
        System.out.println("Insert your password:");
        String userPass = scanner.nextLine();
        System.out.println("Insert your birth date:");
        String userBirthDate = scanner.nextLine();
        System.out.println("Insert your email:");
        String email = scanner.nextLine();

        LocalDate birthDate = turnStringToDate(userBirthDate);

        final User newUser = new User(userCont, userName, userPass, birthDate, email, false);
        users.add(newUser);

        System.out.printf("User successfully registered! userId: %d.", userCont++);
        return newUser;
    }

    public void loanBook(Scanner scanner, User user) {
        printAvailableBookList();
        System.out.println("Do you want to loan a book?");
        if(scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Insert the id for the book you want");
            Long bookId = scanner.nextLong();
            scanner.nextLine();
            newLoan(bookId, user);
        }
    }

    public void addBook(Scanner scanner) {
        System.out.println("\nInsert book infos:");
        System.out.print("Title:");
        String title = scanner.nextLine();
        System.out.print("Author:");
        String authorName = scanner.nextLine();
        Author author = null;
        try {
            author = findAuthor(authorName);

        } catch (RuntimeException ex) {
            author = addAuthor(scanner, authorName);

        } finally {
            books.add(new Book(bookCont++, title, author));
            System.out.println("\nBook added!");
        }

    }

    private Author addAuthor(Scanner scanner, String authorName) {
        System.out.print("Author still doesn't exist, please insert their birth date:");
        String birthDate = scanner.nextLine();

        Author author = new Author(authorCount++, authorName, turnStringToDate(birthDate));
        authors.add(author);
        System.out.println("Author registered!");
        return author;
    }

    public void printSelectedUserLoanHistory(Scanner scanner) {
        System.out.println("\nUser List:");
        users.forEach(User::printUser);
        System.out.println("\nWhich user do you want to see the history?");
        final Long userId = scanner.nextLong();
        scanner.nextLine();

        try{
            final User user = findUser(userId);
            user.printUserLoans();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printBookLoanHistory(Scanner scanner) {
        System.out.println("\nBook List:");
        books.forEach(Book::printBook);
        System.out.println("\nWhich book do you want to see the history?");
        final Long bookId = scanner.nextLong();
        scanner.nextLine();

        try{
            final Book book = findBook(bookId);
            loans.stream().filter(loan -> loan.getBook().equals(book)).forEach(Loan::printLoan);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printBookList() {
        System.out.println("Library books:");
        books.forEach(Book::printBook);
        System.out.println();
    }

    public void printAvailableBookList() {
        System.out.println("\nAvailable Books:");
        books.stream().filter(Book::isAvailable).forEach(Book::printBook);
        System.out.println();
    }

    public void newLoan(Long bookId, User user) {
        try {
            Book book = findBook(bookId);
            final Loan newLoan = new Loan(book, user);
            user.getLoans().add(newLoan);
            loans.add(newLoan);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void returnBook(Scanner scanner, User user) {
        try {
            user.printUserLoans();

            System.out.println("Insert the id for the book you're returning");
            Long bookId = scanner.nextLong();
            scanner.nextLine();
            Loan loan = user.findActiveLoan(bookId);

            if (loan != null){
                findBook(bookId).returnBook(loan);
                System.out.println("Book returned!");
            } else {
                System.out.println("You don't have a active loan with this book");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveData() {
        saveLibraryData(authors, books, users, loans);
    }

    private Book findBook(Long bookId) {
        Optional<Book> selectedBook = books.stream().filter(book -> book.getId().equals(bookId)).findFirst();
        if (selectedBook.isEmpty()) throw new RuntimeException("Incorrect id, book not found.");

        return selectedBook.get();
    }

    private User findUser(Long userId) {
        Optional<User> selectedUser = users.stream().filter(user -> user.getId().equals(userId)).findFirst();
        if (selectedUser.isEmpty()) throw new RuntimeException("Incorrect id, user not found.");

        return selectedUser.get();
    }

    private Author findAuthor(String name) {
        Optional<Author> selectedAuthor = authors.stream().filter(author -> author.name().equals(name)).findFirst();
        if (selectedAuthor.isEmpty()) throw new RuntimeException("Incorrect id, author not found.");

        return selectedAuthor.get();
    }

    private boolean verifyPassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
