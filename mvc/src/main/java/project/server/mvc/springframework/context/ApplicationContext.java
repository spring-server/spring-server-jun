package project.server.mvc.springframework.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import project.server.mvc.springframework.annotation.Bean;
import project.server.mvc.springframework.annotation.Component;
import project.server.mvc.springframework.annotation.Configuration;

public class ApplicationContext {
    private static final Map<Class<?>, Object> beans = new HashMap<>();
    private static final Map<Class<?>, Object> dependenciesInjectedBeans = new HashMap<>();
    private static final Map<String, Object> dependenciesInjectedBeansByName = new HashMap<>();

    private static Reflections reflections;

    public ApplicationContext(String... packages) throws Exception {
        reflections = getReflections(packages);

        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> configurations = reflections.getTypesAnnotatedWith(Configuration.class);
        componentScan(components);
        processConfigurations(configurations);
    }

    private void componentScan(Set<Class<?>> components) throws Exception {
        for (Class<?> component : components) {
            if (isInstance(component)) {
                add(component);
            }
        }

        for (Class<?> instance : components) {
            if (isInstance(instance)) {
                injectDependencies(instance);
                registerNamedInstance(instance);
            }
        }
    }

    private void processConfigurations(Set<Class<?>> configurations) throws Exception {
        for (Class<?> configClass : configurations) {
            Object configInstance = createInstance(configClass);
            Method[] methods = configClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Bean.class)) {
                    processBeanMethod(configInstance, method);
                }
            }
        }
    }

    private Object createInstance(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private void processBeanMethod(Object configInstance, Method method) throws Exception {
        Class<?> returnType = method.getReturnType();
        if (!Modifier.isStatic(method.getModifiers()) && returnType != void.class) {
            Object bean = method.invoke(configInstance);
            beans.put(returnType, bean);
            dependenciesInjectedBeansByName.put(method.getName(), bean);
        }
    }

    private Reflections getReflections(String... packages) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
            .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner());
        for (String packageName : packages) {
            configurationBuilder.addUrls(ClasspathHelper.forPackage(packageName));
        }
        return new Reflections(configurationBuilder);
    }

    private boolean isInstance(Class<?> clazz) {
        return !clazz.isAnnotation() && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    private void registerNamedInstance(Class<?> clazz) {
        Object instance = dependenciesInjectedBeans.get(clazz);
        if (instance != null) {
            dependenciesInjectedBeansByName.put(clazz.getSimpleName(), instance);
        }
    }

    public static <T> T getBean(String beanName) {
        @SuppressWarnings("unchecked")
        T bean = (T) dependenciesInjectedBeansByName.get(beanName);
        return bean;
    }

    private void add(Class<?> clazz) throws Exception {
        if (beans.containsKey(clazz)) {
            return;
        }

        if (clazz.isInterface()) {
            @SuppressWarnings("unchecked")
            Set<Class<?>> implementations = reflections.getSubTypesOf((Class<Object>) clazz);
            if (implementations.isEmpty()) {
                throw new IllegalStateException("No implementation found for interface: " + clazz.getName());
            }

            Class<?> subTypeClass = implementations.iterator().next();
            add(subTypeClass);
            beans.put(clazz, beans.get(subTypeClass));
            return;
        }

        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int index = 0; index < paramTypes.length; index++) {
            Class<?> parameterType = paramTypes[index];
            if (!beans.containsKey(parameterType)) {
                add(parameterType);
            }
            params[index] = beans.get(parameterType);
        }

        Object instance = constructor.newInstance(params);
        beans.put(clazz, instance);
    }

    private void injectDependencies(Class<?> clazz) {
        if (dependenciesInjectedBeans.containsKey(clazz)) {
            return;
        }

        Object instance = beans.get(clazz);
        if (instance == null) {
            throw new IllegalStateException("Instance not found: " + clazz.getName());
        }
        dependenciesInjectedBeans.put(clazz, instance);
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz.cast(dependenciesInjectedBeans.get(clazz));
    }

    public static Collection<Object> getAllDependencyInjectedInstances() {
        return dependenciesInjectedBeansByName.values();
    }
}
