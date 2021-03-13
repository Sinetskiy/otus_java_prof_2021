package ru.otus.sinetskiy.class_loader;

import ru.otus.sinetskiy.class_loader.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {

        private final TestLoggingInterface testLogging;
        private Set<String> logMethodsSet = new HashSet<>();


        DemoInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
            initLogMethodsSet();
        }

        private void initLogMethodsSet() {
            for (Method method : TestLoggingInterface.class.getMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
                    logMethodsSet.add(method.getName() + getParamsTypes(method));
                }
            }
        }

        private String getParamsTypes(Method method) {
            var paramsTypes = new StringBuilder();
            paramsTypes.append('[');
            for (var paramType : method.getParameterTypes()) {
                paramsTypes.append(paramType);
                paramsTypes.append(',');
            }
            paramsTypes.deleteCharAt(paramsTypes.length() - 1);
            paramsTypes.append(']');
            return paramsTypes.toString();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (logMethodsSet.contains(method.getName() + getParamsTypes(method))) {
                var params = args.length > 0 ? Arrays.toString(args) : "no params";
                System.out.println("invoking method: " + method + " params: " + params);
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "class=" + testLogging +
                    '}';
        }
    }
}
