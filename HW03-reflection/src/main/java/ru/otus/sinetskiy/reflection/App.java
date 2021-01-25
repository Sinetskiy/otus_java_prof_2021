package ru.otus.sinetskiy.reflection;

/**
 * @author Andrei Sinetskii
 */
public class App {
    public static void main(String... args) {

        TestRunner runner = new TestRunner<>(AppTest.class);
        runner.run();
    }
}
