package ru.otus.sinetskiy.collections;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private Stack<Customer> customerList = new Stack<>();

    public void add(Customer customer) {
        customerList.push(customer);
    }

    public Customer take() {
        return customerList.pop();
    }
}
