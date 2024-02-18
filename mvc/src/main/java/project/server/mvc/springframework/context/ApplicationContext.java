package project.server.mvc.springframework.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static java.lang.reflect.Modifier.isStatic;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.reflections.Reflections;
import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;
import static org.reflections.util.ClasspathHelper.forPackage;
import org.reflections.util.ConfigurationBuilder;
import project.server.mvc.springframework.annotation.Bean;
import project.server.mvc.springframework.annotation.Component;
import project.server.mvc.springframework.annotation.Configuration;

public class ApplicationContext {

    private static final int FIRST_CONSTRUCTOR = 0;
    private static final String PROXY = "Proxy";
    private static final String DATASOURCE = "Datasource";

    private static final Set<Class<?>> allBeans = new HashSet<>();
    private static final Map<String, Object> nameKeyBeans = new HashMap<>();
    private static final Map<Class<?>, Object> clazzKeyBeans = new HashMap<>();
    private static final Map<Class<?>, Object> dependencyInjectedBeans = new HashMap<>();

    private static Reflections reflections;

    public ApplicationContext(String... packages) throws Exception {
        reflections = getReflections(packages);

        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> configurations = reflections.getTypesAnnotatedWith(Configuration.class);
        componentScan(components);
        processConfigurations(configurations);
    }

    private Reflections getReflections(String... packages) {
        ConfigurationBuilder configurationBuilder = createConfigurationBuilder();
        for (String packageName : packages) {
            configurationBuilder.addUrls(forPackage(packageName));
        }
        return new Reflections(configurationBuilder);
    }

    private void componentScan(Set<Class<?>> components) throws Exception {
        for (Class<?> component : components) {
            if (isInstance(component)) {
                add(component);
            }
        }

        for (Class<?> instance : components) {
            if (isInstance(instance)) {
                put(instance);
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

    private boolean isInstance(Class<?> clazz) {
        return !clazz.isAnnotation()
            && !clazz.isInterface()
            && !Modifier.isAbstract(clazz.getModifiers());
    }

    private Object createInstance(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private void processBeanMethod(
        Object configInstance,
        Method method
    ) throws Exception {
        Class<?> returnType = method.getReturnType();
        if (!isStatic(method.getModifiers()) && isNotVoid(returnType)) {
            Object bean = method.invoke(configInstance);
            clazzKeyBeans.put(returnType, bean);
            nameKeyBeans.put(method.getName(), bean);
        }
    }

    private boolean isNotVoid(Class<?> returnType) {
        return returnType != void.class;
    }

    private ConfigurationBuilder createConfigurationBuilder() {
        return new ConfigurationBuilder()
            .setScanners(SubTypes, TypesAnnotated);
    }

    private void registerNamedInstance(Class<?> clazz) {
        Object instance = dependencyInjectedBeans.get(clazz);
        if (instance != null) {
            nameKeyBeans.put(clazz.getSimpleName(), instance);
        }
    }

    public static <T> T getBean(String beanName) {
        @SuppressWarnings("unchecked")
        T bean = (T) ApplicationContext.nameKeyBeans.get(beanName);
        return bean;
    }

    private void add(Class<?> clazz) throws Exception {
        if (clazzKeyBeans.containsKey(clazz) || allBeans.contains(clazz)) {
            return;
        }
        allBeans.add(clazz);

        if (clazz.isInterface()) {
            if (isDataSource(clazz)) {
                return;
            }

            @SuppressWarnings("unchecked")
            Set<Class<?>> implementations = reflections.getSubTypesOf((Class<Object>) clazz);
            if (implementations.isEmpty()) {
                throw new IllegalStateException("No implementation found for interface: " + clazz.getName());
            }

            Class<?> concreteClass = implementations.stream()
                .filter(containsName(PROXY))
                .findAny()
                .orElse(implementations.iterator().next());

            add(concreteClass);
            clazzKeyBeans.put(clazz, clazzKeyBeans.get(concreteClass));
            return;
        }

        Object instance = injectDependency(clazz);
        clazzKeyBeans.put(clazz, instance);
        dependencyInjectedBeans.put(clazz, instance);
    }

    private boolean isDataSource(Class<?> clazz) {
        return DATASOURCE.equals(clazz.getSimpleName());
    }

    private Object injectDependency(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[FIRST_CONSTRUCTOR];
        constructor.setAccessible(true);
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int index = 0; index < paramTypes.length; index++) {
            Class<?> parameterType = paramTypes[index];
            if (!clazzKeyBeans.containsKey(parameterType)) {
                add(parameterType);
            }
            params[index] = clazzKeyBeans.get(parameterType);
        }

        return constructor.newInstance(params);
    }

    private Predicate<Class<?>> containsName(String name) {
        return clazz -> clazz.getSimpleName().contains(name);
    }

    private void put(Class<?> clazz) {
        if (dependencyInjectedBeans.containsKey(clazz)) {
            return;
        }

        Object instance = clazzKeyBeans.get(clazz);
        if (instance == null) {
            throw new IllegalStateException("Instance not found: " + clazz.getName());
        }
        dependencyInjectedBeans.put(clazz, instance);
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz.cast(dependencyInjectedBeans.get(clazz));
    }

    public static Collection<Object> getAllDependencyInjectedInstances() {
        return nameKeyBeans.values();
    }
}
