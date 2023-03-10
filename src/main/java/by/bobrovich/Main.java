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
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        task16();
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
                .peek(a -> a.setBread(a.getBread().toUpperCase()))
                .filter(a -> "Female".equals(a.getGender()))
                .map(Animal::getBread)
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

    private static void task9() throws IOException {
        final List<Animal> animals = Util.getAnimals();
        final long length = animals.stream()
                    .map(Animal::getBread)
                    .mapToLong(b -> b.chars().count())
//                    .map(String::toCharArray)
//                    .mapToInt(c -> c.length)
                    .min()
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

        people.stream()
                .filter(p -> "Male".equals(p.getGender()))
                .filter((p) -> p.getDateOfBirth().isBefore(eighteen))
                .filter((p) -> p.getDateOfBirth().isAfter(twentySeven))
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

        houses.stream()
                .flatMap(house -> house.getPersonList().stream()
                    .map(p -> Map.entry(
                        "Hospital".equals(house.getBuildingType()) ? 1 :
                            p.getDateOfBirth().isAfter(years18) ||
                            ("Male".equals(p.getGender()) && p.getDateOfBirth().isBefore(malePensionYear)) ||
                            ("Female".equals(p.getGender()) && p.getDateOfBirth().isBefore(femalePensionYear)) ? 2 :
                                3, p)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .flatMap(e -> e.getValue().stream())
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        final BigDecimal costKilogram = BigDecimal.valueOf(7.14).multiply(BigDecimal.valueOf(0.001));
        final Map<Integer, Predicate<Car>> predicates = new LinkedHashMap<>();
        predicates.put(1, (c) -> "Jaguar".equals(c.getCarMake()) ||
                                 "White".equals(c.getColor()));
        predicates.put(2, (c) -> c.getMass() < 1500 &&
                                ("BMW".equals(c.getCarMake()) ||
                                "Lexus".equals(c.getCarMake()) ||
                                "Chrysler".equals(c.getCarMake()) ||
                                "Toyota".equals(c.getCarMake())));
        predicates.put(3, (c) -> "GMC".equals(c.getCarMake()) ||
                                 "Dodge".equals(c.getCarMake()) ||
                                 ("Red".equals(c.getColor()) && c.getMass() > 4000));
        predicates.put(4, (c) -> "Civic".equals(c.getCarModel()) ||
                                 "Cherokee".equals(c.getCarModel()) ||
                                 c.getReleaseYear() < 1982);
        predicates.put(5, (c) -> !("Yellow".equals(c.getColor()) ||
                                 "Red".equals(c.getColor()) ||
                                 "Green".equals(c.getColor()) ||
                                 "Blue".equals(c.getColor())) ||
                                 c.getPrice() > 40000);
        predicates.put(6, (c) -> c.getVin() != null &&
                                 c.getVin().contains("59"));
        predicates.put(7, (car) -> true);

        cars.stream()
                .map(car -> Map.entry(predicates.entrySet().stream()
                        .filter(entry -> entry.getValue().test(car))
                        .findFirst()
                        .get().getKey(), car))
                .filter(entry -> entry.getKey() < 7)
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(carList -> Map.entry(carList,
                        costKilogram.multiply(BigDecimal.valueOf(carList.stream()
                                .mapToInt(Car::getMass)
                                .sum()))))              //???????????????????? ??1 = ???????? ????????, ??1 = ???????????????????????? ??????????????
                .peek(entry -> System.out.println(entry.getValue()))
                .map(carCost -> carCost.getKey().stream()
                        .map(car -> BigDecimal.valueOf(car.getPrice()))
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO).subtract(carCost.getValue()))
                .reduce(BigDecimal::add)
                .ifPresent(System.out::println);

    }

    private static void task15() throws IOException {
        final List<Flower> flowers = Util.getFlowers();
        final Set<String> vaseMaterialSet = Set.of("Glass", "Aluminum", "Steel");
        final LocalDate now = LocalDate.now();
        final BigDecimal costOneLitreWaterForFiveYears = Stream.of(BigDecimal.valueOf(1.39))
                .map(x -> x.multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(now, now.plusYears(5)))))
                .map(x -> x.multiply(BigDecimal.valueOf(0.001)))
                .findFirst()
                .get();

        flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed()))
                .filter(f -> !Objects.isNull(f.getCommonName()))
                .filter(f -> !f.getCommonName().isEmpty())
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

    private static void task16() throws IOException {
        List<Animal> animals = Util.getAnimals();
        List<Animal> uniqueYangAnimals = animals.stream()
                .collect(Collectors.groupingBy(Animal::getBread, Collectors.toList()))
                .values().stream()
                .map(list -> {
                    list.sort(Comparator.comparingInt(Animal::getAge));
                    return list.get(0);
                })
                .filter(a -> "Male".equals(a.getGender()) || "Female".equals(a.getGender()))
                .toList();

        List<Animal> uniquePairs = Stream.concat(uniqueYangAnimals.stream(), uniqueYangAnimals.stream()
                        .map(a -> new Animal(
                                "Male".equals(a.getGender()) ? 0 : a.getId(),
                                a.getBread(),
                                a.getAge(),
                                a.getOrigin(),
                                "Male".equals(a.getGender()) ? "Female" : "Male")
                        ).toList().stream())
                .sorted(Comparator.comparing(Animal::getBread)
                        .thenComparing(Animal::getAge).reversed())
                .toList();

        System.out.println(uniquePairs.size() / 2);

        long shipHold = animals.stream()
                .filter(uniqueYangAnimals::contains)
                .count();

        System.out.println(BigDecimal.valueOf(0.25).multiply(BigDecimal.valueOf(shipHold)));

        BigDecimal container = uniquePairs.stream()
                .map(a -> "Male".equals(a.getGender()) ? BigDecimal.valueOf(1.5) : BigDecimal.valueOf(1))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        Util.getFlowers().stream()
                .sorted(Comparator.comparing(Flower::getWaterConsumptionPerDay))
                .limit(uniquePairs.size() / 4)
                .map(a -> BigDecimal.valueOf(a.getWaterConsumptionPerDay()))
                .reduce(BigDecimal::add)
                .ifPresent(w -> System.out.println(container.add(w).multiply(BigDecimal.valueOf(1.25))));
    }
}