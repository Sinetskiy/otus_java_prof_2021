package ru.otus.sinetskiy.reflection;

import ru.otus.sinetskiy.reflection.annotations.After;
import ru.otus.sinetskiy.reflection.annotations.Before;
import ru.otus.sinetskiy.reflection.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrei Sinetskii
 */
class TestRunner<T> {

    private Class<T> clazz;
    private Map<String, Integer> resultMap = new HashMap<>();
    private T testObj;

    TestRunner(Class<T> clazz) {
        this.clazz = clazz;
        this.testObj = ReflectionHelper.instantiate(clazz);
    }

    void run() {
        mainTests();
        printStatistic();
    }

    private void printStatistic() {

        System.out.println("--- Statistics");
        resultMap.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private void before() {
        System.out.println("--- before methods:");
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    ReflectionHelper.callMethod(testObj, method.getName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    resultMap.put("setup-problem", (resultMap.get("setup-problem") == null ? 0 : resultMap.get("setup-problem")) + 1);
                }
            }

        }
    }

    private void mainTests() {
        System.out.println("Tests methods:");
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    System.out.println("--- test method - " +  method.getName());
                    this.before();
                    ReflectionHelper.callMethod(testObj, method.getName());
                    resultMap.put("tests-success", (resultMap.get("tests-success") == null ? 0 : resultMap.get("tests-success")) + 1);
                    this.after();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    resultMap.put("tests-problem", (resultMap.get("tests-problem") == null ? 0 : resultMap.get("tests-success")) + 1);
                }
            }
        }
    }

    private void after() {
        System.out.println("--- after methods:");
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    ReflectionHelper.callMethod(testObj, method.getName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    resultMap.put("after-problem", (resultMap.get("after-problem") == null ? 0 : resultMap.get("after-problem")) + 1);
                }
            }
        }
    }
}
