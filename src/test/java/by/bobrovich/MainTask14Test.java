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
    private static final PrintStream out = System.out;
    @Test
    public void task14() throws NoSuchMethodException{
        List<String> expected = List.of("221", "107", "2255", "531", "14922", "120", "29106864");

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
        expected.forEach(ex -> assertTrue(actual.contains(ex)));
    }

    private ByteArrayOutputStream setOut() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}