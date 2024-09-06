package DTOs;

import static utils.ConversionUtils.turnDateToString;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private final Long id;
    private final String title;
    private final Author author;
    private boolean available;
    private final LocalDate registrationDate;
    private LocalDate lastUpdateDate;

    public Book(final Long id, final String title, final Author author, final boolean available, final LocalDate registrationDate,
        final LocalDate lastUpdateDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
        this.registrationDate = registrationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Book(final Long id, final String title, final Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
        this.registrationDate = LocalDate.now();
        this.lastUpdateDate = registrationDate;
    }

    protected void printBook() {
        String bookLine = "id: %d. BookName: %s. Author: %s. **%s".formatted(
            id, title, author.name(), available ? "Disponível" : "Indisponível"
        );

        System.out.println(bookLine);
    }

    protected void loanBook() {
        this.available = false;
        this.lastUpdateDate = LocalDate.now();
        System.out.println("\nLoan successfully registered.");
    }

    protected void returnBook(Loan loan) {
        this.available = true;
        loan.endLoan();
        this.lastUpdateDate = LocalDate.now();
        System.out.println("Book successfully returned.");
    }

    public String convertToString() {
        final String registrationDate = turnDateToString(this.registrationDate);
        final String lastUpdateDate = turnDateToString(this.lastUpdateDate);

        return String.format("%d;%s;%s;%b;%s;%s%n", id, title, author.name(), available, registrationDate, lastUpdateDate);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Book book = (Book) o;
        return available == book.available && Objects.equals(id, book.id) && Objects.equals(title, book.title)
            && Objects.equals(author, book.author) && Objects.equals(registrationDate, book.registrationDate)
            && Objects.equals(lastUpdateDate, book.lastUpdateDate);
    }
}
