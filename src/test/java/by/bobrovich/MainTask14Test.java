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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /*
        221.91834
        107.63550
        2255.96868
        531.29454
        14922.70710
        120.80880
        ALL: 18160.33296
     */
    //Из перечня автомобилей приходящих на рынок Азии логистическому агентству предстоит
    // отсортировать их в порядке следования
    // 1.Туркменистан -> Все автомобили марки "Jaguar" а так же все авто цвета White идут в первую страну
    // 2.Узбекистан -> Из оставшихся все автомобили с массой до 1500 кг и марок BMW, Lexus, Chrysler и Toyota идут во второй эшелон
    // 3.Казахстан -> Из оставшихся все автомобили Черного цвета с массой более 4000 кг и все GMC и Dodge идут в третий эшелон
    // 4.Кыргызстан -> Из оставшихся все автомобили года выпуска до 1982 или все модели "Civic" и "Cherokee" идут в четвёртый эшелон
    // 5.Россия -> Из оставшихся все автомобили цветов НЕ Yellow, Red, Green и Blue или же по стоимости дороже 40000 в пятый эшелон
    // 6.Монголия -> Из оставшиеся все автомобили в vin номере которых есть цифра "59" идут в последний шестой эшелон
    //Оставшиеся автомобили отбрасываем, они никуда не идут.
    //Измерить суммарные массы автомобилей всех эшелонов для каждой из стран и
    //подсчитать сколько для каждой страны будет стоить транспортные расходы если учесть что
    // на 1 тонну транспорта будет потрачено 7.14 $.
    //Вывести суммарные стоимости в консоль. Вывести общую выручку логистической кампании.
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

        ByteArrayOutputStream expected = setOut();

        BigDecimal summaryCost = countriesMap.values().stream()
                .map(list -> list.stream()
                        .mapToInt(Car::getMass)
                        .sum())
                .map(sum -> cost.multiply(BigDecimal.valueOf(sum)))
                .peek(System.out::println)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        System.out.println(summaryCost);

        int allPrice = countriesMap.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(Car::getPrice)
                .sum();

        System.out.println(allPrice);

        //Original method

        ByteArrayOutputStream actual = setOut();

        Method task14 = Main.class.getDeclaredMethod("task14");
        task14.setAccessible(true);
        try {
            task14.invoke(new Object());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            System.setOut(out);
        }

        assertEquals(expected.toString(), actual.toString());
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