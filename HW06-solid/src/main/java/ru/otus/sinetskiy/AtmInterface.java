package ru.otus.sinetskiy;

import java.util.Map;

/**
 * @author Andrei Sinetskii
 */
public interface AtmInterface {

    // добавление денег
    void cashIn(int AccountId, Map<Denomination, Integer> cash);

    // выдача денег
    Map<Denomination, Integer> withdrawal(int AccountId, int sum) throws Exception;

    // баланс
    void printAccount(int AccountId);
}
