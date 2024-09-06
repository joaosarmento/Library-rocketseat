package DAO;

import static utils.ConversionUtils.turnStringToDate;
import static utils.DataUtils.saveData;

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

public class UserDAO {

    final Path userFile = Paths.get("data/UserList.txt");

    public List<User> getUsers() {
        try{
            List<String> fileContent = Files.readAllLines(userFile);

            return !fileContent.isEmpty() ?
                fileContent.stream().map(this::buildUser).toList():
                new ArrayList<>();

        } catch (IOException e) {
            throw new RuntimeException("Error fetching users data.");
        }
    }

    public void saveUsers(List<User> users) {
        List<String> usersInfos = users.stream().map(User::convertToString).toList();
        saveData(userFile, usersInfos);
    }

    private User buildUser(String user) {
        List<String> data = Arrays.stream(user.split(";")).toList();
        final Long id = Long.parseLong(data.get(0));
        final String name = data.get(1);
        final String password = data.get(2);
        final LocalDate birthDate = turnStringToDate(data.get(3));
        final String email = data.get(4);
        final List<Loan> loans = new ArrayList<>();
        boolean isEmployee = data.get(5).equals("true");

        return new User(id, password, name, birthDate, email, loans, isEmployee);
    }

}
