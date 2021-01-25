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

    TestRunner(Class<T> clazz) {
        this.clazz = clazz;
    }

    void run() {
        before();
        mainTests();
        after();
        printStatistic();
    }

    private void printStatistic() {

        System.out.println("--- Statistics");
        resultMap.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private void before() {
        System.out.println("--- before methods:");
        T beforeObj = ReflectionHelper.instantiate(clazz);
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    ReflectionHelper.callMethod(beforeObj, method.getName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    resultMap.put("setup-problem", (resultMap.get("setup-problem") == null ? 0 : resultMap.get("setup-problem")) + 1);
                }
            }

        }
    }

    private void mainTests() {
        System.out.println("--- tests methods:");
        T mainTestsObj = ReflectionHelper.instantiate(clazz);
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    ReflectionHelper.callMethod(mainTestsObj, method.getName());
                    resultMap.put("tests-success", (resultMap.get("tests-success") == null ? 0 : resultMap.get("tests-success")) + 1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    resultMap.put("tests-problem", (resultMap.get("tests-problem") == null ? 0 : resultMap.get("tests-success")) + 1);
                }
            }
        }
    }

    private void after() {
        System.out.println("--- tests methods:");
        T afterObj = ReflectionHelper.instantiate(clazz);
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    ReflectionHelper.callMethod(afterObj, method.getName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    resultMap.put("after-problem", (resultMap.get("after-problem") == null ? 0 : resultMap.get("after-problem")) + 1);
                }
            }
        }
    }
}
