package ru.otus.sinetskiy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrei Sinetskii
 */
public class ATM {

    private Map<Denomination, Integer> atmBank = new HashMap<>();

    public void init() {
        atmBank.put(Denomination._5000_, 100);
        atmBank.put(Denomination._2000_, 100);
        atmBank.put(Denomination._1000_, 100);
        atmBank.put(Denomination._500_, 100);
        atmBank.put(Denomination._200_, 100);
        atmBank.put(Denomination._100_, 100);
        atmBank.put(Denomination._50_, 100);
    }

    void cashIn(Map<Denomination, Integer> cash) {

        for (var key : cash.keySet()) {
            atmBank.put(key, atmBank.getOrDefault(key, 0) + cash.get(key));
        }

        printAccount();

    }

    Map<Denomination, Integer> withdrawal(int sum) throws Exception {

        var cash = new HashMap<Denomination, Integer>();

        if (sum >= 5000) {
            sum = getCash(sum, Denomination._5000_, cash);
        }
        if (sum >= 2000) {
            sum = getCash(sum, Denomination._2000_, cash);
        }
        if (sum >= 1000) {
            sum = getCash(sum, Denomination._1000_, cash);
        }
        if (sum >= 500) {
            sum = getCash(sum, Denomination._500_, cash);
        }
        if (sum >= 200) {
            sum = getCash(sum, Denomination._200_, cash);
        }
        if (sum >= 100) {
            sum = getCash(sum, Denomination._100_, cash);
        }
        if (sum >= 50) {
            sum = getCash(sum, Denomination._50_, cash);
        }

        if (sum > 0)
            throw new Exception("Не возможно выдать запрашщиваемму сумму");

        return cash;
    }

    private int getCash(int sum, Denomination denomination, HashMap<Denomination, Integer> cash) {

        var countOfNotes = sum / denomination.getValue();
        atmBank.put(denomination, atmBank.getOrDefault(denomination,0) - countOfNotes);
        cash.put(denomination, countOfNotes);
        return sum % denomination.getValue();
    }

    void printAccount() {

        var sum = 0;

        for (var key : atmBank.keySet()) {
            sum += atmBank.get(key) * key.getValue();
        }

        System.out.println("Your account balance: " + sum);
    }

}
