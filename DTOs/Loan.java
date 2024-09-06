package DTOs;

import static utils.ConversionUtils.turnDateToString;

import java.time.LocalDate;

public class Loan {

    private final Book book;
    private final User user;
    private final LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(final Book book, final User user, final LocalDate loanDate, final LocalDate returnDate) {
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    protected Loan (Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.returnDate = null;
        book.loanBook();
    }

    protected void endLoan() {
        this.returnDate = LocalDate.now();
    }

    protected void printLoan () {
        System.out.printf("Date of loan: %s. Book: %s. Client: %s. %s%n",
            loanDate.toString(), book.getTitle(), user.getName(),
            returnDate == null ? "Not yet returned." : "Return date: %s.".formatted(returnDate.toString()));
    }

    public String convertToString() {
        final String loanDate = turnDateToString(this.loanDate);
        final String returnDate = turnDateToString(this.returnDate);

        return String.format("%s;%s;%s;%s%n", book.getTitle(), user.getName(), loanDate, returnDate);
    }

    protected Book getBook() {
        return book;
    }

    public boolean isLoanActive() {
        return returnDate == null;
    }
}
