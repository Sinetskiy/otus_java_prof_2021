package ru.otus.sinetskiy.class_loader;

import ru.otus.sinetskiy.class_loader.annotations.Log;

public interface TestLoggingInterface {

    @Log
    void calculation(int param);

    void calculation(int param1, int param2);

    @Log
    void calculation(int param1, int param2, int param3);
}
