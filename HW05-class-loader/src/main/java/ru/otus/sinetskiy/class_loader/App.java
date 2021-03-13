package ru.otus.sinetskiy.class_loader;

/**
 * @author Andrei Sinetskii
 */
public class App {
    public static void main(String... args) {

        TestLoggingInterface testLogging = Ioc.createTestLogging();

        testLogging.calculation(6);
        testLogging.calculation(6,6);
        testLogging.calculation(6,6,6);
    }
}
