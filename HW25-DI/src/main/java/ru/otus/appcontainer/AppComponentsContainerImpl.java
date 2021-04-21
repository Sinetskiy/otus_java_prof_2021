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

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Constructor<?> ctor = null;
        try {
            ctor = configClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            Object appConfig = ctor.newInstance();
            var methods = configClass.getMethods();
            for (var i = 0; i < 3; i++) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AppComponent.class)) {
                        AppComponent annotations = method.getAnnotation(AppComponent.class);
                        if (annotations.order() == i) {
                            var params = method.getParameterTypes();
                            ArrayList<Object> args = new ArrayList<>();
                            for (var param : params) {
                                args.add(appComponentsByName.get(param.getTypeName()));
                            }
                            Object object;
                            if (args.size() == 0) {
                                object = method.invoke(appConfig);
                                collectComponents(method, annotations, object);
                            }
                            if (args.size() == 1) {
                                object = method.invoke(appConfig, args.get(0));
                                collectComponents(method, annotations, object);
                            }
                            if (args.size() == 3) {
                                object = method.invoke(appConfig, args.get(0), args.get(1), args.get(2));
                                collectComponents(method, annotations, object);
                            }
                        }
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void collectComponents(Method method, AppComponent annotations, Object object) {
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
