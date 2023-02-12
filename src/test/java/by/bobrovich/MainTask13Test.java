package by.bobrovich;

import by.bobrovich.model.House;
import by.bobrovich.model.Person;
import by.bobrovich.util.Util;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTask13Test {
    private static final PrintStream out = System.out;

    @Test
    public void task13() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final List<House> houses = Util.getHouses();
        final LocalDate now = LocalDate.now();
        final LocalDate years18 = now.minusYears(18);
        final LocalDate malePensionYear = now.minusYears(63).plusDays(1);
        final LocalDate femalePensionYear = now.minusYears(58).plusDays(1);
        final Predicate<Person> isLessThen18 = (p) -> p.getDateOfBirth().isAfter(years18);
        final Predicate<Person> isMalePensioner = (p) -> "Male".equals(p.getGender()) && p.getDateOfBirth().isBefore(malePensionYear);
        final Predicate<Person> isFemalePension = (p) -> "Female".equals(p.getGender()) && p.getDateOfBirth().isBefore(femalePensionYear);

        final List<Person> hospitalPeople = houses.stream()
                .filter(h -> "Hospital".equals(h.getBuildingType()))
                .flatMap(h -> h.getPersonList().stream())
                .collect(Collectors.toList());

        final List<Person> yangAndOldPeople = houses.stream()
                .filter(h -> !"Hospital".equals(h.getBuildingType()))
                .flatMap(h -> h.getPersonList().stream())
                .filter(isLessThen18.or(isMalePensioner).or(isFemalePension))
                .collect(Collectors.toList());

        final List<Person> otherPeople = houses.stream()
                .flatMap(house -> house.getPersonList().stream())
                .filter(p -> !hospitalPeople.contains(p))
                .filter(p -> !yangAndOldPeople.contains(p))
                .collect(Collectors.toList());

        final ByteArrayOutputStream expected = setOut();

        Stream.of(hospitalPeople, yangAndOldPeople, otherPeople)
                .flatMap(Collection::stream)
                .limit(500)
                .forEach(System.out::println);

        final ByteArrayOutputStream actual = setOut();
        final Method task13 = Main.class.getDeclaredMethod("task13");
        task13.setAccessible(true);

        try {
            task13.invoke(new Object());
        }catch (Throwable e) {
            throw new RuntimeException(e);
        }finally{
            System.setOut(out);
        }

        assertEquals(expected.toString(), actual.toString());
    }

    private ByteArrayOutputStream setOut() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}