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

    //Надвигается цунами и в районе эвакуации требуется
    //в первую очередь обойти дома и эвакуировать больных и раненых (из Hospital),
    //во вторую очередь детей и стариков (до 18 и пенсионного возраста)
    //а после всех остальных.
    //В первый этап эвакуации
    //мест в эвакуационном транспорте только 500.
    //Вывести всех людей попадающих в первый этап эвакуации в порядке приоритета (в консоль).
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

        List<Person> hospitalPeople = houses.stream()
                .filter(h -> "Hospital".equals(h.getBuildingType()))
                .flatMap(h -> h.getPersonList().stream())
                .collect(Collectors.toList());

        List<Person> yangAndOldPeople = houses.stream()
                .filter(h -> !"Hospital".equals(h.getBuildingType()))
                .flatMap(h -> h.getPersonList().stream())
                .filter(isLessThen18.or(isMalePensioner).or(isFemalePension))
                .collect(Collectors.toList());

        List<Person> otherPeople = houses.stream()
                .flatMap(house -> house.getPersonList().stream())
                .filter(p -> !hospitalPeople.contains(p))
                .filter(p -> !yangAndOldPeople.contains(p))
                .collect(Collectors.toList());

        ByteArrayOutputStream expected = setOut();

        Stream.of(hospitalPeople, yangAndOldPeople, otherPeople)
                .flatMap(Collection::stream)
                .limit(500)
                .forEach(System.out::println);

        ByteArrayOutputStream actual = setOut();

        Method task13 = Main.class.getDeclaredMethod("task13");
        task13.setAccessible(true);
        task13.invoke(new Object());

        assertEquals(expected.toString(), actual.toString());

        System.setOut(out);
    }

    private ByteArrayOutputStream setOut() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}