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
        var sortedMethods = getSortedMethods(methods);

        for (Map.Entry<String, ArrayList<Method>> entry : sortedMethods.entrySet()) {
            var methodsByOrder = entry.getValue();
            for (Method method : methodsByOrder) {
                addComponent(appConfig, method);
            }
        }

    }

    private TreeMap<String, ArrayList<Method>> getSortedMethods(Method[] methods) {
        var sortedMethods = new TreeMap<String, ArrayList<Method>>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                AppComponent annotations = method.getAnnotation(AppComponent.class);
                ArrayList<Method> methodsList = sortedMethods.get(String.valueOf(annotations.order())) != null ?
                        sortedMethods.get(String.valueOf(annotations.order())) : new ArrayList<>();
                methodsList.add(method);
                sortedMethods.put(String.valueOf(annotations.order()), methodsList);
            }
        }
        return sortedMethods;
    }

    private void addComponent(Object appConfig, Method method) throws IllegalAccessException, InvocationTargetException {

        AppComponent annotations = method.getAnnotation(AppComponent.class);

        var params = method.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for (var param : params) {
            args.add(appComponentsByName.get(param.getTypeName()));
        }

        var object = method.invoke(appConfig, args.toArray(new Object[0]));
        appComponentsByName.put(method.getReturnType().getTypeName(), object);
        appComponentsByName.put(annotations.name(), object);
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
