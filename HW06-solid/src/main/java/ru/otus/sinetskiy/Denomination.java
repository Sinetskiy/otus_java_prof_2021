package ru.otus.sinetskiy;

/**
 * @author Andrei Sinetskii
 */
public enum Denomination {
    D_5000(5000),
    D_2000(2000),
    D_1000(1000),
    D_500(500),
    D_200(200),
    D_100(100),
    D_50(50);
    private final int value;
    Denomination(int value) { this.value = value; }
    public int getValue() { return value; }
}
