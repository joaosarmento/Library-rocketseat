import static DAO.LibraryDAO.fetchLibraryData;

import DTOs.Library;
import DTOs.User;
import java.util.Scanner;

public class LibraryApplication {

    public static void main(String[] args) {
        Library library = fetchLibraryData();
        Scanner scanner = new Scanner(System.in);
        User user = null;
        String answer;

        while (user == null) {
            answer = initialMenu(scanner);

            switch (answer) {
                case "1" -> user = library.logIn(scanner);
                case "2" -> user = library.registerNewUser(scanner);
                default -> System.out.println("Non-existent option.");
            }
        }

        do {
            if (!user.isEmployee()) {
                answer = clientMenu(scanner, user);

                switch (answer) {
                    case "0" -> answer = "exit";
                    case "1" -> library.loanBook(scanner, user);
                    case "2" -> library.returnBook(scanner, user);
                    case "3" -> user.printUserLoans();
                    default -> System.out.println("Non-existent option.");
                }

            } else {
                answer = employeeMenu(scanner, user);

                switch (answer) {
                    case "0" -> answer = "exit";
                    case "1" -> library.addBook(scanner);
                    case "2" -> library.printBookList();
                    case "3" -> library.printSelectedUserLoanHistory(scanner);
                    case "4" -> library.printBookLoanHistory(scanner);
                    default -> System.out.println("Non-existent option.");
                }
            }

        } while (!answer.equals("exit"));

        library.saveData();
        System.out.println("System closed");
        scanner.close();
    }

    private static String initialMenu(Scanner scanner) {
        System.out.println("\nWelcome! Select the option that suits you:");
        System.out.println("1. Log-in");
        System.out.println("2. Register");
        return scanner.nextLine();
    }

    private static String clientMenu(Scanner scanner, User user) {
        System.out.printf("%nWelcome, %s! Select an option:%n", user.getName());
        System.out.println("1. Check available books list");
        System.out.println("2. Return book");
        System.out.println("3. Check my loan history");
        System.out.println("0 - Exit");
        return scanner.nextLine();
    }

    private static String employeeMenu(Scanner scanner, User user) {
        System.out.printf("%nWelcome, %s! Select an option:%n", user.getName());
        System.out.println("1. Add book");
        System.out.println("2. Check books list");
        System.out.println("3. Check user loan history");
        System.out.println("4. Check book loan history");
        System.out.println("0 - Exit");
        return scanner.nextLine();
    }

}
