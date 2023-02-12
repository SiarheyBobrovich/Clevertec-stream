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
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Comparator;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        final int countAnimalInZoo = 7;
        final List<Animal> animals = Util.getAnimals();
                animals.stream()
                        .filter(animal -> animal.getAge() >= 10)
                        .filter(animal -> animal.getAge() < 20)
                        .sorted(Comparator.comparingInt(Animal::getAge))
                        .skip(2 * countAnimalInZoo)
                        .limit(countAnimalInZoo)
                        .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                .map(a -> "Female".equals(a.getGender()) ? a.getGender().toUpperCase() : a.getGender())
                .forEach(System.out::println);

    }

    private static void task3() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(a -> a.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final long countFemale = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count();
        System.out.println(countFemale);
    }

    private static void task5() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final boolean isExistFromHungarian = animals.stream()
                .filter(a -> a.getAge() >= 20)
                .filter(a -> a.getAge() <= 30)
                .peek(System.out::println)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));
        System.out.println(isExistFromHungarian);
    }

    private static void task6() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final boolean isAllMaleOrFemale = animals.stream()
                .allMatch(a -> "Male".equals(a.getGender()) || "Female".equals(a.getGender()));
        System.out.println(isAllMaleOrFemale);
    }

    private static void task7() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final boolean isNoneMatchOceania = animals.stream()
                .noneMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(isNoneMatchOceania);
    }

    private static void task8() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final int maxAge = animals.stream()
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
        final List<Animal> animals = Util.getAnimals();
        final long length = animals.stream()
                    .map(Animal::getBread)
                    .mapToLong(b -> b.chars().count())
//                    .map(String::toCharArray)
//                    .mapToInt(c -> c.length)
                    .max()
                    .orElse(0);
        System.out.println(length);
    }

    private static void task10() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final long sumAge = animals.stream()
                .mapToInt(Animal::getAge)
                .summaryStatistics()
                .getSum();
        System.out.println(sumAge);
    }

    //Взять всех животных.
    //Подсчитать средний возраст всех животных из индонезии (Indonesian).
    //Вывести результат в консоль
    /*
        25.8
     */
    private static void task11() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final double indonesianAvgAge = animals.stream()
                .filter(a -> "Indonesian".equals(a.getOrigin()))
                .mapToInt(Animal::getAge)
                .summaryStatistics()
                .getAverage();
        System.out.println(indonesianAvgAge);
    }

    private static void task12() throws IOException {
        final List<Person> people = Util.getPersons();
        final LocalDate now = LocalDate.now();
        final LocalDate eighteen = now.minusYears(18).plusDays(1);
        final LocalDate twentySeven = now.minusYears(27);

        final Predicate<Person> is18AndMore = (p) -> p.getDateOfBirth().isBefore(eighteen);
        final Predicate<Person> isMoreThen27 = (p) -> p.getDateOfBirth().isAfter(twentySeven);

        people.stream()
                .filter(p -> "Male".equals(p.getGender()))
                .filter(is18AndMore)
                .filter(isMoreThen27)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        final List<House> houses = Util.getHouses();
        final LocalDate now = LocalDate.now();
        final LocalDate years18 = now.minusYears(18);
        final LocalDate malePensionYear = now.minusYears(63).plusDays(1);
        final LocalDate femalePensionYear = now.minusYears(58).plusDays(1);

        final Predicate<Person> isLessThen18 = (p) -> p.getDateOfBirth().isAfter(years18);
        final Predicate<Person> isMalePensioner = (p) -> "Male".equals(p.getGender()) && p.getDateOfBirth().isBefore(malePensionYear);
        final Predicate<Person> isFemalePensioner = (p) -> "Female".equals(p.getGender()) && p.getDateOfBirth().isBefore(femalePensionYear);
        final Predicate<House> isFirstCategory = (h) -> "Hospital".equals(h.getBuildingType());
        final Predicate<Person> isSecondCategory = isLessThen18.or(isMalePensioner).or(isFemalePensioner);

        houses.stream()
                .flatMap(house -> house.getPersonList().stream()
                        .map(p -> Map.entry(isFirstCategory.test(house) ? 1 : isSecondCategory.test(p) ? 2 : 3, p)))
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
        final BigDecimal cost = new BigDecimal("7.14");

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
                .map(b -> b.multiply(BigDecimal.valueOf(0.001)))
                .peek(c -> System.out.println(cost.multiply(c)))
                .reduce(BigDecimal::add)
                .ifPresent(x -> System.out.println(cost.multiply(x)));

        System.out.println(allPrice);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();

        final Set<String> vaseMaterialSet = Set.of("Glass", "Aluminum", "Steel");
        final LocalDate now = LocalDate.now();
        final long daysInFiveYears = now.plusYears(5).toEpochDay() - now.toEpochDay();
        final BigDecimal cubeInLitre = new BigDecimal("0.001");
        final BigDecimal waterCostByLitre = BigDecimal.valueOf(1.39).multiply(cubeInLitre);
        final BigDecimal costOneLitreWaterForFiveYears = waterCostByLitre.multiply(BigDecimal.valueOf(daysInFiveYears));

        flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed()))
                .filter(f -> f.getCommonName().charAt(0) <= 'S')
                .filter(f -> f.getCommonName().charAt(0) > 'C')
                .filter(Flower::isShadePreferred)
                .filter(f -> f.getFlowerVaseMaterial().stream()
                        .anyMatch(vaseMaterialSet::contains))
                .map(f -> BigDecimal.valueOf(f.getWaterConsumptionPerDay())
                        .multiply(costOneLitreWaterForFiveYears)
                        .add(BigDecimal.valueOf(f.getPrice())))
                .reduce(BigDecimal::add)
                .ifPresent(System.out::println);
    }
}