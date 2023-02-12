package by.bobrovich;

import by.bobrovich.model.Animal;
import by.bobrovich.model.Car;
import by.bobrovich.model.Flower;
import by.bobrovich.model.House;
import by.bobrovich.model.Person;
import by.bobrovich.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
        task14();
        task15();
    }

    /*
        Из представленных животных отобрать все молодые особи
        от 10 до 20 лет и отсортировать по возрасту (по возрастанию)
        далее - распределить по 7 на каждый зоопарк.
        Зоопарков неограниченное кол-во а вы - директор 3-го по счёту зоопарка.
        Полученных животных вывести в консоль.
     */
    private static void task1() throws IOException {
        int countAnimalInZoo = 7;
        List<Animal> animals = Util.getAnimals();
                animals.stream()
                        .filter(animal -> animal.getAge() >= 10)
                        .filter(animal -> animal.getAge() < 20)
                        .sorted(Comparator.comparingInt(Animal::getAge))
                        .skip(2 * countAnimalInZoo)
                        .limit(countAnimalInZoo)
                        .forEach(System.out::println);
    }

    /*
        Отобрать всех животных из Японии (Japanese) и
        записать породу UPPER_CASE в если пол Female
        преобразовать к строкам породы животных и вывести в консоль
     */
    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                .map(a -> "Female".equals(a.getGender()) ? a.getGender().toUpperCase() : a.getGender())
                .forEach(System.out::println);

    }

    //Отобрать всех животных старше 30 лет
    //и вывести все страны происхождения без дубликатов начинающиеся на "A"
    /*
        Azeri
        Aymara
        Afrikaans
        Arabic
        Armenian
        Amharic
        Assamese
        Albanian
     */
    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(a -> a.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    //Подсчитать количество всех животных пола = Female.
    //Вывести в консоль
    /*
        476
     */
    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();

        long countFemale = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count();
        System.out.println(countFemale);
    }

    //Взять всех животных возрастом 20 - 30 лет.
    //Есть ли среди нах хоть один из страны Венгрия (Hungarian)?
    //Ответ вывести в консоль
    /*
        true
     */
    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isExistFromHungarian = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));
        System.out.println(isExistFromHungarian);
    }

    //Взять всех животных.
    //Все ли они пола Male и Female ?
    //Ответ вывести в консоль
    /*
        false
     */
    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isAllMaleOrFemale = animals.stream()
                .allMatch(a -> "Male".equals(a.getGender()) || "Female".equals(a.getGender()));
        System.out.println(isAllMaleOrFemale);
    }

    //Взять всех животных.
    //Узнать что ни одно из них не имеет страну происхождения Oceania.
    //Ответ вывести в консоль
    /*
        true
     */
    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isNoneMatchOceania = animals.stream()
                .noneMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(isNoneMatchOceania);
    }

    //Взять всех животных.
    //Отсортировать их породу в стандартном порядке и
    //взять первые 100.
    //Вывести в консоль возраст самого старого животного
    /*
        48
     */
    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int maxAge = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .orElse(0);
        System.out.println(maxAge);
    }

    //Взять всех животных.
    //Преобразовать их в породы,
    //а породы в []char
    //Вывести в консоль длину самого короткого массива
    /*
        40
     */
    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int length = 0;
//        long start = System.currentTimeMillis();

