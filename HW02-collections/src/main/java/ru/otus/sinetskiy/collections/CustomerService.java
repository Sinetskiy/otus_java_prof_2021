package ru.otus.sinetskiy.collections;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private TreeMap<Customer, String> customerMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Customer key = customerMap.firstKey();
        Customer newKey = new Customer(key.getId(), key.getName(), key.getScores());
        return new AbstractMap.SimpleEntry<>(newKey, customerMap.get(newKey));
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return customerMap.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
