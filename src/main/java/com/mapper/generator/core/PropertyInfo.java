package com.mapper.generator.core;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * Class to hold information about a property (field) in a class.
 */
public class PropertyInfo {
    private final String name;
    private final Method getter;
    private final Method setter;
    private final Class<?> type;
    private final boolean isCollection;
    private final boolean isArray;
    private final boolean isMap;
    private final Class<?> componentType;

    public PropertyInfo(String name, Method getter, Method setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.type = getter.getReturnType();
        this.isCollection = Collection.class.isAssignableFrom(type);
        this.isArray = type.isArray();
        this.isMap = Map.class.isAssignableFrom(type);
        
        // Determine component type for collections and arrays
        if (isArray) {
            this.componentType = type.getComponentType();
        } else if (isCollection) {
            Type genericReturnType = getter.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                Type[] typeArgs = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                if (typeArgs.length > 0 && typeArgs[0] instanceof Class) {
                    this.componentType = (Class<?>) typeArgs[0];
                } else {
                    this.componentType = Object.class;
                }
            } else {
                this.componentType = Object.class;
            }
        } else if (isMap) {
            // For maps, we consider the value type as the component type
            Type genericReturnType = getter.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                Type[] typeArgs = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                if (typeArgs.length > 1 && typeArgs[1] instanceof Class) {
                    this.componentType = (Class<?>) typeArgs[1];
                } else {
                    this.componentType = Object.class;
                }
            } else {
                this.componentType = Object.class;
            }
        } else {
            this.componentType = null;
        }
    }

    public String getName() {
        return name;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isMap() {
        return isMap;
    }

    public Class<?> getComponentType() {
        return componentType;
    }

    public boolean isPrimitive() {
        return type.isPrimitive() || type.equals(String.class) || 
               Number.class.isAssignableFrom(type) || 
               Boolean.class.equals(type) || 
               Character.class.equals(type) ||
               type.isEnum();
    }
}