//        for (int i = 0; i < 1_000_000; i++) {
            length = animals.stream()
                    .map(Animal::getBread)
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);
//                    .map(String::toCharArray)
//                    .max(Comparator.comparingInt(String::length))
//                    .orElse("")
//                    .length();
//                    .mapToInt(c -> c.length)
//                    .max()
//                    .orElse(0);
//        }
//        System.out.println(System.currentTimeMillis() - start);
        System.out.println(length);
    }

    //Взять всех животных.
    //Подсчитать суммарный возраст всех животных.
    //Вывести результат в консоль
    /*
        25329
     */
    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int sumAge = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println(sumAge);
    }

    //Взять всех животных.
    //Подсчитать средний возраст всех животных из индонезии (Indonesian).
    //Вывести результат в консоль
    /*
        25.8
     */
    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        double indonesianAvgAge = animals.stream()
                .filter(a -> "Indonesian".equals(a.getOrigin()))
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0);

        System.out.println(indonesianAvgAge);
    }

    //Во Французский легион принимают людей со всего света, но есть
    //отбор по полу (только мужчины)
    //возраст от 18 до 27 лет.
    //Преимущество отдаётся людям военной категории 1,
    //на втором месте - военная категория 2,
    //и на третьем месте военная категория 3.
    //Отсортировать всех подходящих кандидатов в порядке их
    //приоритета по военной категории.
    //Однако взять на обучение академия может только 200 человек.
    //Вывести их в консоль.
    /*
        id: 14, 44, 137, 177, 182, 274, 285, 311, 366, 396, 473, 521, 535, 555, 630, 665, 743, 888, 917, 951, 31, 130, 141, 247, 254, 263, 336, 341, 423, 435, 578, 590, 612, 617, 619, 664, 759, 807, 809, 815, 830, 842, 866, 889, 891, 902, 953, 50, 77, 120, 264, 276, 318, 432, 434, 463, 474, 504, 567, 628, 716, 739, 747, 753, 777, 885, 915, 937, 939
     */
    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        int currentYear = LocalDate.now().getYear();

        people.stream()
                .filter(p -> "Male".equals(p.getGender()))
                .filter(p -> currentYear - p.getDateOfBirth().getYear() >= 18)
                .filter(p -> currentYear - p.getDateOfBirth().getYear() < 27)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .forEach(System.out::println);
    }

    //Надвигается цунами и в районе эвакуации требуется
    //в первую очередь обойти дома и эвакуировать больных и раненых (из Hospital),
    //во вторую очередь детей и стариков (до 18 и пенсионного возраста)
    //а после всех остальных.
    //В первый этап эвакуации
    //мест в эвакуационном транспорте только 500.
    //Вывести всех людей попадающих в первый этап эвакуации в порядке приоритета (в консоль).
