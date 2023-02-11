package by.bobrovich;

import by.bobrovich.model.Car;
import by.bobrovich.util.Util;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    //подсчитать сколько для каждой страны
    //будет стоить транспортные расходы если учесть что на 1 тонну транспорта будет потрачено 7.14 $.
    //Вывести суммарные стоимости в консоль. Вывести общую выручку логистической кампании.
    @Test
    public void task14() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Car> turkmenistanCarsList = getTurkmenistanCarsList();
        List<Car> uzbekistanCarsList = getUzbekistanCarsList();
        List<Car> kazahstanCarsList = getKazahstanCarsList();
        List<Car> kurgustanCarsList = getKurgustanCarsList();
        List<Car> russianCarsList = getRussianCarsList();
        List<Car> mongoliaCarsList = getMongoliaCarsList();


//        cars.stream().map(Car::getColor).distinct().forEach(System.out::println);










//
//        ByteArrayOutputStream expected = setOut();
//
//        ByteArrayOutputStream actual = setOut();
//
//        Method task13 = Main.class.getDeclaredMethod("task14");
//        task13.setAccessible(true);
//        task13.invoke(new Object());
//
//        assertEquals(expected.toString(), actual.toString());
//
//        System.setOut(out);
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
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    private ByteArrayOutputStream setOut() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}