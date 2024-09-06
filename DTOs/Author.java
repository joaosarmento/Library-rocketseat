package DTOs;

import static utils.ConversionUtils.turnDateToString;

import java.time.LocalDate;
import java.util.Objects;

public record Author(Long id, String name, LocalDate birthDate) {

    public String convertToString() {
        final String birthDate = turnDateToString(this.birthDate);

        return String.format("%d;%s;%s%n", id, name, birthDate);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(birthDate,
            author.birthDate);
    }
}
