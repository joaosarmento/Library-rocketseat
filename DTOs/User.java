package DTOs;

import static utils.ConversionUtils.turnDateToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User {

    private final Long id;
    private final String password;
    private final String name;
    private final LocalDate birthDate;
    private final String email;
    private final List<Loan> loans;
    private final boolean isEmployee;

    public User(final Long id, final String password, final String name, final LocalDate birthDate, final String email,
        final List<Loan> loans, final boolean isEmployee) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
        this.loans = loans;
        this.isEmployee = isEmployee;
    }

    public User(Long id, String name, String password, LocalDate birthDate, String email, boolean isEmployee) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
        this.loans = new ArrayList<>();
        this.isEmployee = isEmployee;
    }

    protected Loan findActiveLoan(Long bookId) {
        Optional<Loan> activeLoan = getLoans().stream()
            .filter(loan -> loan.getBook().getId().equals(bookId) && loan.isLoanActive())
            .findFirst();

        return activeLoan.orElse(null);
    }

    public void printUser() {
        System.out.printf("Id: %s. Name: %s.%n", id, name);
    }

    public void printUserLoans() {
        System.out.printf("%n%s's loan history: %n", name);
        loans.forEach(Loan::printLoan);
    }

    public String convertToString() {
        final String birthDate = turnDateToString(this.birthDate);

        return String.format("%d;%s;%s;%s;%s;%b%n", id, name, password, birthDate, email, isEmployee);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public boolean isEmployee() {
        return isEmployee;
    }
}
