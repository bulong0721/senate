package com.jlt.framework.util;

import com.jlt.framework.GenericEntity;
import com.querydsl.core.types.EntityPath;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public final class GenericEntityUtil {
    private static final String NO_CLASS_FOUND_TEMPLATE = "Did not find a query class %s for domain class %s!";
    private static final String NO_FIELD_FOUND_TEMPLATE = "Did not find a static field of the same type in %s!";

    public static Class<?> getEntityClass(Class<?> clazz) {
        int retriesCount = 0;

        while (true) {
            if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                Type[] argumentTypes = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();

                for (Type argumentType : argumentTypes) {
                    Class<?> argumentClass;

                    if (argumentType instanceof ParameterizedType) {
                        argumentClass = (Class<?>) ((ParameterizedType) argumentType).getRawType();
                    } else {
                        argumentClass = (Class<?>) argumentType;
                    }

                    if (GenericEntity.class.isAssignableFrom(argumentClass)) {
                        return argumentClass;
                    }
                }
            }

            clazz = clazz.getSuperclass();
            retriesCount++;

            if (retriesCount > 5) {
                throw new IllegalArgumentException("Unable to find a generic type extending GenericEntityService.");
            }
        }
    }

    public static <T> EntityPath<T> createPath(Class<T> domainClass) {
        String pathClassName = getQueryClassName(domainClass);

        try {
            Class<?> pathClass = ClassUtils.forName(pathClassName, domainClass.getClassLoader());
            return getStaticFieldOfType(pathClass)//
                    .map(it -> (EntityPath<T>) ReflectionUtils.getField(it, null))//
                    .orElseThrow(() -> new IllegalStateException(String.format(NO_FIELD_FOUND_TEMPLATE, pathClass)));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format(NO_CLASS_FOUND_TEMPLATE, pathClassName, domainClass.getName()),
                    e);
        }
    }

    private static Optional<Field> getStaticFieldOfType(Class<?> type) {
        for (java.lang.reflect.Field field : type.getDeclaredFields()) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            boolean hasSameType = type.equals(field.getType());

            if (isStatic && hasSameType) {
                return java.util.Optional.of(field);
            }
        }

        Class<?> superclass = type.getSuperclass();
        return Object.class.equals(superclass) ? java.util.Optional.empty() : getStaticFieldOfType(superclass);
    }

    private static String getQueryClassName(Class<?> domainClass) {
        String simpleClassName = ClassUtils.getShortName(domainClass);
        return String.format("%s.Q%s%s", domainClass.getPackage().getName(), getClassBase(simpleClassName),
                domainClass.getSimpleName());
    }

    private static String getClassBase(String shortName) {
        String[] parts = shortName.split("\\.");
        if (parts.length < 2) {
            return "";
        }
        return parts[0] + "_";
    }

    private GenericEntityUtil() {
    }

}
