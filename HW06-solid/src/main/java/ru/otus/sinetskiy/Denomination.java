package ru.otus.sinetskiy;

/**
 * @author Andrei Sinetskii
 */
public enum Denomination {
    _50_(50),
    _100_(100),
    _200_(200),
    _500_(500),
    _1000_(1000),
    _2000_(2000),
    _5000_(5000);
    private final int value;
    Denomination(int value) { this.value = value; }
    public int getValue() { return value; }
}
