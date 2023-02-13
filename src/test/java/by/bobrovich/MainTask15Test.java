package by.bobrovich;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTask15Test {
    private static final PrintStream out = System.out;

    //Name['"]:['"][D-S].*true.*((Glass)|(Aluminum)|(Steel))
    @Test
    public void task15() throws NoSuchMethodException, InvocationTargetException {
        final ByteArrayOutputStream actual = setOut();
        final Method task15 = Main.class.getDeclaredMethod("task15");
        task15.setAccessible(true);

        try {
            task15.invoke(new Object());
        }catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            System.setOut(out);
        }
        assertTrue(actual.toString().contains("315363.59988"));
    }

    private ByteArrayOutputStream setOut() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        return byteArrayOutputStream;
    }
}