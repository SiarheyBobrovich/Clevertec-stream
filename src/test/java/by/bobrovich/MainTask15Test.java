package by.bobrovich;

import by.bobrovich.model.Flower;
import by.bobrovich.util.Util;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

class MainTask15Test {
    MainTask15Test() throws IOException {
    }
    private static final PrintStream out = System.out;
    private final List<Flower> flowers = Util.getFlowers();

    //Для оранжереи нужно подобрать растения соответствующие требованиям.
    //Во-первых, нужно произвести сложную сортировку каталога растений.
    //Отсортировать их по странам происхождения в обратном порядке
    //После по стоимости
    //и еще по водопотреблению в обратном порядке.
    //Из этого списка взять растения название которых от буквы "S" до буквы "C".
    //Если растения тенелюбивые и им подходит горшок из стекла, алюминия или стали - то выбираем их.
    //Далее на каждое растение надо рассчитать:
    //стоимость растения + стоимость потребления воды за 5 лет c учётом того что кубометр воды стоит 1.39 $.
    //Суммировать общую стоимость обслуживания всех растений.
    //Во сколько это обойдётся бюджету?
    @Test
    public void task15() throws NoSuchMethodException, InvocationTargetException {
        Predicate<Flower> flowerNamePredicate = (f) -> f.getCommonName().charAt(0) > 'C' && f.getCommonName().charAt(0) <= 'S';
        Predicate<Flower> vaseMaterialPredicate = (f) -> f.getFlowerVaseMaterial().containsAll(List.of("Glass", "Aluminum", "Steel"));
        LocalDate now = LocalDate.now();
        long days = now.plusYears(5).toEpochDay() - now.toEpochDay();

        BigDecimal waterCost = BigDecimal.valueOf(1.39).multiply(BigDecimal.valueOf(days));
        Function<Flower, BigDecimal> function = (f) -> waterCost.multiply(BigDecimal.valueOf(f.getWaterConsumptionPerDay()).add(BigDecimal.valueOf(f.getPrice())));

        flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed()))
                .filter(flowerNamePredicate)
                .filter(Flower::isShadePreferred)
                .filter(vaseMaterialPredicate)
                .map(function)
                .reduce(BigDecimal::add)
                .ifPresent(System.out::println);


        //Original method
//
//        ByteArrayOutputStream actual = setOut();
//
//        Method task14 = Main.class.getDeclaredMethod("task14");
//        task14.setAccessible(true);
//        try {
//            task14.invoke(new Object());
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }finally {
//            System.setOut(out);
//        }
//
//        assertEquals(expected.toString(), actual.toString());
    }

    private ByteArrayOutputStream setOut() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}