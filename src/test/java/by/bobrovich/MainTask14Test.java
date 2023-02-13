package by.bobrovich;

import by.bobrovich.model.Car;
import by.bobrovich.util.Util;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTask14Test {
    MainTask14Test() throws IOException {
    }
    private static final PrintStream out = System.out;
    private final List<Car> cars = Util.getCars();
    private final Predicate<Car> turkmenistanCarPredicate = (c) -> "Jaguar".equals(c.getCarMake()) || "White".equals(c.getColor());
    private final Predicate<Car> uzbekistanCarPredicate = (c) -> c.getMass() < 1500 && ("BMW".equals(c.getCarMake()) || "Lexus".equals(c.getCarMake()) || "Chrysler".equals(c.getCarMake()) || "Toyota".equals(c.getCarMake()));
    private final Predicate<Car> kazahstanCarPredicate = (c) -> "GMC".equals(c.getCarMake()) || "Dodge".equals(c.getCarMake()) || ("Red".equals(c.getColor()) && c.getMass() > 4000);
    private final Predicate<Car> kurgustanCarPredicate = (c) -> "Civic".equals(c.getCarModel()) || "Cherokee".equals(c.getCarModel()) || c.getReleaseYear() < 1982;
    private final Predicate<Car> russianCarPredicate = (c) -> !("Yellow".equals(c.getColor()) || "Red".equals(c.getColor()) || "Green".equals(c.getColor()) || "Blue".equals(c.getColor())) || c.getPrice() > 40000;
    private final Predicate<Car> mongoliaCarPredicate = (c) -> c.getVin() != null && c.getVin().contains("59");

    @Test
    public void task14() throws IOException, NoSuchMethodException, InvocationTargetException {
        BigDecimal cost = new BigDecimal("0.00714");
        Map<String, List<Car>> countriesMap = new LinkedHashMap<>();
        countriesMap.put("Туркменистан", getTurkmenistanCarsList());
        countriesMap.put("Узбекистан", getUzbekistanCarsList());
        countriesMap.put("Казахстан", getKazahstanCarsList());
        countriesMap.put("Кыргызстан", getKurgustanCarsList());
        countriesMap.put("Россия", getRussianCarsList());
        countriesMap.put("Монголия", getMongoliaCarsList());

        ByteArrayOutputStream expectedByteArray = setOut();

        BigDecimal summaryCost = countriesMap.values().stream()
                .map(list -> list.stream()
                        .mapToInt(Car::getMass)
                        .sum())
                .map(sum -> cost.multiply(BigDecimal.valueOf(sum)))
                .peek(System.out::println)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        int allPrice = countriesMap.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(Car::getPrice)
                .sum();

        System.out.println(BigDecimal.valueOf(allPrice).subtract(summaryCost));
        String[] expected = expectedByteArray.toString().split("\n");

        //Original method

        ByteArrayOutputStream actualByteArray = setOut();

        Method task14 = Main.class.getDeclaredMethod("task14");
        task14.setAccessible(true);
        try {
            task14.invoke(new Object());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            System.setOut(out);
        }

        String actual = actualByteArray.toString();
        Arrays.stream(expected).forEach(ex -> assertTrue(actual.contains(ex)));
    }

    private List<Car> getTurkmenistanCarsList() {
        return cars.stream()
                .filter(turkmenistanCarPredicate)
                .collect(Collectors.toList());
    }

    private List<Car> getUzbekistanCarsList() {
        return cars.stream()
                .filter(turkmenistanCarPredicate.negate())
                .filter(uzbekistanCarPredicate)
                .collect(Collectors.toList());
    }
    private List<Car> getKazahstanCarsList() {
        return cars.stream()
                .filter(turkmenistanCarPredicate.negate())
                .filter(uzbekistanCarPredicate.negate())
                .filter(kazahstanCarPredicate)
                .collect(Collectors.toList());
    }
    private List<Car> getKurgustanCarsList() {
        return cars.stream()
                .filter(turkmenistanCarPredicate.negate())
                .filter(uzbekistanCarPredicate.negate())
                .filter(kazahstanCarPredicate.negate())
                .filter(kurgustanCarPredicate)
                .collect(Collectors.toList());
    }
    private List<Car> getRussianCarsList() {
        return cars.stream()
                .filter(turkmenistanCarPredicate.negate())
                .filter(uzbekistanCarPredicate.negate())
                .filter(kazahstanCarPredicate.negate())
                .filter(kurgustanCarPredicate.negate())
                .filter(russianCarPredicate)
                .collect(Collectors.toList());
    }

    private List<Car> getMongoliaCarsList() {
        return cars.stream()
                .filter(turkmenistanCarPredicate.negate())
                .filter(uzbekistanCarPredicate.negate())
                .filter(kazahstanCarPredicate.negate())
                .filter(kurgustanCarPredicate.negate())
                .filter(russianCarPredicate.negate())
                .filter(mongoliaCarPredicate)
                .collect(Collectors.toList());
    }

    private ByteArrayOutputStream setOut() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}