//    private static void task13() throws IOException {
//        final List<House> houses = Util.getHouses();
//        final LocalDate now = LocalDate.now();
//        final LocalDate years18 = now.minusYears(18);
//        final LocalDate years63 = now.minusYears(63).minusDays(1);
//        final Predicate<Person> isLessThen18 = (p) -> p.getDateOfBirth().isAfter(years18);
//        final Predicate<Person> isMoreThen63 = (p) -> p.getDateOfBirth().isBefore(years63);
//
//        houses.stream()
//                .flatMap(house -> house.getPersonList().stream()
//                                .map(p -> Map.entry("Hospital".equals(house.getBuildingType()) ? 1 :
//                                        isLessThen18.or(isMoreThen63).test(p) ? 2 : 3, p)))
//                .sorted(Map.Entry.comparingByKey())
//                .limit(500)
//                .map(Map.Entry::getValue)
//                .forEach(System.out::println);
//    }

    private static void task13() throws IOException {
        final List<House> houses = Util.getHouses();
        final LocalDate now = LocalDate.now();
        final LocalDate years18 = now.minusYears(18);
        final LocalDate malePensionYear = now.minusYears(63).plusDays(1);
        final LocalDate femalePensionYear = now.minusYears(58).plusDays(1);
        final Predicate<Person> isLessThen18 = (p) -> p.getDateOfBirth().isAfter(years18);
        final Predicate<Person> isMalePensioner = (p) -> "Male".equals(p.getGender()) && p.getDateOfBirth().isBefore(malePensionYear);
        final Predicate<Person> isFemalePension = (p) -> "Female".equals(p.getGender()) && p.getDateOfBirth().isBefore(femalePensionYear);


        houses.stream()
                .flatMap(house -> house.getPersonList().stream()
                        .map(p -> Map.entry("Hospital".equals(house.getBuildingType()) ? 1 :
                                isLessThen18.or(isMalePensioner).or(isFemalePension).test(p) ? 2 : 3, p)))
                .sorted(Map.Entry.comparingByKey())
                .limit(500)
                .map(Map.Entry::getValue)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        final Predicate<Car> turkmenistanCarPredicate = (c) -> "Jaguar".equals(c.getCarMake()) || "White".equals(c.getColor());
        final Predicate<Car> uzbekistanCarPredicate = (c) -> c.getMass() < 1500 && ("BMW".equals(c.getCarMake()) || "Lexus".equals(c.getCarMake()) || "Chrysler".equals(c.getCarMake()) || "Toyota".equals(c.getCarMake()));
        final Predicate<Car> kazahstanCarPredicate = (c) -> "GMC".equals(c.getCarMake()) || "Dodge".equals(c.getCarMake()) || ("Red".equals(c.getColor()) && c.getMass() > 4000);
        final Predicate<Car> kurgustanCarPredicate = (c) -> "Civic".equals(c.getCarModel()) || "Cherokee".equals(c.getCarModel()) || c.getReleaseYear() < 1982;
        final Predicate<Car> russianCarPredicate = (c) -> !("Yellow".equals(c.getColor()) || "Red".equals(c.getColor()) || "Green".equals(c.getColor()) || "Blue".equals(c.getColor())) || c.getPrice() > 40000;
        final Predicate<Car> mongoliaCarPredicate = (c) -> c.getVin() != null && c.getVin().contains("59");
        final BigDecimal cost = new BigDecimal("0.00714");

        final Map<String, List<Car>> cuntryMap = new LinkedHashMap<>();
        cuntryMap.put("Туркменистан", new ArrayList<>());
        cuntryMap.put("Узбекистан", new ArrayList<>());
        cuntryMap.put("Казахстан", new ArrayList<>());
        cuntryMap.put("Кыргызстан", new ArrayList<>());
        cuntryMap.put("Россия", new ArrayList<>());
        cuntryMap.put("Монголия", new ArrayList<>());

        cars.stream()
                .peek(car -> {if (turkmenistanCarPredicate.test(car)) cuntryMap.get("Туркменистан").add(car);})
                .filter(turkmenistanCarPredicate.negate())
                .peek(car -> {if (uzbekistanCarPredicate.test(car)) cuntryMap.get("Узбекистан").add(car);})
                .filter(uzbekistanCarPredicate.negate())
                .peek(car -> {if (kazahstanCarPredicate.test(car)) cuntryMap.get("Казахстан").add(car);})
                .filter(kazahstanCarPredicate.negate())
                .peek(car -> {if (kurgustanCarPredicate.test(car)) cuntryMap.get("Кыргызстан").add(car);})
                .filter(kurgustanCarPredicate.negate())
                .peek(car -> {if (russianCarPredicate.test(car)) cuntryMap.get("Россия").add(car);})
                .filter(russianCarPredicate.negate())
                .forEach(car -> {if (mongoliaCarPredicate.test(car)) cuntryMap.get("Монголия").add(car);});

        List<Integer> countriesCost = new ArrayList<>();

        int allPrice = cuntryMap.entrySet().stream()
                .peek(entry -> countriesCost.add(entry.getValue().stream()
                        .mapToInt(Car::getMass)
                        .sum()))
                .flatMap(entry -> entry.getValue().stream())
                .mapToInt(Car::getPrice)
                .sum();

        countriesCost.stream()
                .map(BigDecimal::valueOf)
                .peek(c -> System.out.println(cost.multiply(c)))
                .reduce(BigDecimal::add)
                .ifPresent(x -> System.out.println(cost.multiply(x)));

        System.out.println(allPrice);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}