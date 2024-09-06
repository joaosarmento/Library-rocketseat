package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ConversionUtils {

    public static LocalDate turnStringToDate(String date) {
        try{
            List<Integer> splitDate = Arrays.stream(date.split("/")).map(Integer::parseInt).toList();
            return LocalDate.of(splitDate.get(2), splitDate.get(1), splitDate.get(0));
        } catch (Exception e) {
            return null;
        }
    }

    public static String turnDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            return date.format(formatter);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
