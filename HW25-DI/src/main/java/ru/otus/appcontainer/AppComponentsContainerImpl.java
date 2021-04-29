package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws Exception {
        Constructor<?> ctor = getConstructor(configClass);
        setCollectionOfComponents(configClass, ctor);
    }

    private Constructor<?> getConstructor(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);
        Constructor<?> ctor;
        ctor = configClass.getConstructor();
        return ctor;
    }

    private void setCollectionOfComponents(Class<?> configClass, Constructor<?> ctor) throws Exception {
            Object appConfig = ctor.newInstance();
            var methods = configClass.getMethods();
            for (var i = 0; i < methods.length; i++) { // но вообще говоря ордер может быть и 100 200 300 ...
                for (Method method : methods) {
                    addComponent(appConfig, i, method);
                }
            }
    }

    private void addComponent(Object appConfig, int i, Method method) throws IllegalAccessException, InvocationTargetException {
        if (method.isAnnotationPresent(AppComponent.class)) {
            AppComponent annotations = method.getAnnotation(AppComponent.class);
            if (annotations.order() == i) {
                var params = method.getParameterTypes();
                List<Object> args = new ArrayList<>();
                for (var param : params) {
                    args.add(appComponentsByName.get(param.getTypeName()));
                }
//                System.out.println(method.getName());
//                System.out.println(appConfig.toString());
//                for (var arg : args) {
//                    System.out.println(arg);
//                }
//                var object =  method.invoke(appConfig, args); // <- запустите потом расскажете как этот метод работает
                var object = args.size() != 0 ?
                                            args.size() != 1 ?
                                                    method.invoke(appConfig, args.get(0), args.get(1), args.get(2))
                                                    : method.invoke(appConfig, args.get(0))
                                                            : method.invoke(appConfig);
                appComponentsByName.put(method.getReturnType().getTypeName(), object);
                appComponentsByName.put(annotations.name(), object);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var component = (C) appComponentsByName.get(componentClass.getTypeName());
        for (var _interface : componentClass.getInterfaces()) {
            component = (C) appComponentsByName.get(_interface.getTypeName());
            if (component != null)
                return component;
        }
        return component;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
