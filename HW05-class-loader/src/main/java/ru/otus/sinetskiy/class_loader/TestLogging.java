package ru.otus.sinetskiy.class_loader;

public class TestLogging implements TestLoggingInterface {


    @Override
    public void calculation(int param) {
        var sum = param + 1;
    }

    @Override
    public void calculation(int param1, int param2) {
        var sum = param1 + param2 + 1;
    }

    @Override
    public void calculation(int param1, int param2, int param3) {
        var sum = param1 + param2 + param3 + 1;
    }
}
