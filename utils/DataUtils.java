package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataUtils {

    private DataUtils() {}

    public static void saveData(Path path, List<String> listToSave) {
        try {
            Files.writeString(path, "");
            StringBuilder stringBuilder = new StringBuilder();
            listToSave.forEach(stringBuilder::append);
            writeUser(path, stringBuilder.toString());

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void writeUser(Path path, String infosToSave) {
        try {
            Files.writeString(path, infosToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
