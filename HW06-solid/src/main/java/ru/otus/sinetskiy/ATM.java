package ru.otus.sinetskiy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrei Sinetskii
 */
public class ATM implements AtmInterface {

    private Map<Denomination, Integer> atmBank = new HashMap<>();
    private Map<Integer, Integer> accounts = new HashMap<>();

    private int getCash(int sum, Denomination denomination, Map<Denomination, Integer> cash) {

        var countOfNotes = sum / denomination.getValue();
        atmBank.put(denomination, atmBank.getOrDefault(denomination, 0) - countOfNotes);
        cash.put(denomination, countOfNotes);
        return sum % denomination.getValue();
    }

    private int getNewBalance(int accountId, int sum) throws Exception {
        var accountBalance = accounts.getOrDefault(accountId, 0);

        if (accountBalance < sum)
            throw new Exception("Не достаточно средств");

        return accountBalance - sum;
    }

    public void cashIn(int accountId, Map<Denomination, Integer> cash) {

        var sum = 0;
        for (var key : cash.keySet()) {
            atmBank.put(key, atmBank.getOrDefault(key, 0) + cash.get(key));
            sum += key.getValue() * cash.get(key);
        }

        accounts.put(accountId, accounts.getOrDefault(accountId, 0) + sum);
        printAccount(accountId);

    }


    public Map<Denomination, Integer> withdrawal(int accountId, int sum) throws Exception {

        var cash = new HashMap<Denomination, Integer>();
        int newBalance = getNewBalance(accountId, sum);

        for (var denomination : Denomination.values()) {
            if (sum != 0)
                sum = getCash(sum, denomination, cash);
        }

        if (sum > 0)
            throw new Exception("Не возможно выдать запрашиваемму сумму");

        accounts.put(accountId, newBalance);

        return cash;
    }

    public void printAccount(int accountId) {

        var sum = accounts.getOrDefault(accountId, 0);
        System.out.println("Your account balance: " + sum);
    }

}
